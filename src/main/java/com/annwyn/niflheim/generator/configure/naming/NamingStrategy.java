package com.annwyn.niflheim.generator.configure.naming;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface NamingStrategy {

	/**
	 * 转换类名
	 * @param name .
	 * @return .
	 */
	String switchClassName(String name);

	/**
	 * 转换属性名
	 * @param name .
	 * @return .
	 */
	String switchFieldName(String name);

	/**
	 * 去掉指定的前缀
	 * @param name 表名称
	 * @param prefixes .
	 * @return .
	 */
	default String removePrefix(String name, List<String> prefixes) {
		if(!StringUtils.hasText(name)) {
			return "";
		}
		for (String prefix : prefixes) {
			if(!name.startsWith(prefix)) {
				continue;
			}
			name = name.substring(prefix.length());
		}
		return name;
	}

	/**
	 * 去掉指定的后缀
	 *
	 * @param name   表名
	 * @param suffixes .
	 * @return .
	 */
	default String removeSuffix(String name, List<String> suffixes) {
		if(!StringUtils.hasText(name)) {
			return "";
		}
		for (String suffix : suffixes) {
			if(!name.endsWith(suffix)) {
				continue;
			}
			name = name.substring(0, name.length() - suffix.length());
		}
		return name;
	}

	/**
	 * 去掉下划线转换成驼峰
	 * @param name .
	 * @return .
	 */
	default String underlineToCamel(String name) {
		if(!StringUtils.hasText(name)) {
			return "";
		}

		return Arrays.stream(name.split("_")) //
			.map(this::firstToUpperCase) //
			.collect(Collectors.joining(""));
	}

	/**
	 * 首字母小写
	 * @param name .
	 * @return .
	 */
	default String firstToLowerCase(String name) {
		if(!StringUtils.hasText(name)) {
			return "";
		}
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	/**
	 * 首字母大写
	 * @param name .
	 * @return .
	 */
	default String firstToUpperCase(String name) {
		if(!StringUtils.hasText(name)) {
			return "";
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
