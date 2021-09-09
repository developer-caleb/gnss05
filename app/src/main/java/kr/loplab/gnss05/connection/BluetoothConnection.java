package kr.loplab.gnss05.connection;

import android.content.Context;

import kr.loplab.gnss05.connection.bluetooth.MyBluetoothManager;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * connect to the bluetooth
 */
public class BluetoothConnection implements IGnssConnection {
	@Override
	public boolean connect(Context context, IConnectionCallback callback) {
		MyBluetoothManager.getInstance().setConnectCallback(callback);
		return MyBluetoothManager.getInstance().connect();
	}

	@Override
	public void disConnect() {
		MyBluetoothManager.getInstance().btDisconnect();
	}

	@Override
	public boolean writeDataToDevice(Cmd cmd) {
		return MyBluetoothManager.getInstance().SendCmd(cmd);
	}
}
