package com.annwyn.niflheim.generator.engine;

import com.annwyn.niflheim.generator.models.parameters.TransferParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedWriter;

@Slf4j
public abstract class AbstractTemplateEngine implements InitializingBean {

	public abstract void doGeneratorFile(String template, BufferedWriter bufferedWriter, TransferParameters transferParameters) throws Exception;

}
