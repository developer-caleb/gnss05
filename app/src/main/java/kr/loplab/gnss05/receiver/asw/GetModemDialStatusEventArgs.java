package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialStatus;

/**
 * 3G model dial status
 * 
 */
public class GetModemDialStatusEventArgs extends ReceiverDataEventArgs {

	ModemDialStatus status;

	public GetModemDialStatusEventArgs(EnumReceiverCmd cmd,
			ModemDialStatus status) {
		super(cmd);
		this.status = status;
	}

	public ModemDialStatus getStatus() {
		return status;
	}
}
