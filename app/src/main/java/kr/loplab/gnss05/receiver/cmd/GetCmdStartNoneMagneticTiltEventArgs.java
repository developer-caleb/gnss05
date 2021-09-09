package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticTiltStartInfo;

/**
 * 开启 imu
 */
public class GetCmdStartNoneMagneticTiltEventArgs extends ReceiverCmdEventArgs {

    private final NoneMagneticTiltStartInfo mNoneMagneticTiltStartInfo;

    public GetCmdStartNoneMagneticTiltEventArgs(EnumReceiverCmd cmd, NoneMagneticTiltStartInfo info) {
        super(cmd);
        this.mNoneMagneticTiltStartInfo = info;
    }

    public NoneMagneticTiltStartInfo getNoneMagneticTiltStartInfo() {
        return mNoneMagneticTiltStartInfo;
    }
}
