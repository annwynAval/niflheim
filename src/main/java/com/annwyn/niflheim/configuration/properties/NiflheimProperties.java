package com.annwyn.niflheim.configuration.properties;

import com.annwyn.niflheim.generator.configure.keyword.KeywordHandle;
import com.annwyn.niflheim.generator.configure.keyword.MySQLKeywordHandle;
import com.annwyn.niflheim.generator.configure.naming.DefaultNamingStrategy;
import com.annwyn.niflheim.generator.configure.naming.NamingStrategy;
import com.annwyn.niflheim.generator.configure.registry.ColumnRegistry;
import com.annwyn.niflheim.generator.configure.registry.MySQLColumnRegistry;
import com.annwyn.niflheim.generator.engine.AbstractTemplateEngine;
import com.annwyn.niflheim.generator.engine.FreemarkerTemplateEngine;
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
     * 开始时是否清空输出目录
     */
    private boolean cleanPathAtStartup = true;
    /**
     * 模板引擎类型
     */
    private Class<? extends AbstractTemplateEngine> templateEngine = FreemarkerTemplateEngine.class;

    /**
     * 数据库转换类型
     */
    private Class<? extends ColumnRegistry> columnRegistry = MySQLColumnRegistry.class;

    /**
     * 关键字处理
     */
    private Class<? extends KeywordHandle> keywordHandle = MySQLKeywordHandle.class;

    /**
     * 命名规则
     */
    private Class<? extends NamingStrategy> namingStrategy = DefaultNamingStrategy.class;


    /**
     * 输出目录
     */
    private String outputDirectory;

    /**
     * 基础包名
     */
    private String packageName;

    /**
     * 模块名称.
     */
    private String moduleName;

    /**
     * 表名需要去除的前缀
     */
    private List<String> removePrefixes;

    /**
     * 表名需要去除的后缀
     */
    private List<String> removeSuffixes;

    /**
     * 需要生成的表, 支持ant-path进行匹配. 为空表示生成所有表
     */
    private List<String> includeTables;

}
