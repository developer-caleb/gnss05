package kr.loplab.gnss05.receiver.entity;

/**
 * command Created by wangjun on 2015/10/29.
 */
public class Cmd {
	public/* CHC_UNIT_UINT8 */byte[] cmd = new byte[1/* 512 */];
	public/* CHC_UNIT_UINT32 */int sleepTime = 0;
	public/* CHC_UNIT_UINT32 */int cmdLength = 0;

	public Cmd() {
		for (int i = 0; i < cmd.length; i++) {
			cmd[i] = 0;
		}
		sleepTime = 50;
		cmdLength = 1;
	}

	public Cmd(byte[] szBuf) {
		set(szBuf, 50, 0);
	}

	public Cmd(byte[] szBuf, int iSleep, int iLen) {
		set(szBuf, iSleep, iLen);
	}

	public Cmd(Cmd cmd) {
		this(cmd.cmd, cmd.sleepTime, cmd.cmdLength);
	}

	public void set(byte[] szBuf, int iSleep, int iLen) {
		cmd = szBuf;
		sleepTime = iSleep;
		cmdLength = iLen;
	}
}
