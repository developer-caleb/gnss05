package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.CorsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

public class GetCmdUpdateCorsInfoEventArgs extends ReceiverCmdEventArgs {

	CorsInfo info;

	public GetCmdUpdateCorsInfoEventArgs(EnumReceiverCmd cmd, CorsInfo info) {
		super(cmd);
		this.info = info;
	}

	public CorsInfo getInfo() {
		return info;
	}
}