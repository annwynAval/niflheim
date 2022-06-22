package com.annwyn.niflheim.core.registry;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTypeRegistry implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(AbstractTypeRegistry.class);

    /**
     * jdbcType <-> javaType
     */
    private final Map<JDBCType, Class<?>> javaTypeCaches = new ConcurrentHashMap<>(256);

    /**
     * databaseType <-> jdbcType
     */
    private final Map<String, JDBCType> jdbcTypeCaches = new ConcurrentHashMap<>(256);

    /**
     * 根据数据库类型查询映射的java类型. 未映射的情况下返回string
     * @param jdbcType .
     * @return .
     */
    public Class<?> getJavaType(JDBCType jdbcType) {
        if(!this.javaTypeCaches.containsKey(jdbcType)) {
            this.logger.warn("类型: {}未匹配. 返回字符串类型", jdbcType.getName());
            return String.class;
        }
        return this.javaTypeCaches.get(jdbcType);
    }

    /**
     * 根据数据库类型查询映射的jdbc类型. 未映射的情况下返回varchar
     * @param typeName .
     * @return .
     */
    public JDBCType getJdbcType(String typeName) {
        if(!this.jdbcTypeCaches.containsKey(typeName)) {
            this.logger.warn("类型: {}未匹配. 返回varchar类型", typeName);
        }
        return this.jdbcTypeCaches.get(typeName);
    }

    /**
     * jdbc类型 - java类之间的映射
     * @param jdbcType .
     * @param clazz .
     */
    protected void registerJavaType(JDBCType jdbcType, Class<?> clazz) {
        this.javaTypeCaches.put(jdbcType, clazz);
    }

    /**
     * 数据库的字段类型 - jdbc类型之间的映射
     * @param databaseName .
     * @param jdbcType .
     */
    protected void registerJdbcType(String databaseName, JDBCType jdbcType) {
        this.jdbcTypeCaches.put(databaseName, jdbcType);
    }

    /**
     * 注册jdbc类型与java类型之间的映射
     */
    protected void registerJavaTypes() {
        this.registerJavaType(JDBCType.BOOLEAN, Boolean.class);
        this.registerJavaType(JDBCType.BIT, Boolean.class);
        this.registerJavaType(JDBCType.TINYINT, Byte.class);
        this.registerJavaType(JDBCType.SMALLINT, Short.class);
        this.registerJavaType(JDBCType.INTEGER, Integer.class);
        this.registerJavaType(JDBCType.FLOAT, Float.class);
        this.registerJavaType(JDBCType.DOUBLE, Double.class);
        this.registerJavaType(JDBCType.CHAR, String.class);
        this.registerJavaType(JDBCType.VARCHAR, String.class);
        this.registerJavaType(JDBCType.CLOB, String.class);
        this.registerJavaType(JDBCType.LONGVARCHAR, String.class);
        this.registerJavaType(JDBCType.NVARCHAR, String.class);
        this.registerJavaType(JDBCType.NCHAR, String.class);
        this.registerJavaType(JDBCType.NCLOB, String.class);
        this.registerJavaType(JDBCType.ARRAY, Object.class);
        this.registerJavaType(JDBCType.BIGINT, Long.class);
        this.registerJavaType(JDBCType.REAL, BigDecimal.class);
        this.registerJavaType(JDBCType.DECIMAL, BigDecimal.class);
        this.registerJavaType(JDBCType.NUMERIC, BigDecimal.class);
        this.registerJavaType(JDBCType.LONGVARBINARY, byte[].class);
        this.registerJavaType(JDBCType.BLOB, byte[].class);
        this.registerJavaType(JDBCType.TIMESTAMP, LocalDateTime.class);
        this.registerJavaType(JDBCType.DATE, LocalDate.class);
        this.registerJavaType(JDBCType.TIME, LocalTime.class);
        this.registerJavaType(JDBCType.OTHER, Object.class);
    }

    /**
     * 注册数据库类型与jdbc类型之间的映射.
     * 每个数据库的类型都是有些许不同的, 这边需要自己实现一个类来进行映射
     */
    protected abstract void registerJdbcTypes();

    @Override
    public void afterPropertiesSet() {
        this.registerJavaTypes();
        this.registerJdbcTypes();
    }

}
