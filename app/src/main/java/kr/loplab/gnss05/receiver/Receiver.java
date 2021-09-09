package kr.loplab.gnss05.receiver;

import android.content.Context;

import com.chc.gnss.sdk.CHC_CMDRef;
import com.chc.gnss.sdk.CHC_COMMUNICATION_TYPE;
import com.chc.gnss.sdk.CHC_OEM_TYPE;
import com.chc.gnss.sdk.CHC_RECEIVER_TYPE;
import com.chc.gnss.sdk.CHC_Receiver;
import com.chc.gnss.sdk.CHC_ReceiverRef;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import kr.loplab.gnss05.GlobalApplication;
import kr.loplab.gnss05.common.FileUtils;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.common.SleepUtils;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * to parse the receiver you need this
 * 
 * @author wangjun
 * 
 */
public class Receiver {

	@SuppressWarnings("unused")
	private Context mContext;

	/**
	 * if initliaze
	 */
	private boolean mIsRun = false;

	private CHC_RECEIVER_TYPE mReceiverType = CHC_RECEIVER_TYPE.CHC_RECEIVER_TYPE_SMART_GNSS;

	private CHC_OEM_TYPE mOemType = CHC_OEM_TYPE.CHC_OEM_TYPE_AUTO;

	private CHC_ReceiverRef mReceiverRef;

	/**
	 * lock
	 */
	private final Object lock = new Object();

	/**
	 * the thread to parse the data
	 */
	private ParseThread mParseThread;

	/**
	 * a timer used to send command
	 */
	private Timer mQueryDataTimer;

	private static final long QUERY_TIME = 10000;

	private static Receiver sInstance = null;

	private Receiver() {
		String file = getFeaturesPath();
		if (!FileUtils.existFile(file)) {
			return;
		}
		mReceiverRef = new CHC_ReceiverRef(file, mReceiverType, mOemType);
	}

	public static Receiver getInstance() {
		if (sInstance == null) {
			synchronized (Receiver.class) {
				if (sInstance == null) {
					sInstance = new Receiver();
				}
			}
		}
		return sInstance;
	}

	/**
	 * run sdk: 1.set file path and Receiver manufacturers 2.get command,send to
	 * Receiver.
	 */
	public boolean run(Context context) {
		this.mContext = context;
		try {
			if (mIsRun) {
				stop();
			}
			ReceiverCmdProxy.sendCmd(new Cmd("MTKSPPForMMI".getBytes()));
			CHC_CMDRef cmdRef = new CHC_CMDRef();
			try {
				CHC_Receiver.CHCGetCmdUpdateCommunicationType(mReceiverRef,
						CHC_COMMUNICATION_TYPE.CHC_COMMUNICATION_TYPE_INDIRECT,
						cmdRef);
				if (CHC_Receiver.CHCGetCmdInitConnection(mReceiverRef, cmdRef) < 0) {
					cmdRef.delete();
					return false;
				}
			} catch (Exception e) {
				cmdRef.delete();
				L.e("runSdk:", e.getMessage());
				L.printException(e);
				return false;
			}
			ReceiverCmdProxy.sendCmds(cmdRef.getCmds());
			cmdRef.delete();
			mIsRun = true;
			startQueryDataTimer();
			return mIsRun;
		} catch (Exception e) {
			L.printException(e);
			stop();
			return false;
		}
	}

	/**
	 * return the comfiguration's path if their doesn't have copy from the other place
	 * 
	 * @return
	 */
	private String getFeaturesPath() {
		String file = ConstPath.getFeaturesPath();
		if (!FileUtils.existFile(file)) {
			try {
				FileUtils.copyFileFromAssets(GlobalApplication.getInstance(),
						ConstPath.getFeaturesName(),
						ConstPath.getConfigFolder());
			} catch (IOException e) {
				L.printException(e);
			}
		}
		return file;
	}

	/**
	 * 接收数据
	 * 
	 * @param bts
	 *            data
	 */
	public void receiveData(byte[] bts) {
		try {
			if (bts == null) {
				return;
			}
			if (!mIsRun) {
				return;
			}
			if (mParseThread == null || !mParseThread.isAlive()) {
				L.d("threadParseData err");
				(mParseThread = new ParseThread("---ThreadParseData---", true))
						.start();
			}
			mParseThread.addData(bts);
		} catch (Exception e) {
			L.printException(e);
		}
	}

