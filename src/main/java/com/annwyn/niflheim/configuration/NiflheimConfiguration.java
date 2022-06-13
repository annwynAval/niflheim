package com.annwyn.niflheim.configuration;


import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.core.engine.AbstractTemplateEngine;
import com.annwyn.niflheim.core.registry.AbstractTypeRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;


@Configuration
public class NiflheimConfiguration {

    @Bean
    @ConditionalOnMissingBean(AbstractTemplateEngine.class)
    public AbstractTemplateEngine abstractTemplateEngine(NiflheimProperties niflheimProperties) throws ReflectiveOperationException {
        return ReflectionUtils.accessibleConstructor(niflheimProperties.getTemplateEngine()).newInstance();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractTypeRegistry.class)
    public AbstractTypeRegistry abstractTypeRegistry(NiflheimProperties niflheimProperties) throws ReflectiveOperationException {
        return ReflectionUtils.accessibleConstructor(niflheimProperties.getTypeRegistry()).newInstance();
    }
}
