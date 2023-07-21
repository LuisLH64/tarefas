package com.test.api.util;

public class NumberUtils {
	public static Number getZeroIfNull(Number numero) {
		return numero != null ? numero : 0;
	}
}
