### mybatis实体类自动生成工具
本项目主要使用SpringBoot进行开发, 主要用途: 读取数据库中的表结构, 生成符合mybatis-plus风格的实体类, mapper文件以及repository文件. 理论上支持所有数据库

#### 如何使用
##### 1. 打包
mvn clean package
##### 2. 执行
java -jar niflheim.jar
##### 3. 配置说明
```yaml
# 数据源信息, 必填
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: xxx
    username: xxx
    password: xxx
    
  # 使用ThymeleafTemplateEngine时必须配置. 后缀名在程序中已经写死, 不要配置.
  thymeleaf:
    prefix: classpath:/thymeleaf/
    mode: TEXT

  # 使用FreemarkerTemplateEngine时必须配置.
  freemarker:
    template-loader-path: classpath:/freemarker/
    
com:
  annwyn:
    niflheim:
      # 文件生成引擎. 可选值: com.annwyn.niflheim.core.engine.FreemarkerTemplateEngine与com.annwyn.niflheim.core.engine.ThymeleafTemplateEngine
      # 可以自行扩展, 只需要继承com.annwyn.niflheim.core.engine.AbstractTemplateEngine即可. 默认选择com.annwyn.niflheim.core.engine.FreemarkerTemplateEngine
      # com.annwyn.niflheim.core.engine.FreemarkerTemplateEngine使用freemarker生成文件
      # 模板文件位于resources/freemarker目录下, 可以自行修改生成文件
      # com.annwyn.niflheim.core.engine.ThymeleafTemplateEngine使用thymeleaf生成文件
      # 模板文件位于resources/thymeleaf目录下, 可以自行修改生成文件
      template-engine: com.annwyn.niflheim.core.engine.FreemarkerTemplateEngine
      # 对应的数据库字段映射. 默认为mysql
      # 映射数据库类型转换jdbc类型以及jdbc类型转换java类型
      # 其他数据库需要继承AbstractTypeRegistry并进行配置
      type-registry: com.annwyn.niflheim.core.registry.MysqlTypeRegistry
      # 生成实体类的输入目录, 必填
      output-directory: /Users/annwyn/workspace/workbench/annwyn/niflheim/output
      # 生成实体类的包名, 必填
      package-name: com.annwyn.eris
      # 需要生成的表, 优先级高, 先判断include-tables再判断exclusion-tables
      include-tables:
        - table2
      # 忽略生成的表, 优先级低, 先判断include-tables再判断exclusion-tables
      exclusion-tables:
        - table1
      # 需要重命名的表
      override-tables:
        - model-name: modelName
          table-name: tableName
      # 去除表名前缀
      remove-suffixes:
        - table_suffix


```