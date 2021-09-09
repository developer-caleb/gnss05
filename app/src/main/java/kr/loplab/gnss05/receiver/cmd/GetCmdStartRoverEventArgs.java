package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.RoverParams;

/**
 * start rover before yyou do that make sure all the params have been set
 * 
 * @author wangjun
 * 
 */
public class GetCmdStartRoverEventArgs extends ReceiverCmdEventArgs {

	private RoverParams mRoverParams;

	public GetCmdStartRoverEventArgs(EnumReceiverCmd cmd,
                                     RoverParams roverParams) {
		super(cmd);
		this.mRoverParams = roverParams;
	}

	public RoverParams getRoverParams() {
		return mRoverParams;
	}
}
