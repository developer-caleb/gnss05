package kr.loplab.gnss05.connection.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import kr.loplab.gnss05.common.ByteUtils;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.connection.IConnectionCallback;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * Bluetooth Chat Service Created by wangjun on 2015/10/19.
 */
public class BluetoothChatService {
	private static BluetoothChatService instance = null;
	/**
	 * As mutex lock
	 */
	private final Object obj = new Object();

	private ThreadConnectBt mThreadConnectBt;
	private ThreadReadBt mThreadReadBt;
	private BluetoothSocket mmSocket;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private final BluetoothAdapter mAdapter;

	BluetoothDevice device;
	boolean secure;
	/**
	 * Bluetooth port server
	 */
	private static final UUID SerialPortServiceClass_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private IConnectionCallback mConnectCallback;

	private BluetoothChatService() {
		mAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public static BluetoothChatService getInstance() {
		if (instance == null) {
			synchronized (BluetoothChatService.class) {
				if (instance == null) {
					instance = new BluetoothChatService();
				}
			}
		}
		return instance;
	}

	public boolean connect(BluetoothDevice device, boolean secure) {
		this.device = device;
		this.secure = secure;
		if (device == null) {
			backConnectionState(false);
			return false;
		}
		resetBtThread();
		if (mThreadConnectBt != null) {
			if (mThreadConnectBt.isAlive()) {
				mThreadConnectBt.stopThread();
			}
			mThreadConnectBt = null;
		}
		mThreadConnectBt = new ThreadConnectBt(device, secure, true);
		mThreadConnectBt.start();
		return true;
	}

	private void connected(BluetoothSocket socket) {
		synchronized (obj) {
			mmSocket = socket;
			try {
				mmInStream = mmSocket.getInputStream();
				mmOutStream = mmSocket.getOutputStream();
				mThreadReadBt = new ThreadReadBt(true);
				mThreadReadBt.start();
				L.d("--bt connect succeed--");
				backConnectionState(true);
			} catch (IOException e) {
				e.printStackTrace();
				L.d("--bt connect IOException");
				L.printException(e);
				backConnectionState(false);
			}
		}

	}

	private void connectionLost(boolean mAllowRun) {
		if (mAllowRun) {
			connectionLost();
		} else {
			// backConnectionState(false);
			return;
		}
		L.d("bt connectionLost");
		synchronized (obj) {
			try {
				if (mmSocket != null) {
					mmSocket.close();
					if (mmInStream != null) {
						mmInStream.close();
					}
					if (mmOutStream != null) {
						mmOutStream.close();
					}
					mmSocket = null;
					mmInStream = null;
					mmOutStream = null;
					L.d("connection lost socket closed");
				}
			} catch (Exception e) {
				L.printException(e);
			}
		}
	}

	public void disConnect() {
		beDisConnected();
		resetBtThread();
	}

	/**
	 * disConnect
	 */
	private void resetBtThread() {
		try {
			if (mThreadConnectBt != null) {
				mThreadConnectBt.stopThread();
				mThreadConnectBt.interrupt();
				mThreadConnectBt = null;
			}
		} catch (Exception e) {
			L.d("bt disConnect failure");
			L.printException(e);
		}
		synchronized (obj) {
			L.d("bt resetBtThread");
			if (mThreadReadBt != null) {
				if (mThreadReadBt.isAlive()) {
					mThreadReadBt.stopRun();
				}
				mThreadReadBt = null;
			}

			try {
				if (mmSocket != null) {
					mmSocket.close();
					if (mmInStream != null) {
						mmInStream.close();
					}
					if (mmOutStream != null) {
						mmOutStream.close();
					}
					mmInStream = null;
					mmOutStream = null;
					mmSocket = null;
					L.d("bt Socket closed");
				}
			} catch (Exception e) {
				L.printException(e);
			}
		}
	}

	private class ThreadConnectBt extends Thread {
		private final BluetoothDevice mmDevice;
		private String mSocketType;
		private boolean mbStopThread = false;
		private BluetoothSocket btSocket;

		public ThreadConnectBt(BluetoothDevice device, boolean secure,
				boolean isDeamon) {
			mmDevice = device;
			setDaemon(isDeamon);
			mSocketType = secure ? "Secure" : "Insecure";
		}

		public void stopThread() {
			L.d("Set Bluetooth thread stop!");
			mbStopThread = true;
		}

		/**
		 * try to port connect
		 * 
		 * @return BluetoothSocket
		 */
		@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
		private BluetoothSocket tryCreateBtSocketBySerialPortService() {
			BluetoothSocket tmp = null;
			try {
				if (mmDevice != null) {
					int sdk = Build.VERSION.SDK_INT;
					if (sdk >= 10) {
						tmp = mmDevice
								.createInsecureRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);
					} else {
						tmp = mmDevice
								.createRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);
					}
				}
				L.d("Get a handle to the divice!");
				if (tmp != null) {
					mAdapter.cancelDiscovery();
					tmp.connect();
				}
				L.d("bt connection succeed");
			} catch (Exception e1) {
				L.printException(e1);
				try {
					if (tmp != null) {
						tmp.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					L.printException(e2);
				}
				tmp = null;
			}
			return tmp;
		}

		/**
		 * try to Create BtSocket By Reflect
		 */
		private BluetoothSocket tryCreateBtSocketByReflect() {
			BluetoothSocket tmp = null;
			try {

				if (mAdapter.isDiscovering()) {
					mAdapter.cancelDiscovery();
				}
				Method m = mmDevice.getClass().getMethod("createRfcommSocket",
						int.class);
				for (int i = 1; i < 36; i++) {
					tmp = (BluetoothSocket) m.invoke(mmDevice, i);
					if (tmp != null) {
						L.d("invoke = " + i);
						break;
					}
				}
				if (tmp != null) {
					tmp.connect();
				}
			} catch (Exception e) {
				L.printException(e);
				try {
					if (tmp != null) {
						tmp.close();
					}
				} catch (IOException e4) {
					e4.printStackTrace();
					L.printException(e4);
				}
				tmp = null;
			}
			return tmp;
		}

		public void run() {
			try {
				if (mbStopThread) {
					return;
				}
				L.d("BEGIN mConnectThread SocketType:" + mSocketType);
				setName("ConnectThread" + mSocketType);
				// 1.Try to port connect
				L.d("1.Try to port connect");
				btSocket = tryCreateBtSocketBySerialPortService();
				if (mbStopThread) {
					return;
				}
				// 2.두 번째 방법을 시도하십시오
				if (btSocket == null) {
					L.d("1.Try to reflect connect");
					btSocket = tryCreateBtSocketByReflect();
				}
				if (mbStopThread) {
					backConnectionState(false);
					return;
				}

				if (btSocket == null) {
					L.d("--ThreadConnectBt btSocket is null--");
					backConnectionState(false);
				} else {
					if (mbStopThread) {
						backConnectionState(false);
						return;
					}
					connected(btSocket);
					btSocket = null;
				}
			} catch (Exception e) {
				L.d("ThreadConnectBt:" + e.getMessage());
				backConnectionState(false);
				L.printException(e);
			}
		}
	}

