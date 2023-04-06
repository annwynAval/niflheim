package com.annwyn.niflheim.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParamUtils {

	/**
	 * 拼接包名
	 * @param packages .
	 * @return .
	 */
	public static String joinPackage(String... packages) {
		Conditions.checkExpression(Objects.isNull(packages) || packages.length == 0, "包名不能为空");
		return Arrays.stream(packages).filter(StringUtils::hasText).collect(Collectors.joining("."));
	}


}
