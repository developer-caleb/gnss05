package kr.loplab.gnss05.common;

/**
 * the util to let the thread sleep
 * 
 * @author wangjun
 * 
 */
public class SleepUtils {

	/**
	 * sleep the thread
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			L.printException(e);
		}
	}

}
