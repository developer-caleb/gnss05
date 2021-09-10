package kr.loplab.gnss05.connection;

import android.content.Context;
import android.util.Log;

import kr.loplab.gnss05.GlobalApplication;
import kr.loplab.gnss05.receiver.UseDemo;
import kr.loplab.gnss05.receiver.entity.Cmd;


public class ConnectManager {
	private String TAG = this.getClass().getSimpleName();
	private static ConnectManager sInstance = null;
	private ConnectionTypes mconnectionType = ConnectionTypes.DEMO;
	private IGnssConnection mGnssConnection;
	private ConnectionStatus mConnectionStatus = ConnectionStatus.DISCONNECT;

	private ConnectManager() {
	}

	public static ConnectManager getInstance() {
		if (sInstance == null) {
			synchronized (ConnectManager.class) {
				if (sInstance == null) {
					sInstance = new ConnectManager();
				}
			}
		}
		return sInstance;
	}

	private void setConnectionStatus(ConnectionStatus status) {
		mConnectionStatus = status;
	}

	public ConnectionStatus getConnectionStatus() {
		return mConnectionStatus;
	}

	/**
	 * begin connecting
	 */
	public boolean isConnected() {
		return mConnectionStatus == ConnectionStatus.CONNECTED;
	}

	public boolean connect(Context context) {
		Log.d(TAG, "connect: 연결 성공할 뻔 실패");
		try {
			Log.d(TAG, "connect: 연결 성공");
			if (mGnssConnection != null) {
				mGnssConnection.disConnect();
				mGnssConnection = null;
			}
			mGnssConnection = GnssConnectionFactory.makeConnection(GnssConnectionWay.BLUETOOTH);
			if (mGnssConnection == null) {
				return false;
			}
			setConnectionStatus(ConnectionStatus.CONNECTTNG);
			return mGnssConnection != null
					&& mGnssConnection.connect(context, mConnectionCallback);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void disConnect() {
		if (mGnssConnection != null) {
			mGnssConnection.disConnect();
			mGnssConnection = null;
		}
		setConnectionStatus(ConnectionStatus.DISCONNECT);
	}

	private IConnectionCallback mConnectionCallback = new IConnectionCallback() {

		@Override
		public void backConnectionState(boolean success) {
			setConnectionStatus(success ? ConnectionStatus.CONNECTED
					: ConnectionStatus.CONNECT_FAILD);
			if (success) {
				// wifi connect success
				UseDemo.runReceiver(GlobalApplication.getInstance());
			} else {
				// wifi break
				UseDemo.stopReceiver();
			}
		}

		@Override
		public void connectionLost() {
			setConnectionStatus(ConnectionStatus.CONNECT_FAILD);
			// 와이파이 연결 끊김
			UseDemo.stopReceiver();
			Log.e(TAG, "connectionLost: nn");
		}

		@Override
		public void beDisConnected() {
			setConnectionStatus(ConnectionStatus.DISCONNECT);
			// wifi disconnected
			UseDemo.stopReceiver();
			Log.e(TAG, "beDisConnected: stop it");
		}

		@Override
		public void receiver(byte[] data) {
			UseDemo.receiveData(data);
		}
	};

	/**
	 * Write data to the receiver, don’t call it directly, only call CmdManager
	 * 
	 * @param data
	 */
	public void write(byte[] data) {
		if (mGnssConnection != null) {
			mGnssConnection.writeDataToDevice(new Cmd(data));
		}
	}

}
