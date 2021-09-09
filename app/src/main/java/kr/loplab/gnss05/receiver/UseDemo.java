package kr.loplab.gnss05.receiver;

import android.content.Context;

import com.chc.gnss.sdk.CHC_DATA_FREQUENCY;
import com.chc.gnss.sdk.CHC_NMEA_TYPE;
import com.chc.gnss.sdk.CHC_Receiver;
import com.chc.gnss.sdk.CHC_ReceiverRef;
import com.huace.gnssserver.gnss.data.receiver.EnumDataFrequency;
import com.huace.gnssserver.gnss.data.receiver.EnumGnssIoId;
import com.huace.gnssserver.gnss.data.receiver.EnumNmeaType;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.NmeaData;
import com.huace.gnssserver.gnss.data.receiver.NmeaSetParam;
import com.huace.gnssserver.gnss.data.receiver.NmeaTypeArray;

import java.util.ArrayList;

import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.receiver.cmd.GetCmdDisableOtherIOsEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdOutputNMEAEventArgs;

/**
 * 수신과 관련된 수신자 패키지 아래의 이러한 클래스에 주의하십시오.
 * 
 * 1、receiver 구성 요소는 다음과 같이 초기화됩니다.： 2、내장 네트워크 로그인의 경우 NetCorsController 클래스의 로그인 및 연결 해제 방법을 참조하십시오.
 */
public class UseDemo {

	/**
	 * 첫 번째 단계에서 사용자가 직접 블루투스 연결 또는 Wi-Fi 연결을 해야 할 수 있으며 연결이 성공한 후 초기화 메소드를 호출해야 합니다.
	 * Huace 수신기 분석 구성 요소 실행
	 */
	public static boolean runReceiver(Context context) {
		if (Receiver.getInstance().isIsRun()) {
			return true;
		}
		return Receiver.getInstance().run(context);
	}

	/**
	 * 두 번째 단계는 분석을 위해 수신기 데이터를 분석 라이브러리로 보내는 것입니다.
	 */
	public static void receiveData(byte[] data) {
		if (data == null || data.length <= 0) {
			return;
		}
		if (!Receiver.getInstance().isIsRun()) {
			return;
		}
		Receiver.getInstance().receiveData(data);
	}

	/**
	 * 마지막으로 소프트웨어를 종료하고 구성 요소를 더 이상 사용하지 않는 경우 구성 요소 구문 분석을 중지해야 합니다.
	 */
	public static void stopReceiver() {
		if (!Receiver.getInstance().isIsRun()) {
			return;
		}
		Receiver.getInstance().stop();
	}

	/**
	 *
	 * NMEA 데이터만 설정하여 스스로 분석
	 */
	public static void onlyOutputNMEA() {
		// 먼저 데이터를 끕니다.
		ReceiverCmdProxy.BUS.post(new GetCmdDisableOtherIOsEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_OTHER_IO_IDS_DISABLED,
				EnumGnssIoId.GNSS_IO_ID_NONE.getValue()));
		//그런 다음 NMEA 출력을 설정합니다.
		NmeaTypeArray nmeaTypeArray = getGnssSupportedTypes();
		if (nmeaTypeArray == null) {
			L.e("오류가 발생했습니다. 수신기가 방금 연결되었을 수 있습니다!");
			return;
		}
		ArrayList<EnumNmeaType> nmeas = nmeaTypeArray.getEnumNmeaTypeList();
		if (nmeas.size() <= 0) {
			return;
		}
		NmeaData[] datas = new NmeaData[nmeas.size()];
		for (int i = 0; i < nmeas.size(); i++) {
			NmeaData nmeaData = new NmeaData();
			nmeaData.setChcEmNmeaType(nmeas.get(i));
			nmeaData.setFrequency(EnumDataFrequency.DATA_FREQUENCY_1HZ);
			datas[i] = nmeaData;
		}
		NmeaSetParam param = new NmeaSetParam();
		param.setMethods(EnumGnssIoId.GNSS_IO_ID_BT.getValue());
		param.setData(datas);
		param.setSave(true);
		ReceiverCmdProxy.BUS.post(new GetCmdOutputNMEAEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_GNSS_NMEADATA, param));
	}

	/***************************************** NMEA를 직접 구문 분석해야 하는 경우 다음 방법으로 수신기 출력 NMEA를 만들 수 있습니다.*******************************************************************/
	/**
	 * 지원되는 NMEA 유형 가져오기
	 * 
	 * @return
	 */
	public static NmeaTypeArray getGnssSupportedTypes() {
		if (!Receiver.getInstance().isIsRun()) {
			return null;
		}
		CHC_ReceiverRef receiverRef = Receiver.getInstance().getReceiverRef();
		CHC_NMEA_TYPE[] types = new CHC_NMEA_TYPE[60];
		CHC_DATA_FREQUENCY[] freqs = new CHC_DATA_FREQUENCY[60];
		synchronized (Receiver.getInstance().getLock()) {
			if (receiverRef == null) {
				return null;
			}
			CHC_Receiver.CHCGetSupportNMEAList(receiverRef, types, freqs);
		}
		ArrayList<EnumNmeaType> listTypes = new ArrayList<EnumNmeaType>();
		for (CHC_NMEA_TYPE type : types) {
			if (type != null) {
				EnumNmeaType t = ConversionDataStruct.covChcEmNmeaType(type);
				if (t != EnumNmeaType.NMEA_TYPE_NONE) {
					listTypes.add(t);
				}
			}
		}
		return new NmeaTypeArray(listTypes);
	}

}
