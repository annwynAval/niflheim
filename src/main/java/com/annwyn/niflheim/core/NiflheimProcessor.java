package com.annwyn.niflheim.core;

import com.annwyn.niflheim.core.engine.AbstractTemplateEngine;
import com.annwyn.niflheim.core.models.JavaModel;
import com.annwyn.niflheim.core.models.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class NiflheimProcessor {

    private final Logger logger = LoggerFactory.getLogger(NiflheimProcessor.class);

    @Resource
    private DatabaseScanner databaseScanner;

    @Resource
    private JavaModelConvertor javaModelConvertor;

    @Resource
    private AbstractTemplateEngine abstractTemplateEngine;

    public void startProcessor() throws IOException {
        this.logger.info("开始生成实体类. ");
        final List<TableModel> tableModels = this.databaseScanner.scanTables();
        this.logger.info("扫描数据库完成, 一共检索到{}张表", tableModels.size());
        final List<JavaModel> javaModels = this.javaModelConvertor.convertJavaModels(tableModels);
        this.logger.info("数据库对象转换java对象完成. 一共转换{}个对象", javaModels.size());
        this.abstractTemplateEngine.startGenerator(javaModels);
        this.logger.info("生成实体类完成");
    }

}
