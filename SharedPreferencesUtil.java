package com.example.app_dev.healthcare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

	// Shared preferences String
	public static final String LAST_DEVICE_ADRESS = "last_device_adress";
	public static final String LAST_LIVING_LOCATION = "last_living_location";

	// put value
	public static synchronized void put(Context context, String key, String value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString(key, value);
		editor.commit();
	}

	public static synchronized void put(Context context, String key, boolean value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean(key, value);
		editor.commit();
	}

	public static synchronized void put(Context context, String key, int value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();

		editor.putInt(key, value);
		editor.commit();
	}
	
	
	// get value
	public static synchronized String getValue(Context context, String key, String dftValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

		try {
			return pref.getString(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}

	}
	
	public static synchronized int getValue(Context context, String key, int dftValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

		try {
			return pref.getInt(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}

	}

	public static synchronized boolean getValue(Context context,String key, boolean dftValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

		try {
			return pref.getBoolean(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}
	}
	
	public static synchronized void clear(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
}
