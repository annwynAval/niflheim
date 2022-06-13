package com.annwyn.niflheim.core.engine;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    @Resource
    private Configuration configuration;

    @Override
    protected void doGeneratorModel(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException, TemplateEngineException {
        try {
            Template template = this.configuration.getTemplate("model.ftl", Locale.CHINA);
            template.process(parameters, bufferedWriter);
        } catch (TemplateException e) {
            throw new TemplateEngineException("生成文件时出现错误", e);
        }
    }

    @Override
    protected void doGeneratorMapper(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException, TemplateEngineException {
        try {
            Template template = this.configuration.getTemplate("mapper.ftl", Locale.CHINA);
            template.process(parameters, bufferedWriter);
        } catch (TemplateException e) {
            throw new TemplateEngineException("生成文件时出现错误", e);
        }
    }

    @Override
    protected void doGeneratorXml(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException {
        try {
            Template template = this.configuration.getTemplate("xml.ftl", Locale.CHINA);
            template.process(parameters, bufferedWriter);
        } catch (TemplateException e) {
            throw new TemplateEngineException("生成文件时出现错误", e);
        }
    }

    @Override
    protected void doGeneratorRepository(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException, TemplateEngineException {
        try {
            Template template = this.configuration.getTemplate("repository.ftl", Locale.CHINA);
            template.process(parameters, bufferedWriter);
        } catch (TemplateException e) {
            throw new TemplateEngineException("生成文件时出现错误", e);
        }
    }
}
