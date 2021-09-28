package kr.loplab.gnss05.receiver.sourcelist;


import android.util.Log;

import com.huace.gnssserver.gnss.data.receiver.DataSourceList;
import com.huace.gnssserver.gnss.data.receiver.EnumNetworkProtocol;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.GprsInfo;

import java.util.ArrayList;
import java.util.List;

import kr.loplab.gnss05.common.ByteUtils;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.receiver.ReceiverCmdProxy;
import kr.loplab.gnss05.receiver.ReceiverService;
import kr.loplab.gnss05.receiver.asw.GetSourceListEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSourceTableEventArgs;
import kr.loplab.gnss05.receiver.asw.IReceiverDataEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateGprsInfoEventArgs;
import kr.loplab.gnss05.receiver.cmd.ReceiverCmdEventArgs;

public class GetSourceFromReceiver {
	String TAG = this.getClass().getSimpleName();
	private static GetSourceFromReceiver instance = null;

	private ArrayList<Ntriprecord> mSourceList = new ArrayList<>();
	private String mIp = "";
	private int mPort = 0;
	private String mStrSource = "";

	private GetSourceFromReceiver() {
	}

	public static GetSourceFromReceiver getInstance() {
		if (instance == null) {
			synchronized (GetSourceFromReceiver.class) {
				if (instance == null) {
					instance = new GetSourceFromReceiver();
				}
			}
		}
		return instance;
	}

	public void loadSourceList(String ip, int port) {
		mIp = ip;
		mPort = port;
		mSourceList = new ArrayList<>();
		ReceiverCmdProxy.BUS.post(new GetCmdUpdateGprsInfoEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GPRS_INFO, getGPRSInfo()));
		ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_GET_SOURCE_TABLE));

		mStrSource = "";
	}

	private GprsInfo getGPRSInfo() {
		GprsInfo info = new GprsInfo();
		info.setProtocol(EnumNetworkProtocol.NETWORK_PROTOCOL_NTRIP_ROVER);
		info.getAddressPort().setPort(mPort);
		info.getAddressPort().setDomain(mIp);
		info.getAddressPort().setUseDoMain(!ByteUtils.checkIP(mIp));
		return info;
	}

	public List<String> getSourceList() {
		List<String> datas = new ArrayList<>();
		for (int i = 0; i < mSourceList.size(); i++) {
			datas.add(mSourceList.get(i).strMountpoint);
		}
		return datas;
	}

	private void parseSourcePoints(List<String> strList) {
		this.mSourceList.clear();
		for (int i = 0; i < strList.size(); i++) {
			Ntriprecord record = new Ntriprecord();
			record.strMountpoint = strList.get(i);
			this.mSourceList.add(record);
		}
	}

	private List<String> parseSourceTable(String src) {
		List<String> lst = new ArrayList<>();
		if (src == null) {
			return lst;
		}
		int pos = src.indexOf("\r\nSTR;");
		while (pos != -1) {
			int f = src.indexOf(";", pos + 6);
			if (f != -1) {
				lst.add(src.substring(pos + 6, f));
			} else {
				break;
			}
			pos = src.indexOf("\r\nSTR;", f);
		}
		return lst;
	}

	public void onEventBackgroundThread(IReceiverDataEventArgs args) {
		Log.d(TAG, "onEventBackgroundThread: 백그라운드에서 소스 가져오기");
		try {
			switch (args.getDataType()) {
			case RECEIVER_ASW_GET_SOURCE_TABLE:
				if (args instanceof GetSourceTableEventArgs) {
					GetSourceTableEventArgs arg = (GetSourceTableEventArgs) args;
					DataSourceList data = arg.getDataSourceList();
					if (data != null && data.getBytes() != null) {
						String rec = new String(data.getBytes());
						mStrSource += rec;
						if (mStrSource.contains("ENDSOURCETABLE")) {
							// Parse the raw data acquired
							List<String> strlist = parseSourceTable(mStrSource);
							parseSourcePoints(strlist);
							ReceiverService.BUS.post(new GetSourceListEventArgs(true));
						}
					}
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			L.printException(e);
		}
	}
}
