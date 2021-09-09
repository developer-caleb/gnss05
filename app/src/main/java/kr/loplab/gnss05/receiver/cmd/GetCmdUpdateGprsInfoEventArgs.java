package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.GprsInfo;

public class GetCmdUpdateGprsInfoEventArgs extends ReceiverCmdEventArgs {

	GprsInfo info;

	public GetCmdUpdateGprsInfoEventArgs(EnumReceiverCmd cmd, GprsInfo info) {
		super(cmd);
		this.info = info;
	}

	public GprsInfo getInfo() {
		return info;
	}
}
