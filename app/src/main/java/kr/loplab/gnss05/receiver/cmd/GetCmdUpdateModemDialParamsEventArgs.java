package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialParams;

/**
 * update the gprs params with ip and address
 */
public class GetCmdUpdateModemDialParamsEventArgs extends ReceiverCmdEventArgs {

	public GetCmdUpdateModemDialParamsEventArgs(EnumReceiverCmd cmd,
                                                ModemDialParams apn) {
		super(cmd);
		this.dataServerApn = apn;
	}

	ModemDialParams dataServerApn;

	public ModemDialParams getModemDialParams() {
		return dataServerApn;
	}
}