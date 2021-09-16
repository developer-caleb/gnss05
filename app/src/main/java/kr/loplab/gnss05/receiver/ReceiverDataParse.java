package kr.loplab.gnss05.receiver;

import android.util.Log;

import com.chc.gnss.sdk.CHC_Buffer;
import com.chc.gnss.sdk.CHC_Course;
import com.chc.gnss.sdk.CHC_DATA_FREQUENCY;
import com.chc.gnss.sdk.CHC_DeviceInfo;
import com.chc.gnss.sdk.CHC_DopsInfo;
import com.chc.gnss.sdk.CHC_MESSAGE_TYPE;
import com.chc.gnss.sdk.CHC_MessageInfo;
import com.chc.gnss.sdk.CHC_ModemDialParams;
import com.chc.gnss.sdk.CHC_ModemDialStatus;
import com.chc.gnss.sdk.CHC_NONE_MAGNETIC_SUPPORT_TYPE;
import com.chc.gnss.sdk.CHC_NoneMagneticTiltInfo;
import com.chc.gnss.sdk.CHC_Position;
import com.chc.gnss.sdk.CHC_Receiver;
import com.chc.gnss.sdk.CHC_ReceiverConstants;
import com.chc.gnss.sdk.CHC_ReceiverInfo;
import com.chc.gnss.sdk.CHC_ReceiverRef;
import com.chc.gnss.sdk.CHC_SATELLITE_CONSTELLAION;
import com.chc.gnss.sdk.CHC_SatelliteInfo;
import com.chc.gnss.sdk.CHC_SatelliteInfoArray;
import com.chc.gnss.sdk.CHC_SatelliteNumber;
import com.huace.gnssserver.gnss.data.SatelliteInfo;
import com.huace.gnssserver.gnss.data.receiver.Course;
import com.huace.gnssserver.gnss.data.receiver.DataSourceList;
import com.huace.gnssserver.gnss.data.receiver.DopsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumDataFrequency;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialParams;
import com.huace.gnssserver.gnss.data.receiver.ModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticSetParams;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticTiltInfo;
import com.huace.gnssserver.gnss.data.receiver.PositionInfo;
import com.huace.gnssserver.gnss.data.receiver.ReceiverInfo;
import com.huace.gnssserver.gnss.data.receiver.SatelliteNumber;

import java.util.ArrayList;
import java.util.List;

import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.receiver.asw.GetBattteyLifeEventArgs;
import kr.loplab.gnss05.receiver.asw.GetGnssDopsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetModemAutoDialParamsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetModemDialStatusEventArgs;
import kr.loplab.gnss05.receiver.asw.GetNoneMagneticSetParamsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetNoneMagneticTiltInfoEventArgs;
import kr.loplab.gnss05.receiver.asw.GetPositionExEventArgs;
import kr.loplab.gnss05.receiver.asw.GetReceiverInfoEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSatelliteInfosEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSatelliteUsedNumsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSourceTableEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdOutputPosDataEventArgs;
import kr.loplab.gnss05.receiver.cmd.ReceiverCmdEventArgs;

/**
 * Analytical data
 * 
 * @author wangjun
 * 
 */
public class ReceiverDataParse {

	private Receiver mReceiver;

	private CHC_ReceiverRef mReceiverRef;

	private static ReceiverDataParse sInstance = null;

	private ReceiverDataParse() {
		mReceiver = Receiver.getInstance();
	}

	public static ReceiverDataParse getInstance() {
		if (sInstance == null) {
			synchronized (ReceiverDataParse.class) {
				if (sInstance == null) {
					sInstance = new ReceiverDataParse();
				}
			}
		}
		return sInstance;
	}

	/**
	 * parse data from the receiver
	 */
	public void parseData() {
		try {
			mReceiverRef = mReceiver.getReceiverRef();
			if (mReceiverRef == null || !mReceiver.isIsRun()) {
				return;
			}
			freshMessageInfo();
		} catch (Exception e) {
			L.printException(e);
		}
	}

