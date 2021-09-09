package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.SatelliteNumber;

/**
 * get the number of the satellite in used
 */
public class GetSatelliteUsedNumsEventArgs extends ReceiverDataEventArgs {

	private SatelliteNumber mSatNum;

	public GetSatelliteUsedNumsEventArgs(EnumReceiverCmd cmd,
			SatelliteNumber satNum) {
		super(cmd);
		this.mSatNum = satNum;
	}

	public SatelliteNumber getSatelliteNumber() {
		return mSatNum;
	}
}
