package com.annwyn.niflheim.generator.configure.registry;


import lombok.Getter;

@Getter
public enum ColumnType {

	// 基本类型
	BASE_BYTE("byte", null),
	BASE_SHORT("short", null),
	BASE_CHAR("char", null),
	BASE_INT("int", null),
	BASE_LONG("long", null),
	BASE_FLOAT("float", null),
	BASE_DOUBLE("double", null),
	BASE_BOOLEAN("boolean", null),

	// 包装类型
	BYTE("Byte", null),
	SHORT("Short", null),
	CHARACTER("Character", null),
	INTEGER("Integer", null),
	LONG("Long", null),
	FLOAT("Float", null),
	DOUBLE("Double", null),
	BOOLEAN("Boolean", null),
	STRING("String", null),

	// sql 包下数据类型
	DATE_SQL("Date", "java.sql.Date"),
	TIME("Time", "java.sql.Time"),
	TIMESTAMP("Timestamp", "java.sql.Timestamp"),
	BLOB("Blob", "java.sql.Blob"),
	CLOB("Clob", "java.sql.Clob"),

	// java8 新时间类型
	LOCAL_DATE("LocalDate", "java.time.LocalDate"),
	LOCAL_TIME("LocalTime", "java.time.LocalTime"),
	YEAR("Year", "java.time.Year"),
	YEAR_MONTH("YearMonth", "java.time.YearMonth"),
	LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
	INSTANT("Instant", "java.time.Instant"),

	// 其他杂类
	MAP("Map", "java.util.Map"),
	BYTE_ARRAY("byte[]", null),
	OBJECT("Object", null),
	BIG_INTEGER("BigInteger", "java.math.BigInteger"),
	BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");
	;

	private final String type;

	private final String packageName;

	ColumnType(String type, String packageName) {
		this.type = type;
		this.packageName = packageName;
	}

}
