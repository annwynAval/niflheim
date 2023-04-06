package com.annwyn.niflheim;


import com.annwyn.niflheim.generator.NiflheimProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class NiflheimApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(NiflheimApplication.class, args);
        applicationContext.getBean(NiflheimProcessor.class).startProcessor();
    }

}
