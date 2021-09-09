package kr.loplab.gnss05.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;


import androidx.annotation.Nullable;

import java.util.Set;

import kr.loplab.gnss05.connection.IConnectionCallback;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * Bluetooth Manager Created by wangjun .
 */
public class MyBluetoothManager {
	private static MyBluetoothManager instance = null;
	private BluetoothAdapter mBtAdapter = null;
	private BluetoothChatService mChatService = null;

	private String mBtName = "";

	private MyBluetoothManager() {
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		mChatService = BluetoothChatService.getInstance();
	}

	public static MyBluetoothManager getInstance() {
		if (instance == null) {
			synchronized (MyBluetoothManager.class) {
				if (instance == null) {
					instance = new MyBluetoothManager();
				}
			}
		}
		return instance;
	}

	public boolean isBtEnable() {
		return mBtAdapter.isEnabled();
	}

	/**
	 * isBtMacBonded
	 * 
	 * @param mstrCurBtMac
	 *            Bluetooth MAC，example“00:11:22:AA:BB:CC”
	 * @return boolean
	 */
	public boolean isBtMacBonded(String mstrCurBtMac) {
		BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			return true;
		}
		Set<BluetoothDevice> set = mBtAdapter.getBondedDevices();
		for (BluetoothDevice dev : set) {
			if (dev.getAddress().equalsIgnoreCase(mstrCurBtMac)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param curBtName
	 *            String
	 * @return boolean
	 */
	public boolean isBtNameBonded(String curBtName) {
		BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			return false;
		}
		Set<BluetoothDevice> set = mBtAdapter.getBondedDevices();
		for (BluetoothDevice dev : set) {
			if (dev.getName().equalsIgnoreCase(curBtName)) {
				return true;
			}
		}
		return false;
	}

	public String getMacByName(String btName) {
		Set<BluetoothDevice> pairedDevices = BluetoothAdapter
				.getDefaultAdapter().getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().equalsIgnoreCase(btName)) {
					return device.getAddress();
				}
			}
		}
		return null;
	}

	@Nullable
	public BluetoothDevice getBluetoothDevice(String btName) {
		Set<BluetoothDevice> pairedDevices = BluetoothAdapter
				.getDefaultAdapter().getBondedDevices();
		if (pairedDevices != null && pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				if (device.getName().equalsIgnoreCase(btName)) {
					return device;
				}
			}
		}
		return null;
	}

	/**
	 * BlueToothConnect
	 * 
	 * @return
	 */
	public boolean connect() {
		if (TextUtils.isEmpty(mBtName) || !isBtNameBonded(mBtName)) {
			mChatService.backConnectionState(false);
			return false;
		}
		String strAddress = "";
		Set<BluetoothDevice> devices = mBtAdapter.getBondedDevices();
		for (BluetoothDevice device_i : devices) {
			if (device_i.getName().equals(mBtName)) {
				strAddress = device_i.getAddress();
				break;
			}
		}
		BluetoothDevice device = mBtAdapter.getRemoteDevice(strAddress);
		return mChatService.connect(device, true);
	}

	public void btDisconnect() {
		if (mChatService != null) {
			mChatService.disConnect();
		}
	}

	public boolean SendCmd(Cmd cmd) {
		return mChatService.sendCmd(cmd);
	}

	public void setConnectCallback(IConnectionCallback connectCallback) {
		mChatService.setConnectCallback(connectCallback);
	}

	public String getBlueName() {
		return mBtName;
	}

	public void setBlueName(String btName) {
		mBtName = btName;
	}

}
