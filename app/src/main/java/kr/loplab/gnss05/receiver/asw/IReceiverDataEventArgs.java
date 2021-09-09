package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * a interface -- all the classwith the param parsed by the command from receiver need this
 */
public interface IReceiverDataEventArgs {
	EnumReceiverCmd getDataType();
}
