package kr.loplab.gnss05.connection;

import android.content.Context;

import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * IGnssConnection Created by zhoujin on 2015-10-12.
 */
public interface IGnssConnection {

	boolean connect(Context context, IConnectionCallback callback);

	void disConnect();

	boolean writeDataToDevice(Cmd cmd);
}
