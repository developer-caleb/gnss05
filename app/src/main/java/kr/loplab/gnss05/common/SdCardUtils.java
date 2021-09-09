package kr.loplab.gnss05.common;

import android.os.Environment;

import java.io.File;

public class SdCardUtils {
	/**
     * get path from the sd card
     */
    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }
}
