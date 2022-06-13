package com.annwyn.niflheim.core.engine;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.core.models.JavaModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractTemplateEngine {

    private static final String IGNORE_PACKAGE_NAME = "java.lang";

    private final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    
    @Resource
    protected NiflheimProperties niflheimProperties;

    public void startGenerator(List<JavaModel> javaModels) {
        final String modelPackage = this.niflheimProperties.getPackageName() + ".models", modelPathName = ClassUtils.convertClassNameToResourcePath(modelPackage);
        final String mapperPackage = this.niflheimProperties.getPackageName() + ".mapper", mapperPathName = ClassUtils.convertClassNameToResourcePath(mapperPackage);
        final String repositoryPackage = this.niflheimProperties.getPackageName() + ".repository", repositoryPathName = ClassUtils.convertClassNameToResourcePath(repositoryPackage);

        for (JavaModel javaModel : javaModels) {
            try {
                this.logger.info("开始生成文件: {}", javaModel.getTableName());
                Path modelPath = Paths.get(this.niflheimProperties.getOutputDirectory(), modelPathName, javaModel.getJavaName() + ".java");
                this.checkPath(modelPath);
                this.generatorModel(modelPath, modelPackage, javaModel);

                Path mapperPath = Paths.get(this.niflheimProperties.getOutputDirectory(), mapperPathName, javaModel.getJavaName() + "Mapper.java");
                this.checkPath(mapperPath);
                this.generatorMapper(mapperPath, mapperPackage, javaModel, modelPackage);

                Path xmlPath = Paths.get(this.niflheimProperties.getOutputDirectory(), mapperPathName, javaModel.getJavaName() + "Mapper.xml");
                this.checkPath(xmlPath);
                this.generatorXml(xmlPath, mapperPackage, javaModel);

                Path repositoryPath = Paths.get(this.niflheimProperties.getOutputDirectory(), repositoryPathName, javaModel.getJavaName() + "Repository.java");
                this.checkPath(repositoryPath);
                this.generatorRepository(repositoryPath, repositoryPackage, javaModel, modelPackage, mapperPackage);
            } catch (IOException e) {
                this.logger.info("生成文件失败.", e);
            }
        }
    }

    private void checkPath(Path currentPath) throws IOException {
        if(Files.exists(currentPath)) { // 存在, 先删掉
            Files.delete(currentPath);
        }
        if(Files.notExists(currentPath.getParent())) { // 创建父目录
            Files.createDirectories(currentPath.getParent());
        }
        if(Files.notExists(currentPath)) { // 创建当前目录
            Files.createFile(currentPath);
        }
    }

    protected void generatorModel(Path modelPath, String packageName, JavaModel javaModel) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(modelPath, StandardCharsets.UTF_8)) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("packageName", packageName);
            parameters.put("javaModel", javaModel);
            parameters.put("imports", javaModel.getJavaColumnModels().stream().map(item -> item.getColumnClass().getCanonicalName())
                    .filter(item -> !item.startsWith(IGNORE_PACKAGE_NAME)).collect(Collectors.toSet()));

            this.doGeneratorModel(bufferedWriter, parameters);
        }
    }

    protected abstract void doGeneratorModel(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException;

    protected void generatorMapper(Path mapperPath, String mapperPackage, JavaModel javaModel, String modelPackage) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(mapperPath, StandardCharsets.UTF_8)) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("packageName", mapperPackage);
            parameters.put("javaModel", javaModel);
            parameters.put("imports", Collections.singleton(String.format("%s.%s", modelPackage, javaModel.getJavaName())));

            this.doGeneratorMapper(bufferedWriter, parameters);
        }
    }

    protected abstract void doGeneratorMapper(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException;

    protected void generatorXml(Path xmlPath, String xmlPackage, JavaModel javaModel) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(xmlPath, StandardCharsets.UTF_8)) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("namespace", String.format("%s.%sMapper", xmlPackage, javaModel.getJavaName()));

            this.doGeneratorXml(bufferedWriter, parameters);
        }
    }

    protected abstract void doGeneratorXml(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException;

    protected void generatorRepository(Path repositoryPath, String repositoryPackage, JavaModel javaModel, String modelPackage, String mapperPackage) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(repositoryPath, StandardCharsets.UTF_8)) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("packageName", repositoryPackage);
            parameters.put("javaModel", javaModel);
            parameters.put("imports", Sets.newHashSet(String.format("%s.%s", modelPackage, javaModel.getJavaName()), String.format("%s.%sMapper", mapperPackage, javaModel.getJavaName())));

            this.doGeneratorRepository(bufferedWriter, parameters);
        }
    }

    protected abstract void doGeneratorRepository(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException;

}
