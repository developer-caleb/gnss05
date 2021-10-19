package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * Power
 * 
 * @author yongik
 * 
 */
public class GetCmdPowerModemEventArgs extends ReceiverCmdEventArgs {

	short isPower; //powerOn: Power on or off. 0 - power off, 1 - power on

	public GetCmdPowerModemEventArgs(EnumReceiverCmd cmd, short isPower) {
		super(cmd);
		this.isPower = isPower;
	}

	public short isDial() {
		return isPower;
	}
}
