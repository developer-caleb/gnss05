package kr.loplab.gnss05.receiver.cmd;


import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * interface -- all the class which you need to send command to the receiver should call this
 * 
 * @author wangjun
 */
public interface IReceiverCmdEventArgs {
	EnumReceiverCmd getCmdType();
}
