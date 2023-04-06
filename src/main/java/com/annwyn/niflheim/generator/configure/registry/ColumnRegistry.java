package com.annwyn.niflheim.generator.configure.registry;

import org.springframework.beans.factory.InitializingBean;

import java.sql.JDBCType;

public interface ColumnRegistry extends InitializingBean {

	/**
	 * jdbc -> java
	 * @param jdbcType .
	 * @return .
	 */
	ColumnType getColumnType(JDBCType jdbcType);

}
