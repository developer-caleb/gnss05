package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.Course;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.PositionInfo;

/**
 * get the latitude and lontitude
 */
public class GetPositionExEventArgs extends ReceiverDataEventArgs {

	private PositionInfo mPositionInfo;
	private Course mCourse;

	public GetPositionExEventArgs(EnumReceiverCmd cmd,
			PositionInfo positionInfo, Course course) {
		super(cmd);
		this.mPositionInfo = positionInfo;
		this.mCourse = course;
	}

	public PositionInfo getPositionInfo() {
		return mPositionInfo;
	}

	public Course getCourse() {
		return mCourse;
	}
}
