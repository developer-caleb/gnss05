package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialParams;

/**
 * APN
 * 
 * @author wangjun
 * 
 */
public class GetModemAutoDialParamsEventArgs extends ReceiverDataEventArgs {

	ModemDialParams apn;

	public GetModemAutoDialParamsEventArgs(EnumReceiverCmd cmd,
			ModemDialParams apn) {
		super(cmd);
		this.apn = apn;
	}

	public ModemDialParams getParams() {
		return apn;
	}
}