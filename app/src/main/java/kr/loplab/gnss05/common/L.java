package kr.loplab.gnss05.common;

import android.util.Log;

/**
 * log tool
 * 
 * @author wangjun
 * 
 */
public class L {

	private static final String DEFAULT_TAG = "SDK Demo";

	public static void printException(Exception exception) {
		exception.printStackTrace();
		Log.e(DEFAULT_TAG + "Exception:", exception.getMessage());
	}

	public static void e(String massage) {
		e(DEFAULT_TAG, massage);
	}

	public static void e(String tag, String massage) {
		Log.e(tag, massage);
	}

	public static void d(String massage) {
		d(DEFAULT_TAG, massage);
	}

	public static void d(String tag, String massage) {
		Log.e(tag, massage);
	}
}
