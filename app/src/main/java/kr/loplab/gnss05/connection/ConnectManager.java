package kr.loplab.gnss05.connection;

import android.content.Context;

import kr.loplab.gnss05.GlobalApplication;
import kr.loplab.gnss05.receiver.UseDemo;
import kr.loplab.gnss05.receiver.entity.Cmd;


public class ConnectManager {

	private static ConnectManager sInstance = null;

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
		try {
			if (mGnssConnection != null) {
				mGnssConnection.disConnect();
				mGnssConnection = null;
			}
			mGnssConnection = GnssConnectionFactory
					.makeConnection(GnssConnectionWay.BLUETOOTH);
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
		public void backConnectionState(boolean succesed) {
			setConnectionStatus(succesed ? ConnectionStatus.CONNECTED
					: ConnectionStatus.CONNECT_FAILD);
			if (succesed) {
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
			// wifi断开
			UseDemo.stopReceiver();
		}

		@Override
		public void beDisConnected() {
			setConnectionStatus(ConnectionStatus.DISCONNECT);
			// wifi断开
			UseDemo.stopReceiver();
		}

		@Override
		public void receiver(byte[] data) {
			UseDemo.receiveData(data);
		}
	};

	/**
	 * 给接收机写数据，不要直接调用，只能让CmdManager调用
	 * 
	 * @param data
	 */
	public void write(byte[] data) {
		if (mGnssConnection != null) {
			mGnssConnection.writeDataToDevice(new Cmd(data));
		}
	}

}