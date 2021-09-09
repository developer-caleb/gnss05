package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.NmeaSetParam;

public class GetCmdOutputNMEAEventArgs extends ReceiverCmdEventArgs {

	NmeaSetParam Param;

	public GetCmdOutputNMEAEventArgs(EnumReceiverCmd cmd, NmeaSetParam Param) {
		super(cmd);
		this.Param = Param;
	}

	public NmeaSetParam getParam() {
		return Param;
	}
}
