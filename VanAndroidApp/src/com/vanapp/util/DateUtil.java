package com.vanapp.util;

import java.sql.Timestamp;

public class DateUtil {
	public static Timestamp toTimestamp(String s) {
		return new Timestamp(System.currentTimeMillis());
	}
	public static String fromTimestamp(Timestamp s) {
		return s.toString();
	} 
}
