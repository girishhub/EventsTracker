package com.keepworks.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataStorage {

	/** create shared preferences */
	public static SharedPreferences getSharedPreferences(final Context ctx) {
		return ctx.getSharedPreferences(ctx.getPackageName() + "_preferences",
				Activity.MODE_PRIVATE);
	}
	
	/** set user's name */
	public static void setUserName(final Context ctx, String userName) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString("user_name", userName);
		editor.commit();
	}

	/** get user's name */
	public static String getUserName(final Context ctx) {
		return getSharedPreferences(ctx).getString("user_name", null);
	}
}
