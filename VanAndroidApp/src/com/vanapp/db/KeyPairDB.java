package com.vanapp.db;

import java.io.FileOutputStream;

import android.content.Context;
import android.content.SharedPreferences;

public class KeyPairDB {

	public static final String PREFS_NAME = "com.vanapp.PrefsFile";


//	public static boolean getGPSUpdaterStatus(Context ctx) {
//		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
//		return settings.getBoolean("gps_update", false);
//	}
//
//	public static void setGPSUpdaterStatus(boolean status, Context ctx) {
//
//		// We need an Editor object to make preference changes.
//		// All objects are from android.context.Context
//		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putBoolean("gps_update", status);
//
//		// Commit the edits!
//		editor.commit();
//	}

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

	public static String getDriverAccountBalance(Context ctx) {
		// Restore preferences
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		String phone = settings.getString("acct_balance", "0");
		return phone;
	}

	public static void setDriverAccountBalance(String phone, Context ctx) {

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("acct_balance", phone);

		// Commit the edits!
		editor.commit();
	}
}
