package kr.loplab.gnss05.net;

/**
 * Data required for cors login
 * 
 * @author wangjun
 * 
 */
public class DiffDataInfo {
	public String ip = "";
	public int port = -1;// 0-65535
	public String userName = "";
	public String passWord = "";
	public String sourcePoint = "";

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSourcePoint() {
		return sourcePoint;
	}

	public void setSourcePoint(String sourcePoint) {
		this.sourcePoint = sourcePoint;
	}

}