	private class ThreadReadBt extends Thread {

		private boolean mAllowRun = true;

		public ThreadReadBt(boolean isDaemon) {
			super("Thread bt receiver message");
			setDaemon(isDaemon);
			mAllowRun = true;
			L.d("create bluetooth connection succeed!");
		}

		public void run() {
			byte[] buffer = new byte[1024 * 4];
			int len;
			while (mAllowRun) {
				// UtilByte.reset(buffer);
				try {
					if (mmInStream == null) {
						connectionLost(mAllowRun);
						break;
					} else {
						len = mmInStream.read(buffer);
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							L.printException(e);
						}
					}
					if (len < 0) {
						connectionLost(mAllowRun);
						break;
					}
					byte[] bts = ByteUtils.get(buffer, 0, len);
					receiver(bts);
				} catch (Exception e) {
					L.d("Blue is disconnect：" + e);
					connectionLost(mAllowRun);
					L.printException(e);
					break;
				}
			}
		}

		public void stopRun() {
			mAllowRun = false;
		}

		public boolean write(byte[] buffer) {
			try {
				if (mmOutStream != null) {
					mmOutStream.write(buffer);
					mmOutStream.flush();
				}
			} catch (IOException e) {
				L.printException(e);
				return false;
			}
			return true;
		}

		public boolean sendCmd(Cmd cmd) {
			return !(cmd == null || cmd.cmd == null) && write(cmd.cmd);
		}

	}

	public boolean sendCmd(Cmd cmd) {
		return !(mThreadReadBt == null || !mThreadReadBt.isAlive())
				&& mThreadReadBt.sendCmd(cmd);
	}

	public void setConnectCallback(IConnectionCallback connectCallback) {
		mConnectCallback = connectCallback;
	}

	public void backConnectionState(boolean succesed) {
		if (mConnectCallback != null) {
			mConnectCallback.backConnectionState(succesed);
		}
	}

	private void connectionLost() {
		if (mConnectCallback != null) {
			mConnectCallback.connectionLost();
		}
	}

	private void beDisConnected() {
		if (mConnectCallback != null) {
			mConnectCallback.beDisConnected();
		}
	}

	private void receiver(byte[] data) {
		if (mConnectCallback != null) {
			mConnectCallback.receiver(data);
		}
	}
}
