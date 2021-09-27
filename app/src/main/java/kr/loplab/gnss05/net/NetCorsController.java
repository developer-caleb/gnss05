package kr.loplab.gnss05.net;

import android.content.Context;

import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

import kr.loplab.gnss05.connection.ConnectManager;
import kr.loplab.gnss05.receiver.ReceiverCmdProxy;
import kr.loplab.gnss05.receiver.cmd.ReceiverCmdEventArgs;

/**
 * Cors controller
 */
public class NetCorsController {

	/**
	 * Disconnect the scoring connection
	 */
	public void loginOut() {
		ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GPRS_BREAK));
	}

	/**
	 *
	 * Is the receiver connected
	 * 
	 * @return Connection Status
	 */
	public boolean isGnssConnect() {
		return ConnectManager.getInstance().isConnected();
	}

	/**
	 * Log in to cors
	 * 
	 * @param diffDataInfo
	 *            login information
	 */
	public void login(DiffDataInfo diffDataInfo, Context context) {
		new AsyncTaskNetCors(context).execute(diffDataInfo);
	}

}
