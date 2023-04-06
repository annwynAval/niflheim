package com.annwyn.niflheim.generator.engine;

import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

	private Configuration configuration;

	@Override
	public void doGeneratorFile(String templatePath, BufferedWriter bufferedWriter, TransferParameters transferParameters) throws Exception {
		Template template = this.configuration.getTemplate(templatePath, Locale.CHINA);
		template.process(transferParameters, bufferedWriter);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		this.configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
		this.configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/");
	}

}
