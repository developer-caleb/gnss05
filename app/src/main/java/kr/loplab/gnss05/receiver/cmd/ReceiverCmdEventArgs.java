package kr.loplab.gnss05.receiver.cmd;


import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * base class to send command to the receiver
 */
public class ReceiverCmdEventArgs implements IReceiverCmdEventArgs {
	private EnumReceiverCmd cmd;

	public ReceiverCmdEventArgs(EnumReceiverCmd cmd) {
		this.cmd = cmd;
	}

	@Override
	public EnumReceiverCmd getCmdType() {
		return cmd;
	}

}
