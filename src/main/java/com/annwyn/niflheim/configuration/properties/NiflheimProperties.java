package com.annwyn.niflheim.configuration.properties;

import com.annwyn.niflheim.core.engine.AbstractTemplateEngine;
import com.annwyn.niflheim.core.engine.FreemarkerTemplateEngine;
import com.annwyn.niflheim.core.registry.AbstractTypeRegistry;
import com.annwyn.niflheim.core.registry.MysqlTypeRegistry;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "com.annwyn.niflheim")
public class NiflheimProperties implements Serializable {

    /**
     * 输出目录
     */
    private String outputDirectory;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 表名需要去除的前缀
     */
    private List<String> removeSuffixes;

    /**
     * 需要转换的表, 如果没有设置的话, 表示转换数据库中所有表
     */
    private List<String> includeTables;

    /**
     * 不需要转换的表, 如果没有设置, 表示转换数据库中所有表
     */
    private List<String> exclusionTables;

    /**
     * 需要转换的表, 如果没有设置的话, 表示转换数据库中所有表
     */
    private List<OverrideTable> overrideTables;

    /**
     * 数据库转换类型
     */
    private Class<? extends AbstractTypeRegistry> typeRegistry = MysqlTypeRegistry.class;

    /**
     * 模板引起类型
     */
    private Class<? extends AbstractTemplateEngine> templateEngine = FreemarkerTemplateEngine.class;

    @Data
    public static class OverrideTable implements Serializable {

        /**
         * 表名
         */
        private String tableName;

        /**
         * 实体名称
         */
        private String modelName;

    }

}
