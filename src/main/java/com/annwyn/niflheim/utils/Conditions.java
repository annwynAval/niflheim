package com.annwyn.niflheim.utils;

import java.util.function.Supplier;

public class Conditions {

	public static void checkExpression(boolean expression, String message) {
		Conditions.checkExpression(expression, () -> new RuntimeException(message));
	}

	public static void checkExpression(boolean expression, Supplier<RuntimeException> supplier) {
		if (expression) {
			throw supplier.get();
		}
	}
}
