package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.DopsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * get pdopMask
 * 
 * @author wangjun
 * 
 */
public class GetGnssDopsEventArgs extends ReceiverDataEventArgs {

	private DopsInfo mDopsInfo;

	public GetGnssDopsEventArgs(EnumReceiverCmd cmd, DopsInfo info) {
		super(cmd);
		this.mDopsInfo = info;
	}

	public DopsInfo getDopsInfo() {
		return mDopsInfo;
	}
}
