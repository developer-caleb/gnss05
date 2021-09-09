package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * battery life
 * 
 * @author wangjun
 * 
 */

public class GetBattteyLifeEventArgs extends ReceiverDataEventArgs {

	int mBatteryLife;

	public GetBattteyLifeEventArgs(EnumReceiverCmd cmd, int batteryLife) {
		super(cmd);
		this.mBatteryLife = batteryLife;
	}

	public int getBatteryLife() {
		return mBatteryLife;
	}
}
