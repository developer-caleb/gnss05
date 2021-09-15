package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticSetParams;

/**
 * Non-magnetic tilt data
 */
public class GetNoneMagneticSetParamsEventArgs extends ReceiverDataEventArgs {
    private NoneMagneticSetParams mInfo;

    public GetNoneMagneticSetParamsEventArgs(EnumReceiverCmd cmd, NoneMagneticSetParams info) {
        super(cmd);
        this.mInfo = info;
    }

    public NoneMagneticSetParams getInfo() {
        return mInfo;
    }
}
