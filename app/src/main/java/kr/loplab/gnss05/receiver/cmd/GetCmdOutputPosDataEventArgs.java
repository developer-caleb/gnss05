package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumDataFrequency;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 *
 * 
 * @author wangjun
 * 
 */
public class GetCmdOutputPosDataEventArgs extends ReceiverCmdEventArgs {

	private EnumDataFrequency mRtkFrequency;

	public GetCmdOutputPosDataEventArgs(EnumReceiverCmd cmd,
                                        EnumDataFrequency rtkFrequency) {
		super(cmd);
		this.mRtkFrequency = rtkFrequency;
	}

	public EnumDataFrequency getEnumDataFrequency() {
		return mRtkFrequency;
	}

}