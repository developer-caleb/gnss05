package kr.loplab.gnss05.net;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.huace.gnssserver.gnss.data.receiver.CorsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumGnssIoId;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumNetworkProtocol;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.GprsInfo;
import com.huace.gnssserver.gnss.data.receiver.RoverParams;

import kr.loplab.gnss05.R;
import kr.loplab.gnss05.common.ByteUtils;
import kr.loplab.gnss05.common.DialogUtils;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.receiver.CmdManager;
import kr.loplab.gnss05.receiver.ReceiverCmdProxy;
import kr.loplab.gnss05.receiver.ReceiverService;
import kr.loplab.gnss05.receiver.cmd.GetCmdDialModemEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdDisableOtherIOsEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdStartRoverEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateCorsInfoEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateGprsInfoEventArgs;
import kr.loplab.gnss05.receiver.cmd.ReceiverCmdEventArgs;

/**
 * Built-in network login cors asynchronous task Created by hanhongyun on 2017/6/20.
 */

public class AsyncTaskNetCors extends AsyncTask<DiffDataInfo, Void, Boolean> {

	private Dialog dialog;

	private Context mContext;

	public AsyncTaskNetCors(Context context) {
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		dialog = DialogUtils
				.showProgressDialog(mContext, R.string.msg_logining);
		dialog.show();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(DiffDataInfo... params) {
		boolean ret;
		DiffDataInfo diffInfo = params[0];
		GprsInfo gprsInfo = new GprsInfo();
		if (!convertToGprs(diffInfo, gprsInfo)) {
			return false;
		}
		CorsInfo corsInfo = convertToCors(diffInfo);

		try {
			sendCmd(gprsInfo, corsInfo);
			ret = true;
		} catch (Exception e) {
			L.printException(e);
			ret = false;
		}
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			ret = false;
			e.printStackTrace();
		}
		if (ret) {
			for (int i = 0; i < 8
					&& CmdManager.getInstance().getCmdNumber() > 0; i++) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					ret = false;
					L.printException(e);
				}
			}
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				ret = false;
				L.printException(e);
			}
		}
		return ret;
	}

	private void sendCmd(GprsInfo gprsInfo, CorsInfo corsInfo) {
		// If the dialing status of the 3G module is off, turn off the module.
		// The dialing status of 3G module or GSM module is controlled by software
		if (ReceiverService.BUS.getModemDialStatus() == EnumModemDialStatus.MODEM_DIAL_STATUS_INIT) {
			ReceiverCmdProxy.BUS.post(new GetCmdDialModemEventArgs(
					EnumReceiverCmd.RECEIVER_CMD_SET_MODEM_DIALON, false));
		}

		// Disable other IOs except the built-in network
		ReceiverCmdProxy.BUS.post(new GetCmdDisableOtherIOsEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_OTHER_IO_IDS_DISABLED,
				EnumGnssIoId.GNSS_IO_ID_NETLINK_ROVER.getValue()));
		ReceiverCmdProxy.BUS.post(new GetCmdUpdateGprsInfoEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GPRS_INFO, gprsInfo));
		if (gprsInfo.getProtocol() == EnumNetworkProtocol.NETWORK_PROTOCOL_NTRIP_ROVER) {
			ReceiverCmdProxy.BUS.post(new GetCmdUpdateCorsInfoEventArgs(
					EnumReceiverCmd.RECEIVER_CMD_SET_CORS_INFO, corsInfo));
		}
		ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GPRS_LOGIN));

		// Start the mobile station
		ReceiverCmdProxy.BUS.post(new GetCmdStartRoverEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GNSS_STARTROVER,
				new RoverParams(10, 6))); //Height cut-off angle, PDOP value
	}

	private CorsInfo convertToCors(DiffDataInfo diffInfo) {
		CorsInfo corsInfo = new CorsInfo();
		corsInfo.setUserName(diffInfo.getUserName());
		corsInfo.setPassword(diffInfo.getPassWord());
		corsInfo.setSource(diffInfo.getSourcePoint());
		return corsInfo;
	}

	private boolean convertToGprs(DiffDataInfo diffInfo, GprsInfo gprsInfo) {
		gprsInfo.setProtocol(EnumNetworkProtocol.NETWORK_PROTOCOL_NTRIP_ROVER);
		gprsInfo.getAddressPort().setPort(diffInfo.getPort());
		if (gprsInfo.getAddressPort().getPort() < 0
				|| gprsInfo.getAddressPort().getPort() > 65535) {
			return false;
		}
		gprsInfo.getAddressPort().setDomain(diffInfo.getIp());
		gprsInfo.getAddressPort().setUseDoMain(
				!ByteUtils.checkIP(diffInfo.getIp()));
		gprsInfo.setBaseId("");
		return true;
	}

	@Override
	protected void onPostExecute(Boolean ret) {
		if (dialog != null) {
			dialog.dismiss();
		}
		if (ret) {
			Toast.makeText(mContext, "Command Send Success！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext, "Command Send False！", Toast.LENGTH_SHORT).show();
		}
		super.onPostExecute(ret);
	}
}
