package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticTiltInfo;

/**
 * Non-magnetic tilt data
 */
public class GetNoneMagneticTiltInfoEventArgs extends ReceiverDataEventArgs {
    private NoneMagneticTiltInfo mInfo;

    public GetNoneMagneticTiltInfoEventArgs(EnumReceiverCmd cmd, NoneMagneticTiltInfo info) {
        super(cmd);
        this.mInfo = info;
    }

    public NoneMagneticTiltInfo getInfo() {
        return mInfo;
    }
}
