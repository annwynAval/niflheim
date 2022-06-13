package com.annwyn.niflheim.core.engine;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ThymeleafTemplateEngine extends AbstractTemplateEngine {

    @Resource
    private TemplateEngine templateEngine;

    @Override
    protected void doGeneratorModel(BufferedWriter bufferedWriter, Map<String, Object> parameters) {
        this.templateEngine.process("model.java", new Context(Locale.CHINA, parameters), bufferedWriter);
    }

    @Override
    protected void doGeneratorMapper(BufferedWriter bufferedWriter, Map<String, Object> parameters) {
        this.templateEngine.process("mapper.java", new Context(Locale.CHINA, parameters), bufferedWriter);
    }

    @Override
    protected void doGeneratorXml(BufferedWriter bufferedWriter, Map<String, Object> parameters) throws IOException {
        this.templateEngine.process("mapper.java", new Context(Locale.CHINA, parameters), bufferedWriter);
    }

    @Override
    protected void doGeneratorRepository(BufferedWriter bufferedWriter, Map<String, Object> parameters) {
        this.templateEngine.process("repository.java", new Context(Locale.CHINA, parameters), bufferedWriter);
    }
}
