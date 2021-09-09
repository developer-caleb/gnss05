package kr.loplab.gnss05.receiver.cmd;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

public class GetCmdDataEventArgs extends ReceiverCmdEventArgs{

    public GetCmdDataEventArgs(EnumReceiverCmd cmd) {
        super(cmd);
    }
}
