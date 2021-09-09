package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;


/**
 * when you need to start a base or a rover it's better to close other modem power which you don't need
 * 
 * @author wangjun
 * 
 */
public class GetCmdDisableOtherIOsEventArgs extends ReceiverCmdEventArgs {

	private int mPort;

	public GetCmdDisableOtherIOsEventArgs(EnumReceiverCmd cmd, int port) {
		super(cmd);
		this.mPort = port;
	}

	public int getPort() {
		return mPort;
	}
}
