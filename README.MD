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
    
com:
  annwyn:
    niflheim:
      # 启动时是否清除输出目录下的所有文件
      clean-path-at-startup: true
      # 生成实体类的输入目录, 必填
      output-directory: /Users/annwyn/workbench/workspace/annwyn/niflheim/output
      # 生成实体类的包名, 必填
      package-name: com.annwyn.eris
      # 需要生成的表, 支持ant-path进行匹配. 为空表示生成所有表
      include-tables:
        - table2
      # 去除表名前缀
      remove-prefixes:
        - tss_
      # 去除表名后缀
      remove-suffixes:
        - tss_
```

#### 如何扩展
`com.annwyn.niflheim.generator.configure.keyword.KeywordHandle`实现该接口, 扩展关键字识别项

`com.annwyn.niflheim.generator.configure.naming.NamingStrategy`实现该接口, 扩展文件名/类名生成规则

`com.annwyn.niflheim.generator.configure.registry.ColumnRegistry`实现该接口, 扩展数据库字段类型与java类型的映射关系

`com.annwyn.niflheim.generator.engine.AbstractTemplateEngine`继承该类, 替换freemarker进行文件生成

`com.annwyn.niflheim.generator.transfer.TransferProcess`继承该类, 同时添加模版文件, 即可生成其他文件