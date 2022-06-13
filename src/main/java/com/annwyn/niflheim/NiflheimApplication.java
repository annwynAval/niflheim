package com.annwyn.niflheim;


import com.annwyn.niflheim.core.NiflheimProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;


@SpringBootApplication
public class NiflheimApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = SpringApplication.run(NiflheimApplication.class, args);
        applicationContext.getBean(NiflheimProcessor.class).startProcessor();
    }

}
