package com.annwyn.niflheim.generator.transfer;


import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.generator.engine.AbstractTemplateEngine;
import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class TransferProcess {

	@Resource
	protected AbstractTemplateEngine templateEngine;

	@Resource
	protected NiflheimProperties niflheimProperties;

	public abstract void writeFile(TransferContext transferContext) throws Exception;

	protected void doWriteFile(String template, Path outputPath, TransferParameters transferParameters) throws Exception {
		if(!Files.exists(outputPath.getParent())) {
			Files.createDirectories(outputPath.getParent());
		}
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
			this.templateEngine.doGeneratorFile(template, bufferedWriter, transferParameters);
		}
	}

	protected Path joinPath(String packageName, String fileName) {
		return Paths.get(this.niflheimProperties.getOutputDirectory(), ClassUtils.convertClassNameToResourcePath(packageName), fileName);
	}

}
