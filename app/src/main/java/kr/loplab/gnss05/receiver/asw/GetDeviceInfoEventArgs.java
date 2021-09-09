package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.device.DeviceInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

public class GetDeviceInfoEventArgs extends ReceiverDataEventArgs {
    private DeviceInfo mDeviceInfo;

    public GetDeviceInfoEventArgs(EnumReceiverCmd cmd, DeviceInfo info) {
        super(cmd);
        this.mDeviceInfo = info;
    }

    public DeviceInfo getDeviceInfo() {
        return mDeviceInfo;
    }
}
