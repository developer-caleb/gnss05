package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumModemCommunicationMode;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * update communication modem
 * 
 * @author wangjun
 * 
 */
public class GetCmdUpdateModemCommunicationModeEventArgs extends
		ReceiverCmdEventArgs {

	EnumModemCommunicationMode mMode;

	public GetCmdUpdateModemCommunicationModeEventArgs(EnumReceiverCmd cmd,
                                                       EnumModemCommunicationMode mode) {
		super(cmd);
		this.mMode = mode;
	}

	public EnumModemCommunicationMode getMode() {
		return mMode;
	}
}