	/**
	 * Get data from SDK
	 */
	private void freshMessageInfo() {
		CHC_MessageInfo info = new CHC_MessageInfo();
		CHC_Receiver.CHCGetMessageInfo(mReceiverRef, info);
		/*
		 * MESSAGE_TYPE_UNKNOWN, // = 0, MESSAGE_TYPE_GNSS, // = 1,
		 * Protocol(GNSS) MESSAGE_TYPE_SYSTEM, // = 2, Protocol(System FILE I/O)
		 * MESSAGE_TYPE_PDA, // = 3, Protocol(3G WIFI) MESSAGE_TYPE_DATALINK; //
		 * = 4 Protocol(Radio, Net & Differential data)
		 */
		long ulmsg = info.getUlmsg();
		if (ulmsg == 0
				|| info.getMsgType() == CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_UNKNOWN) {
			return;
		}

		freshMessageInfo(info.getMsgType(), ulmsg);
		info.delete();
	}

	private void freshMessageInfo(CHC_MESSAGE_TYPE type, long ulmsg) {
		switch (type) {
		case CHC_MESSAGE_TYPE_GNSS:
			parseGnssData(ulmsg);
			break;
		case CHC_MESSAGE_TYPE_SYSTEM:
			parseSystemData(ulmsg);
			break;
		case CHC_MESSAGE_TYPE_PDA:
			getPdaData(ulmsg);
			break;
		case CHC_MESSAGE_TYPE_DATALINK:
			parseDatalinkData(ulmsg);
			break;
		default:
			break;
		}
	}

