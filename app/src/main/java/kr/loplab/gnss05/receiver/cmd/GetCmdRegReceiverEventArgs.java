package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.RegisterCode;

/**
 * registe the receiver
 * 
 * @author wangjun
 * 
 */
public class GetCmdRegReceiverEventArgs extends ReceiverCmdEventArgs {

	RegisterCode code;

	public GetCmdRegReceiverEventArgs(EnumReceiverCmd cmd, RegisterCode code) {
		super(cmd);
		this.code = code;
	}

	public RegisterCode getCode() {
		return code;
	}
}
