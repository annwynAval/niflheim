package com.annwyn.niflheim.generator.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class TableEntity implements Serializable {

	// 从数据库中获取的数据 --------------------------------------------------------------------------------
	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 表注释
	 */
	private String comment;

	/**
	 * schemaName
	 */
	private String schemaName;

	/**
	 * 表字段信息
	 */
	private List<TableFieldEntity> fields;

	// 从配置中解析生成的数据 --------------------------------------------------------------------------------
	/**
	 * 主键信息
	 */
	private TableFieldEntity primaryKey;
}
