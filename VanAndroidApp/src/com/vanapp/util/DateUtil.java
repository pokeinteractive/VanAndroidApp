package com.vanapp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private final static SimpleDateFormat dateTimeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
	

	public static Date toDate(String sdate) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	

	public static String fromTimestamp(Timestamp sdate) {
		return dateTimeFormater.format(sdate);
	}
	
	public static Timestamp toTimestamp(String sdate) {
		try {
			Date d =  dateTimeFormater.parse(sdate);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
}
