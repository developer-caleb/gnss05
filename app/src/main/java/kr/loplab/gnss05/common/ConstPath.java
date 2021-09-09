package kr.loplab.gnss05.common;

import java.io.File;

public class ConstPath {

	public static String getAppDataFolder() {
		return SdCardUtils.getSdCardPath() + "sdk" + File.separator;
	}

	public static String getConfigFolder() {
		return getAppDataFolder() + "Config" + File.separator;
	}

	public static String getFeaturesName() {
		return "features.hcc";
	}

	public static String getFeaturesPath() {
		return getConfigFolder() + getFeaturesName();
	}

	/**
	 * 注册配置文件路径
	 * 
	 * @return
	 */
	public static String getRsaUserPath() {
		return getConfigFolder() + getRsaUserName();
	}

	public static String getRsaUserName() {
		return "RSAUser.xml";
	}
}
