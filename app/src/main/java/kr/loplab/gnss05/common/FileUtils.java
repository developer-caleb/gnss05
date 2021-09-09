package kr.loplab.gnss05.common;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * utils for the file
 * 
 * @author wangjun
 * 
 */
public class FileUtils {
	/**
	 * check out if file exist
	 */
	public static boolean existFile(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * copy one file from the assests
	 * 
	 * @param srcFileName
	 *           example:abc.txt
	 * @param dirDest
	 *            example：E:/phsftp/dest
	 * @throws IOException
	 */
	public static void copyFileFromAssets(Context context, String srcFileName,
			String dirDest) throws IOException {
		copyFileFromAssets(context, srcFileName, dirDest, srcFileName);
	}

	/**
	 * copy one file from the assests
	 * 
	 * @param srcFileName
	 *            example:abc.txt
	 * @param dirDest
	 *            example:E:/phsftp/dest
	 * @param rename
	 *            ReName
	 * @throws IOException
	 */
	public static void copyFileFromAssets(Context context, String srcFileName,
			String dirDest, String rename) throws IOException {
		mkdirs(dirDest);
		OutputStream myOutput = new FileOutputStream(dirDest + "/" + rename);
		InputStream myInput = context.getAssets().open(srcFileName);
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myInput.close();
		myOutput.close();
	}

	/**
	 * create file direction
	 * 
	 * @param dir
	 *            file's path
	 * @return true file create success/file had already exist
	 */
	public static boolean mkdirs(String dir) {
		File dirPath = new File(dir);
		return dirPath.exists() || dirPath.mkdirs();

	}
}
