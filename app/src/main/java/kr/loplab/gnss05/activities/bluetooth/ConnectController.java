package kr.loplab.gnss05.activities.bluetooth;

import android.content.Context;

import kr.loplab.gnss05.connection.ConnectManager;


public class ConnectController {

	public boolean isConnect() {
		return ConnectManager.getInstance().isConnected();
	}

	public void connect(Context context) {
		ConnectManager.getInstance().connect(context);
	}

	public void disConnect() {
		ConnectManager.getInstance().disConnect();
	}

}
