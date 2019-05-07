package mars.nomad.com.a0_common.DataBase.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedObject {
	public static final String APP_CODE = "property";

	public static String getProperty_string(Context acontext, String property, String def) {
		String result = "";
		SharedPreferences sharedPreferences = acontext.getSharedPreferences(APP_CODE, 0);
		result = sharedPreferences.getString(property, def);
		return result;
	}

	public static int getProperty_int(Context acontext, String property, int def) {
		int result = 0;
		SharedPreferences sharedPreferences = acontext.getSharedPreferences(APP_CODE, 0);
		result = sharedPreferences.getInt(property, def);
		return result;
	}

	public static long getProperty_long(Context acontext, String property, long def) {
		long result = 0;
		SharedPreferences sharedPreferences = acontext.getSharedPreferences(APP_CODE, 0);
		result = sharedPreferences.getLong(property, def);
		return result;
	}

	public static boolean getProperty_boolean(Context acontext, String property, boolean def) {
		boolean result = false;
		SharedPreferences sharedPreferences = acontext.getSharedPreferences(APP_CODE, 0);
		result = sharedPreferences.getBoolean(property, def);
		return result;
	}

	public static void setProperty_string(Context acontext, String property, String value) {
		SharedPreferences pref = acontext.getSharedPreferences(APP_CODE, 0);
		Editor ed = pref.edit();
		ed.putString(property, value);
		ed.commit();
	}

	public static void setProperty_int(Context acontext, String property, int value) {
		SharedPreferences pref = acontext.getSharedPreferences(APP_CODE, 0);
		Editor ed = pref.edit();
		ed.putInt(property, value);
		ed.commit();
	}

	public static void setProperty_long(Context acontext, String property, long value) {
		SharedPreferences pref = acontext.getSharedPreferences(APP_CODE, 0);
		Editor ed = pref.edit();
		ed.putLong(property, value);
		ed.commit();
	}

	public static void setProperty_boolean(Context acontext, String property, Boolean value) {
		SharedPreferences pref = acontext.getSharedPreferences(APP_CODE, 0);
		Editor ed = pref.edit();
		ed.putBoolean(property, value);
		ed.commit();
	}
}
