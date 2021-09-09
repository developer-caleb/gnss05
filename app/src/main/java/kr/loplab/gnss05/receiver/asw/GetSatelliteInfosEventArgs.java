package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.SatelliteInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

import java.util.List;

/**
 * get the satellite info
 */
public class GetSatelliteInfosEventArgs extends ReceiverDataEventArgs {

	private List<SatelliteInfo> mSatelliteInfos;

	public GetSatelliteInfosEventArgs(EnumReceiverCmd cmd,
			List<SatelliteInfo> satelliteInfos) {
		super(cmd);
		this.mSatelliteInfos = satelliteInfos;
	}

	public List<SatelliteInfo> getSatelliteInfos() {
		return mSatelliteInfos;
	}
}
