package kr.loplab.gnss05.common;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * util for the bytes
 * 
 * @author wangjun
 * 
 */
public class ByteUtils {

	public static byte[] get(byte[] array, int offset) {
		return get(array, offset, array.length - offset);
	}

	/**
	 * Gets the subarray of length <tt>length</tt> from <tt>array</tt> that
	 * starts at <tt>offset</tt>.
	 */
	public static byte[] get(byte[] array, int offset, int length) {
		if (offset + length > array.length) {
			byte[] result = new byte[array.length - offset];
			System.arraycopy(array, offset, result, 0, array.length - offset);
			return result;
		} else {
			byte[] result = new byte[length];
			System.arraycopy(array, offset, result, 0, length);
			return result;
		}
	}

	/**
	 * Dec->Hex
	 * 
	 * @param data
	 *            Dec string
	 * @return hex string
	 */
	public static String decimalArray2Hex(byte[] data) {
		if (data == null || data.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (byte bt : data) {
			String str = Integer.toHexString(bt);
			if (str.length() == 1) {
				str = "0" + str;
			}
			builder.append(str);
		}
		return builder.toString();
	}

	public static boolean checkIP(String ipString) {
		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipString);
		return matcher.matches();
	}

	public static String getString_UTF8(byte[] bytes) {
		return bytes == null ? null : getString(bytes, "UTF-8");
	}

	public static String getString(byte[] bytes, String charsetName) {
		return new String(bytes, Charset.forName(charsetName));
	}

}
