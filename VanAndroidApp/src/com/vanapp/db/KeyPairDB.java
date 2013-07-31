package com.vanapp.db;

import com.vanapp.bean.ClientOrder;
import com.vanapp.parser.OrderMessageParser;

import android.content.Context;
import android.content.SharedPreferences;

public class KeyPairDB {

	public static final String PREFS_NAME = "com.vanapp.PrefsFile";

	public static String getOrderString(Context ctx) {
		// Restore preferences
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		String driverId = settings.getString("order_string", null);
		return driverId;
	}
	
	public static ClientOrder getOrder(Context ctx) {
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		String order_string = settings.getString("order_string", null);
		ClientOrder order = OrderMessageParser.parseMessage(order_string);
		return order;
	}

	public static void setOrderString(String id, Context ctx) {

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("order_string", id);

		// Commit the edits!
		editor.commit();
	}
	
	public static String getDriverPhone(Context ctx) {
		// Restore preferences
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		String phone = settings.getString("driver_phone", null);
		return phone;
	}

	public static void setDriverPhone(String phone, Context ctx) {

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("driver_phone", phone);

		// Commit the edits!
		editor.commit();
	}
	
	public static String getDriverId(Context ctx) {
		// Restore preferences
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		String phone = settings.getString("driver_id", null);
		return phone;
	}

	public static void setDriverId(String phone, Context ctx) {

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("driver_id", phone);

		// Commit the edits!
		editor.commit();
	}
}
