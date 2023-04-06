package com.annwyn.niflheim.generator.configure.registry;

import java.sql.JDBCType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractColumnRegistry implements ColumnRegistry {

	protected final Map<JDBCType, ColumnType> jdbcTypeCaches = new ConcurrentHashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		this.jdbcTypeCaches.put(JDBCType.BINARY, ColumnType.BYTE_ARRAY);
		this.jdbcTypeCaches.put(JDBCType.BLOB, ColumnType.BYTE_ARRAY);
		this.jdbcTypeCaches.put(JDBCType.LONGVARBINARY, ColumnType.BYTE_ARRAY);
		this.jdbcTypeCaches.put(JDBCType.VARBINARY, ColumnType.BYTE_ARRAY);
		//byte
		this.jdbcTypeCaches.put(JDBCType.TINYINT, ColumnType.BYTE);
		//long
		this.jdbcTypeCaches.put(JDBCType.BIGINT, ColumnType.LONG);
		//boolean
		this.jdbcTypeCaches.put(JDBCType.BIT, ColumnType.BOOLEAN);
		this.jdbcTypeCaches.put(JDBCType.BOOLEAN, ColumnType.BOOLEAN);
		//short
		this.jdbcTypeCaches.put(JDBCType.SMALLINT, ColumnType.SHORT);
		//string
		this.jdbcTypeCaches.put(JDBCType.CHAR, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.CLOB, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.VARCHAR, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.LONGVARCHAR, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.LONGNVARCHAR, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.NCHAR, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.NCLOB, ColumnType.STRING);
		this.jdbcTypeCaches.put(JDBCType.NVARCHAR, ColumnType.STRING);
		//date
		this.jdbcTypeCaches.put(JDBCType.DATE, ColumnType.LOCAL_DATE);
		this.jdbcTypeCaches.put(JDBCType.TIME, ColumnType.LOCAL_TIME);
		this.jdbcTypeCaches.put(JDBCType.TIMESTAMP, ColumnType.LOCAL_DATE_TIME);
		//double
		this.jdbcTypeCaches.put(JDBCType.FLOAT, ColumnType.DOUBLE);
		this.jdbcTypeCaches.put(JDBCType.REAL, ColumnType.DOUBLE);
		//int
		this.jdbcTypeCaches.put(JDBCType.INTEGER, ColumnType.INTEGER);
		//bigDecimal
		this.jdbcTypeCaches.put(JDBCType.NUMERIC, ColumnType.BIG_DECIMAL);
		this.jdbcTypeCaches.put(JDBCType.DECIMAL, ColumnType.BIG_DECIMAL);
	}

	public ColumnType getColumnType(JDBCType jdbcType) {
		return this.jdbcTypeCaches.get(jdbcType);
	}

}
