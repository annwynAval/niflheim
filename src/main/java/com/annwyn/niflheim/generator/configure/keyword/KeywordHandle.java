package com.annwyn.niflheim.generator.configure.keyword;

import java.util.List;


/**
 * 关键字处理
 */
public interface KeywordHandle {

	/**
	 * 获取关键字
	 *
	 * @return ,
	 */
	List<String> getKeyWords();

	/**
	 * 是否为关键字
	 *
	 * @param columnName .
	 * @return .
	 */
	default boolean isKeyWords(String columnName) {
		return this.getKeyWords().contains(columnName.toUpperCase());
	}

	/**
	 * 格式化关键字格式
	 *
	 * @return .
	 */
	String formatStyle();

	/**
	 * 格式化字段
	 *
	 * @param columnName .
	 * @return .
	 */
	default String formatColumn(String columnName) {
		return String.format(this.formatStyle(), columnName);
	}

}
