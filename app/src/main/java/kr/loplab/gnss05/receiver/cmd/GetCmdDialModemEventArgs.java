package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * 3G dial
 * 
 * @author wangjun
 * 
 */
public class GetCmdDialModemEventArgs extends ReceiverCmdEventArgs {

	boolean isDial;

	public GetCmdDialModemEventArgs(EnumReceiverCmd cmd, boolean isDial) {
		super(cmd);
		this.isDial = isDial;
	}

	public boolean isDial() {
		return isDial;
	}
}
