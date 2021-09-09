package kr.loplab.gnss05.connection;

public interface IConnectionCallback {
	/**
	 * connection status changed
	 * 
	 * @param succesed
	 *            true:connect success;false:connect fail
	 */
	void backConnectionState(boolean succesed);

	/**
	 * break when connecting
	 */
	void connectionLost();

	/**
	 * break as user
	 */
	void beDisConnected();

	/**
	 * receive data into the memory
	 * 
	 * @param data
	 *            data
	 */
	void receiver(byte[] data);
}
