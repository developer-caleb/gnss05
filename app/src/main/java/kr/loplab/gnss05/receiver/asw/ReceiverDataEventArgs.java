package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 *  all the classwith the param parsed by the command from receiver need this
 */
public class ReceiverDataEventArgs implements IReceiverDataEventArgs {

	private EnumReceiverCmd cmd;

	public ReceiverDataEventArgs(EnumReceiverCmd cmd) {
		this.cmd = cmd;
	}

	@Override
	public EnumReceiverCmd getDataType() {
		return cmd;
	}
}
