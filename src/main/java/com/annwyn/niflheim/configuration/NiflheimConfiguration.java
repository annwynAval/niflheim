package com.annwyn.niflheim.configuration;


import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.generator.configure.keyword.KeywordHandle;
import com.annwyn.niflheim.generator.configure.naming.NamingStrategy;
import com.annwyn.niflheim.generator.configure.registry.ColumnRegistry;
import com.annwyn.niflheim.generator.engine.AbstractTemplateEngine;
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
    @ConditionalOnMissingBean(ColumnRegistry.class)
    public ColumnRegistry columnRegistry(NiflheimProperties niflheimProperties) throws ReflectiveOperationException {
        return ReflectionUtils.accessibleConstructor(niflheimProperties.getColumnRegistry()).newInstance();
    }

    @Bean
    @ConditionalOnMissingBean(KeywordHandle.class)
    public KeywordHandle keywordHandle(NiflheimProperties niflheimProperties) throws ReflectiveOperationException {
        return ReflectionUtils.accessibleConstructor(niflheimProperties.getKeywordHandle()).newInstance();
    }
    @Bean
    @ConditionalOnMissingBean(NamingStrategy.class)
    public NamingStrategy namingStrategy(NiflheimProperties niflheimProperties) throws ReflectiveOperationException {
        return ReflectionUtils.accessibleConstructor(niflheimProperties.getNamingStrategy()).newInstance();
    }


}
