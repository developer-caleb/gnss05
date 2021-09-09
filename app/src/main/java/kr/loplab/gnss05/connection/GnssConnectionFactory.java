package kr.loplab.gnss05.connection;


import androidx.annotation.Nullable;

/**
 * this factory is using for the connection
 */
public class GnssConnectionFactory {

	@Nullable
	public static IGnssConnection makeConnection(GnssConnectionWay way) {
		if (way == GnssConnectionWay.BLUETOOTH) {
			return new BluetoothConnection();
		} else {
			return null;
		}
	}

}
