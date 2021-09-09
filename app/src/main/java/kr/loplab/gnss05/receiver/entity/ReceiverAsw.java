package kr.loplab.gnss05.receiver.entity;

import android.os.Parcelable;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

public class ReceiverAsw {

	private EnumReceiverCmd mReceiverCmd;

	private Parcelable mParcelable;

	public EnumReceiverCmd getReceiverCmdType() {
		return mReceiverCmd;
	}

	public void setReceiverCmdType(EnumReceiverCmd mReceiverCmdType) {
		this.mReceiverCmd = mReceiverCmdType;
	}

	public Parcelable getParcelable() {
		return mParcelable;
	}

	public void setParcelable(Parcelable mParcelable) {
		this.mParcelable = mParcelable;
	}

}
