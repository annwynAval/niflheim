package com.annwyn.niflheim.generator.models;

import com.annwyn.niflheim.generator.configure.registry.ColumnType;
import lombok.Data;

import java.io.Serializable;
import java.sql.JDBCType;

@Data
public class TableFieldEntity implements Serializable {

	// 从数据库中获取的数据 --------------------------------------------------------------------------------
	/**
	 * 属性名称
	 */
	private String fieldName;

	/**
	 * 是否是主键
	 */
	private boolean primaryKey;

	/**
	 * 是否是自增
	 */
	private boolean autoincrement;

	/**
	 * 注释
	 */
	private String comment;

	/**
	 * 字段类型
	 */
	private JDBCType jdbcType;

	// 从配置中解析生成的数据 --------------------------------------------------------------------------------

	/**
	 * 属性类型
	 */
	private ColumnType propertyType;

	/**
	 * 属性名称
	 */
	private String propertyName;

	/**
	 * 是否是关键字
	 */
	private boolean keyWord;


}