	private void getPdaData(long ulmsg) {

		/** 3G modem dial params */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_MODEM_DIALPARAM) != 0) {
			CHC_ModemDialParams chc_params = new CHC_ModemDialParams();
			CHC_Receiver.CHCGetModemAutoDialParams(mReceiverRef, chc_params);
			ModemDialParams apn = ConversionDataStruct
					.covModemDialParams(chc_params);
			ReceiverService.BUS.post(new GetModemAutoDialParamsEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_PARAM, apn));
			chc_params.delete();
		}

		/** 3G status of dial*/
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_MODEM_DIALSTATUS) != 0) {
			CHC_ModemDialStatus chc_status = new CHC_ModemDialStatus();
			CHC_Receiver.CHCGetModemDialStatus(mReceiverRef, chc_status);
			ModemDialStatus status = ConversionDataStruct
					.covModemDialStatus(chc_status);
			ReceiverService.BUS
					.post(new GetModemDialStatusEventArgs(
							EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_STATUS,
							status));
			chc_status.delete();
		}

	}

	/**
	 * CHC_MESSAGE_TYPE_GNSS
	 * 
	 * @param ulmsg
	 */
	private void parseGnssData(long ulmsg) {
		// the satellite infomation
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_GNSS_DATASVTRACK) != 0) {
			parseSatelliteData();
		}

		// the position information
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_GNSS_DATAPOS) != 0) {// PositionInfo
																			// Data
			CHC_Position chc_Position = new CHC_Position();
			CHC_Course chc_Course = new CHC_Course();
			CHC_Receiver.CHCGetPositionEx(mReceiverRef, chc_Position,
					chc_Course);
			PositionInfo positionInfo = ConversionDataStruct
					.covPositionInfo(chc_Position);
			Course course = ConversionDataStruct.covCourse(chc_Course);
			ReceiverService.BUS.post(new GetPositionExEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA,
					positionInfo, course));
			chc_Position.delete();
			chc_Course.delete();
		}

		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_GNSS_DATADOPS) != 0) { // Dop
																				// Data
			CHC_DopsInfo chc_DopsInfo = new CHC_DopsInfo();
			CHC_Receiver.CHCGetGNSSDops(mReceiverRef, chc_DopsInfo);
			DopsInfo dopsInfo = ConversionDataStruct.covDopsInfo(chc_DopsInfo);
			ReceiverService.BUS.post(new GetGnssDopsEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA, dopsInfo));
			chc_DopsInfo.delete();
		}

	}

	/***
	 * get Satellite Data
	 */
	private void parseSatelliteData() {

		CHC_SatelliteNumber chc_SatelliteNumber = new CHC_SatelliteNumber();
		CHC_Receiver.CHCGetSatelliteUsedNums(mReceiverRef, chc_SatelliteNumber); // user			// satellite/all
		SatelliteNumber satNumbers = ConversionDataStruct
				.covSatelliteNumber(chc_SatelliteNumber);
		ReceiverService.BUS.post(new GetSatelliteUsedNumsEventArgs(
				EnumReceiverCmd.RECEIVER_ASW_GET_GNSS_SATELLITE_USEDNUM,
				satNumbers));

		int satNum = (int) chc_SatelliteNumber.getSatNum();
		if (satNum == 0) {
			ReceiverService.BUS.post(new GetSatelliteInfosEventArgs(
					EnumReceiverCmd.MRECEIVER_ASW_SET_GNSS_SATELLITEDATA,
					new ArrayList<SatelliteInfo>()));
			return;
		}
		chc_SatelliteNumber.delete();

		List<SatelliteInfo> satelliteInfoArrayList = new ArrayList<SatelliteInfo>();
		satelliteInfoArrayList
				.addAll(getSatelliteInfos(CHC_SATELLITE_CONSTELLAION.CHC_SATELLITE_CONSTELLAION_GPS));
		satelliteInfoArrayList
				.addAll(getSatelliteInfos(CHC_SATELLITE_CONSTELLAION.CHC_SATELLITE_CONSTELLAION_CAMPASE));
		satelliteInfoArrayList
				.addAll(getSatelliteInfos(CHC_SATELLITE_CONSTELLAION.CHC_SATELLITE_CONSTELLAION_GALILEO));
		satelliteInfoArrayList
				.addAll(getSatelliteInfos(CHC_SATELLITE_CONSTELLAION.CHC_SATELLITE_CONSTELLAION_GLONASS));
		satelliteInfoArrayList
				.addAll(getSatelliteInfos(CHC_SATELLITE_CONSTELLAION.CHC_SATELLITE_CONSTELLAION_SBAS));

		ReceiverService.BUS.post(new GetSatelliteInfosEventArgs(
				EnumReceiverCmd.MRECEIVER_ASW_SET_GNSS_SATELLITEDATA,
				satelliteInfoArrayList));
	}

	private List<SatelliteInfo> getSatelliteInfos(
			CHC_SATELLITE_CONSTELLAION SatelliteType) {
		CHC_SatelliteInfoArray chcSatelliteInfoArray = new CHC_SatelliteInfoArray();
		CHC_Receiver.CHCGetSatelliteInfo(mReceiverRef, SatelliteType,
				chcSatelliteInfoArray); // get　satellite　Data
		CHC_SatelliteInfo[] chcSatelliteInfos = chcSatelliteInfoArray
				.getItems();
		List<SatelliteInfo> satelliteInfoList = new ArrayList<SatelliteInfo>();
		for (CHC_SatelliteInfo info : chcSatelliteInfos) {
			if (info != null) {
				SatelliteInfo satelliteInfo = ConversionDataStruct
						.covSatelliteInfo(info);
				satelliteInfoList.add(satelliteInfo);
			}
		}
		chcSatelliteInfoArray.delete();
		return satelliteInfoList;
	}

	/**
	 * CHC_MESSAGE_TYPE_SYSTEM
	 * 
	 * @param ulmsg
	 */
	private void parseSystemData(long ulmsg) {
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_INIT_CONNECTION) != 0) {
			/**
			 * query some data
			 */
			ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
					EnumReceiverCmd.MSG_RECEIVER_CMD__SET_INIT));
			ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
					EnumReceiverCmd.RECEIVER_CMD_GET_RECEIVER_INFO));
			ReceiverCmdProxy.BUS.post(new GetCmdOutputPosDataEventArgs(
					EnumReceiverCmd.RECEIVER_CMD_SET_GNSS_POSDATA,
					EnumDataFrequency.DATA_FREQUENCY_1HZ));
			ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
					EnumReceiverCmd.RECEIVER_CMD_GET_GNSS_BASEPARAM));
		}

		/** the onformation of the receiver */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_DEVICEINFO) != 0) {
			CHC_ReceiverInfo receiverInfo = new CHC_ReceiverInfo();
			CHC_Receiver.CHCGetReceiverInfo(mReceiverRef, receiverInfo);
			ReceiverInfo info = ConversionDataStruct
					.covReceiverInfo(receiverInfo);
			ReceiverService.BUS.post(new GetReceiverInfoEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_RECEIVER_INFO, info));
			receiverInfo.delete();
		}

		/** the battery life from the receiver */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_POWERSTATUS) != 0) {
			int[] batteryLife = new int[1];
			CHC_Receiver.CHCGetBattteyLife(mReceiverRef, batteryLife);
			ReceiverService.BUS.post(new GetBattteyLifeEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_BATTERYLIFE,
					batteryLife[0]));
		}

		/** Get Device Info  *///CHC_MESSAGE_SYSTEM_RECEIVER_INFO
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_RECEIVER_INFO) != 0) {
			Log.v("Click", "Get Receivce Info");

			CHC_DeviceInfo nDeviceInfo = new CHC_DeviceInfo();
			CHC_Receiver.CHCGetDeviceInfo(mReceiverRef, nDeviceInfo);
			nDeviceInfo.delete();

			boolean tiltsupported;
			CHC_NONE_MAGNETIC_SUPPORT_TYPE[] support = new CHC_NONE_MAGNETIC_SUPPORT_TYPE[1];
			if (CHC_Receiver.CHCGetNoneMagneticSupportedEx(mReceiverRef, support) >= 0) {
				tiltsupported = (support[0] == CHC_NONE_MAGNETIC_SUPPORT_TYPE.CHC_NONE_MAGNETIC_SUPPORT_SHAKE);
				if (tiltsupported) {
					Log.v("Click", "Magnetic Was Supported");
					//Start Magnetic Tilt
					ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
							EnumReceiverCmd.RECEIVER_CMD_SET_START_NONE_MAGNETIC_TILT));
				}
			}
		}

		/*
		 * i80s倾斜测量数据
		 */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_NONE_MAGNETIC_TILT_INFO) != 0) {
			CHC_NoneMagneticTiltInfo chcTiltInfo = new CHC_NoneMagneticTiltInfo();
			CHC_Receiver.CHCGetNoneMagneticTiltInfo(mReceiverRef, chcTiltInfo);
			NoneMagneticTiltInfo info = CovMagneticTilt.covNoneMagneticTiltInfo(chcTiltInfo);
			if (info == null) {
				return;
			}
			ReceiverService.BUS.post(new GetNoneMagneticTiltInfoEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_NONE_MAGNETIC_TILT_INFO, info));
			chcTiltInfo.delete();
		}

		/*
		 * i80s倾斜测量参数
		 */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_NONE_MAGNETIC_TILT_PARAM) != 0) {
			short[] controlStatus = new short[1];
			double[] antennaHeight = new double[1];
			CHC_DATA_FREQUENCY[] freq = new CHC_DATA_FREQUENCY[1];
			CHC_Receiver.CHCGetNoneMagneticSetParams(mReceiverRef,
					controlStatus, antennaHeight, freq);
			NoneMagneticSetParams params = CovMagneticTilt.covNoneMagneticSetParams(
					controlStatus, antennaHeight, freq);
			ReceiverService.BUS.post(new GetNoneMagneticSetParamsEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_NONE_MAGNETIC_SET_PARAMS, params));
		}
	}
	/****
	 * get the information callback of the netlink
	 */
	void parseDatalinkData(long ulmsg) {
		/** get source table */
		if ((ulmsg & CHC_ReceiverConstants.CHC_MESSAGE_NETLINK_SOURCELIST) != 0) {
			CHC_Buffer chc_buf = new CHC_Buffer(0);
			int len = CHC_Receiver.CHCGetSourceTable_s(mReceiverRef, chc_buf);
			chc_buf.delete();
			if (len <= 0) {
				return;
			}
			CHC_Buffer buf = new CHC_Buffer(len);
			CHC_Receiver.CHCGetSourceTable_s(mReceiverRef, buf);
			DataSourceList data = ConversionDataStruct.covDataSourceList(buf);
			ReceiverService.BUS.post(new GetSourceTableEventArgs(
					EnumReceiverCmd.RECEIVER_ASW_GET_SOURCE_TABLE, data));
			chc_buf.delete();
		}
	}

}
