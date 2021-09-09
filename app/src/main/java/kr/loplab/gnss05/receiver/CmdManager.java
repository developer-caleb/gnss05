package kr.loplab.gnss05.receiver;


import java.util.Vector;

import kr.loplab.gnss05.connection.ConnectManager;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * command manager the command can not send all the time they need to have a rest
 * 
 * @author wangjun
 * 
 */
public class CmdManager {
	private SendCmdThread mSendCmdThread;

	private static CmdManager sInstance = null;

	private CmdManager() {
		if (mSendCmdThread == null || !mSendCmdThread.isAlive()) {
			mSendCmdThread = new SendCmdThread(
					"---The Thread Send message to Receiver---");
		}
		mSendCmdThread.setDaemon(true);
		mSendCmdThread.start();
	}

	public static CmdManager getInstance() {
		if (sInstance == null) {
			synchronized (CmdManager.class) {
				if (sInstance == null) {
					sInstance = new CmdManager();
				}
			}
		}
		return sInstance;
	}

	/**
	 * send command
	 * 
	 * @param cmd
	 */
	public void sendCmd(byte[] cmd) {
		sendCmd(new Cmd(cmd));
	}

	/**
	 * 명령을 보내다
	 * 
	 * @param cmd
	 */
	public void sendCmd(Cmd cmd) {
		if (mSendCmdThread != null && mSendCmdThread.isAlive()) {
			mSendCmdThread.addCmd(cmd);
		}
	}

	/**
	 * The Thread send message to Receriver
	 */
	private class SendCmdThread extends Thread {

		private final Vector<Cmd> mCmds = new Vector<Cmd>();

		private boolean mAllowRun = true;

		public SendCmdThread(String name) {
			super(name);
		}

		public void addCmd(Cmd cmd) {
			synchronized (mCmds) {
				int size = mCmds.size();
				mCmds.add(size, cmd);
				mCmds.notify();
			}
		}

		public int getCmdNumber() {
			return mCmds.size();
		}

		/**
		 * Send a message to the receiver loop
		 */
		@Override
		public void run() {
			try {
				super.run();
				while (mAllowRun && !Thread.interrupted()) {
					Cmd cmd;
					synchronized (mCmds) {
						if (mCmds.size() > 0) {
							cmd = mCmds.remove(0);
						} else {
							try {
								mCmds.wait(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue;
						}
					}
					if (cmd == null) {
						continue;
					}
					write(cmd);

					if (cmd.sleepTime > 0/* 0 */) {
						try {
							Thread.sleep(cmd.sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {// Min interval between two commands is 50ms
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void write(Cmd cmd) {
			if (!ConnectManager.getInstance().isConnected()) {
				return;
			}
			ConnectManager.getInstance().write(cmd.cmd);
		}

	}

	/**
	 * get how many command has send to the receiver
	 * 
	 * @return
	 */
	public int getCmdNumber() {
		if (mSendCmdThread == null) {
			return 0;
		}
		return mSendCmdThread.getCmdNumber();
	}
}
