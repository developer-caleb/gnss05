package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ReceiverInfo;

/**
 * get the receiver info
 * 
 * @author wangjun
 * 
 */
public class GetReceiverInfoEventArgs extends ReceiverDataEventArgs {

	private ReceiverInfo mReceiverInfo;

	public GetReceiverInfoEventArgs(EnumReceiverCmd cmd, ReceiverInfo info) {
		super(cmd);
		this.mReceiverInfo = info;
	}

	public ReceiverInfo getInfo() {
		return mReceiverInfo;
	}
}