	/**
	 * stop parse the data from the receiver
	 */
	public void stop() {
		try {
			if (!mIsRun) {
				return;
			}
			mIsRun = false;
//			synchronized (lock) {
//				if (mReceiverRef != null) {
//					mReceiverRef.delete();
//					mReceiverRef = null;
//				}
//			}

			if (mParseThread != null && mParseThread.isAlive()) {
				mParseThread.pause();
				mParseThread = null;
			}
			stopQueryDataTimer();
			L.d("stop SDK");
		} catch (Exception e) {
			L.printException(e);
		}
	}

	/**
	 * a timer to send data
	 */
	private void startQueryDataTimer() {
		if (mQueryDataTimer != null) {
			mQueryDataTimer.cancel();
			mQueryDataTimer = null;
		}
		mQueryDataTimer = new Timer("--Keeplive of SDK！--", true);
		mQueryDataTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (!mIsRun) {
						return;
					}
					synchronized (getLock()) {
						sendAsk200Data();
						
						ReceiverCmdProxy.BUS
								.post(new ReceiverCmdEventArgs(
										EnumReceiverCmd.RECEIVER_CMD_GET_RECEIVER_INFO));
						ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
								EnumReceiverCmd.RECEIVER_CMD_GET_BATTERYLIFE));
						ReceiverCmdProxy.BUS
								.post(new ReceiverCmdEventArgs(
										EnumReceiverCmd.RECEIVER_CMD_GET_FILERECORD_STATUS));
						ReceiverCmdProxy.BUS
								.post(new ReceiverCmdEventArgs(
										EnumReceiverCmd.RECEIVER_CMD_GET_FILERECORD_PARAM));
						ReceiverCmdProxy.BUS
								.post(new ReceiverCmdEventArgs(
										EnumReceiverCmd.RECEIVER_CMD_GET_GNSS_BASEPARAM));
						ReceiverCmdProxy.BUS
								.post(new ReceiverCmdEventArgs(
										EnumReceiverCmd.RECEIVER_CMD_GET_MODEM_GSM_DIAL_STATUS));
					}
				} catch (Exception e) {
					L.printException(e);
				}
			}
		}, QUERY_TIME, QUERY_TIME);
	}

	private void sendAsk200Data() {
		if (mReceiverType == CHC_RECEIVER_TYPE.CHC_RECEIVER_TYPE_CHC) {
			CHC_CMDRef cmdRef = new CHC_CMDRef();
			CHC_Receiver.CHCAskForMoreData(mReceiverRef, (short) 1, cmdRef);
			ReceiverCmdProxy.sendCmds(cmdRef.getCmds());
			cmdRef.delete();
		}
	}

	/***
	 * stop query the data from the receiver
	 */
	private void stopQueryDataTimer() {
		if (mQueryDataTimer != null) {
			mQueryDataTimer.cancel();
			mQueryDataTimer = null;
		}
	}

	/**
	 * the thread of parse data
	 * 
	 * @author wangjun
	 */
	private class ParseThread extends Thread {

		private final Vector<byte[]> mDatas = new Vector<byte[]>();

		private boolean mAllowRun = true;

		public ParseThread(String string, boolean isDeamon) {
			super(string);
			setDaemon(isDeamon);
		}

		public void addData(byte[] bts) {
			synchronized (mDatas) {// Role: 1.read and write protection for vbuf
									// 2.Wake parsing thread
				mDatas.add(bts);
			}
		}

		public void pause() {
			mAllowRun = false;
		}

		@Override
		public void run() {
			try {
				byte[] bts;
				do {
					while (mAllowRun) {
						synchronized (mDatas) {
							if (mDatas.size() > 0) {
								bts = mDatas.remove(0);
							} else {
								break;
							}
						}
						if (mAllowRun && bts != null && mIsRun) {
							receiveData(bts);
						}
					}
					try {
						synchronized (lock) {
							while (mIsRun
									&& mReceiverRef != null
									&& (CHC_Receiver.CHCParseData(mReceiverRef) > 0)) {
								ReceiverDataParse.getInstance().parseData();
							}
						}
					} catch (Exception e) {
						L.e("ReceiverManager",
								"ThreadParseData:CHCParseData err");
						L.printException(e);
						break;
					}
					SleepUtils.sleep(100);
				} while (mAllowRun);
			} catch (Exception e) {
				L.printException(e);
			}
		}

		private void receiveData(byte[] bts) {
			try {
				synchronized (lock) {
					// send data to SDK
					if (mReceiverRef != null) {
						CHC_Receiver.CHCReceiveData(mReceiverRef, bts);
					}
				}
			} catch (Exception e) {
				L.e("ParseThread:", e.getMessage());
				L.printException(e);
			}
		}
	}

	public boolean isIsRun() {
		return mIsRun;
	}

	public CHC_ReceiverRef getReceiverRef() {
		return mReceiverRef;
	}

	public Object getLock() {
		return lock;
	}
}
