package kr.loplab.gnss05.receiver;

import com.chc.gnss.sdk.CHC_AddressPort;
import com.chc.gnss.sdk.CHC_BaseParams;
import com.chc.gnss.sdk.CHC_Buffer;
import com.chc.gnss.sdk.CHC_CMD;
import com.chc.gnss.sdk.CHC_CORSInfo;
import com.chc.gnss.sdk.CHC_Course;
import com.chc.gnss.sdk.CHC_DATA_FREQUENCY;
import com.chc.gnss.sdk.CHC_DeviceInfo;
import com.chc.gnss.sdk.CHC_DopsInfo;
import com.chc.gnss.sdk.CHC_ExpireDate;
import com.chc.gnss.sdk.CHC_FILE_RECORD_METHOD;
import com.chc.gnss.sdk.CHC_FILE_RECORD_RECORD_TIME_PERIOD;
import com.chc.gnss.sdk.CHC_FILE_RECORD_STATUS;
import com.chc.gnss.sdk.CHC_FILE_RECORD_SURVEY_METHOD;
import com.chc.gnss.sdk.CHC_FILE_RECORD_TOTAL_MEMORY;
import com.chc.gnss.sdk.CHC_FTP_SERVER;
import com.chc.gnss.sdk.CHC_FileRecordInfo;
import com.chc.gnss.sdk.CHC_GNSS_DATA;
import com.chc.gnss.sdk.CHC_GNSS_DIFF_TYPE;
import com.chc.gnss.sdk.CHC_GPRSInfo;
import com.chc.gnss.sdk.CHC_GPSTime;
import com.chc.gnss.sdk.CHC_MODEM_COMMUNICATION_MODE;
import com.chc.gnss.sdk.CHC_MODEM_DIAL_ERROR;
import com.chc.gnss.sdk.CHC_MODEM_DIAL_STATUS;
import com.chc.gnss.sdk.CHC_ModemDialParams;
import com.chc.gnss.sdk.CHC_ModemDialStatus;
import com.chc.gnss.sdk.CHC_NETWORK_PROTOCOL;
import com.chc.gnss.sdk.CHC_NMEAData;
import com.chc.gnss.sdk.CHC_NMEA_TYPE;
import com.chc.gnss.sdk.CHC_Position;
import com.chc.gnss.sdk.CHC_Position3D;
import com.chc.gnss.sdk.CHC_RADIO_CHANNEL_PROPERTY;
import com.chc.gnss.sdk.CHC_RADIO_PROTOCOL;
import com.chc.gnss.sdk.CHC_RADIO_SENSITIVITY;
import com.chc.gnss.sdk.CHC_RadioAdvanceProperty;
import com.chc.gnss.sdk.CHC_RadioCallSign;
import com.chc.gnss.sdk.CHC_RadioChannel;
import com.chc.gnss.sdk.CHC_RadioChannelArray;
import com.chc.gnss.sdk.CHC_RadioInfo;
import com.chc.gnss.sdk.CHC_ReceiverInfo;
import com.chc.gnss.sdk.CHC_RoverParams;
import com.chc.gnss.sdk.CHC_SATELLITE_CONSTELLAION;
import com.chc.gnss.sdk.CHC_SOLVE_STATUS;
import com.chc.gnss.sdk.CHC_STATION_MODE;
import com.chc.gnss.sdk.CHC_SatelliteInfo;
import com.chc.gnss.sdk.CHC_SatelliteNumber;
import com.chc.gnss.sdk.CHC_SatellitePosition;
import com.chc.gnss.sdk.CHC_SatellitePrecision;
import com.chc.gnss.sdk.CHC_StringStruct;
import com.chc.gnss.sdk.CHC_Time;
import com.huace.gnssserver.gnss.data.SatelliteInfo;
import com.huace.gnssserver.gnss.data.device.DeviceInfo;
import com.huace.gnssserver.gnss.data.receiver.AddressPort;
import com.huace.gnssserver.gnss.data.receiver.Agency;
import com.huace.gnssserver.gnss.data.receiver.BaseParams;
import com.huace.gnssserver.gnss.data.receiver.CorsInfo;
import com.huace.gnssserver.gnss.data.receiver.Course;
import com.huace.gnssserver.gnss.data.receiver.DataRadioCallsign;
import com.huace.gnssserver.gnss.data.receiver.DataSourceList;
import com.huace.gnssserver.gnss.data.receiver.DopsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumDataFrequency;
import com.huace.gnssserver.gnss.data.receiver.EnumDiffFormat;
import com.huace.gnssserver.gnss.data.receiver.EnumFileRecordMethod;
import com.huace.gnssserver.gnss.data.receiver.EnumFileRecordStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumFileRecordSurveyMethod;
import com.huace.gnssserver.gnss.data.receiver.EnumFileRecordTotalMemory;
import com.huace.gnssserver.gnss.data.receiver.EnumFtpServer;
import com.huace.gnssserver.gnss.data.receiver.EnumModemCommunicationMode;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialError;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumNetworkProtocol;
import com.huace.gnssserver.gnss.data.receiver.EnumNmeaType;
import com.huace.gnssserver.gnss.data.receiver.EnumRadioChannelPropery;
import com.huace.gnssserver.gnss.data.receiver.EnumRadioProtocol;
import com.huace.gnssserver.gnss.data.receiver.EnumRecordTimeLenght;
import com.huace.gnssserver.gnss.data.receiver.EnumSensitivity;
import com.huace.gnssserver.gnss.data.receiver.EnumSolveStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumWorkWay;
import com.huace.gnssserver.gnss.data.receiver.ExpireDate;
import com.huace.gnssserver.gnss.data.receiver.FileRecordInfo;
import com.huace.gnssserver.gnss.data.receiver.GprsInfo;
import com.huace.gnssserver.gnss.data.receiver.GpsTime;
import com.huace.gnssserver.gnss.data.receiver.ModemDialParams;
import com.huace.gnssserver.gnss.data.receiver.ModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.NmeaData;
import com.huace.gnssserver.gnss.data.receiver.Position;
import com.huace.gnssserver.gnss.data.receiver.PositionInfo;
import com.huace.gnssserver.gnss.data.receiver.RadioAdvanceProperty;
import com.huace.gnssserver.gnss.data.receiver.RadioChannel;
import com.huace.gnssserver.gnss.data.receiver.RadioChannelArray;
import com.huace.gnssserver.gnss.data.receiver.RadioInfo;
import com.huace.gnssserver.gnss.data.receiver.ReceiverInfo;
import com.huace.gnssserver.gnss.data.receiver.RoverParams;
import com.huace.gnssserver.gnss.data.receiver.SatelliteNumber;
import com.huace.gnssserver.gnss.data.receiver.SatellitePosition;
import com.huace.gnssserver.gnss.data.receiver.SatellitePrecision;
import com.huace.gnssserver.gnss.data.receiver.StaticObserver;
import com.huace.gnssserver.gnss.data.receiver.Time;

import java.util.ArrayList;
import java.util.List;

import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * this class is a transfer from SDK->java vlass or java class->SDK
 * 
 * @author wangjun
 * 
 */
public class ConversionDataStruct {

	public static short covShort(boolean booleanValue) {
		if (booleanValue) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean covBoolean(short shortValue) {
		if (shortValue > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean covBoolean(char b) {
		return b > 0;
	}

	public static Cmd covCmd(CHC_CMD chcCmd) {
		if (chcCmd == null) {
			return null;
		}
		Cmd cmd = new Cmd();
		cmd.cmd = chcCmd.getCmd();
		cmd.sleepTime = chcCmd.getSleep();
		return cmd;
	}

	public static ReceiverInfo covReceiverInfo(CHC_ReceiverInfo chc_info) {
		ReceiverInfo info = new ReceiverInfo();
		info.setBtType(chc_info.getBtType());
		info.setExpireDate(covExpireDate(chc_info.getExpireDate()));
		info.setFirmwareVersion(chc_info.getFirmwareVersion());
		info.setGprsType(chc_info.getGprsType());
		info.setGSensorType(chc_info.getGSensorType());
		info.setMachineType(chc_info.getMachineType());
		info.setPn(chc_info.getPn());
		info.setRadioType(chc_info.getRadioType());
		info.setRegCode(chc_info.getRegCode());
		info.setSn(chc_info.getSn());
		info.setUltimate(covBoolean(chc_info.getIsUltimate()));
		return info;
	}

	public static DeviceInfo covReceiverDeviceInfo(CHC_DeviceInfo Device_info) {
		DeviceInfo info = new DeviceInfo();
		//TODO

		return info;
	}

	public static ExpireDate covExpireDate(CHC_ExpireDate chc_date) {
		ExpireDate date = new ExpireDate();
		date.setYear(chc_date.getYear());
		date.setMonth(chc_date.getMonth());
		date.setDay(chc_date.getDay());
		date.setExpire(covBoolean(chc_date.getExpire()));
		return date;
	}

	public static SatelliteNumber covSatelliteNumber(
			CHC_SatelliteNumber chcSatNum) {
		if (chcSatNum == null) {
			return null;
		}
		return new SatelliteNumber((int) chcSatNum.getSatNum(),
				(int) chcSatNum.getSatUsedNum());
	}

	public static SatelliteInfo covSatelliteInfo(CHC_SatelliteInfo chc_Info) {
		if (chc_Info == null) {
			return null;
		}
		SatelliteInfo info = new SatelliteInfo();
		info.L1 = chc_Info.getL1();
		info.L2 = chc_Info.getL2();
		info.L5 = chc_Info.getL5();
		info.satType = covConstellaionValue(chc_Info.getConstellation());
		info.azimuth = (short) chc_Info.getAzimuth();
		info.elevation = (byte) chc_Info.getElevation();
		if (chc_Info.getLocked() > 0) {
			info.locked = 1;
		} else {
			info.locked = 0;
		}
		info.prn = (byte) chc_Info.getPrn();
		return info;
	}

	public static int covConstellaionValue(
			CHC_SATELLITE_CONSTELLAION chc_Constellaion) {
		if (chc_Constellaion == null) {
			return 0;
		}
		switch (chc_Constellaion) {
		case CHC_SATELLITE_CONSTELLAION_GPS:
			return 0;
		case CHC_SATELLITE_CONSTELLAION_GLONASS:
			return 1;
		case CHC_SATELLITE_CONSTELLAION_CAMPASE:
			return 2;
		case CHC_SATELLITE_CONSTELLAION_SBAS:
			return 3;
		case CHC_SATELLITE_CONSTELLAION_GALILEO:
			return 4;
		default:
			return 0;
		}
	}

	public static PositionInfo covPositionInfo(CHC_Position chcPosition) {
		if (chcPosition == null) {
			return null;
		}
		PositionInfo positionInfo = new PositionInfo();
		positionInfo.setTime(covTime(chcPosition.getTime()));
		positionInfo.setGpsTime(covGpsTime(chcPosition.getGpsTime()));
		positionInfo.setSatellitePosition(covSatellitePosition(chcPosition
				.getPos()));
		positionInfo.setSatellitePrecision(covSatellitePrecision(chcPosition
				.getPrecision()));
		positionInfo.setDiffAge(chcPosition.getDiffAge());
		return positionInfo;
	}

	public static Time covTime(CHC_Time chcTime) {
		if (chcTime == null) {
			return null;
		}
		Time time = new Time();
		time.setYear(chcTime.getYear());
		time.setMonth(chcTime.getMonth());
		time.setDay(chcTime.getDay());
		time.setHour(chcTime.getHour());
		time.setMinute(chcTime.getMin());
		time.setSecond(chcTime.getSec());
		return time;
	}

	public static GpsTime covGpsTime(CHC_GPSTime chcGpsTime) {
		if (chcGpsTime == null) {
			return null;
		}
		return new GpsTime((int) chcGpsTime.getWeek(),
				(int) chcGpsTime.getSec());
	}

	public static SatellitePosition covSatellitePosition(
			CHC_SatellitePosition chcPos) {
		if (chcPos == null) {
			return null;
		}
		SatellitePosition pos = new SatellitePosition();
		pos.setPosition(covPosition(chcPos.getPos()));
		pos.setEnumSolveStatus(covEnumSolveStatus(chcPos.getSolveStatus()));
		return pos;
	}

	public static SatellitePrecision covSatellitePrecision(
			CHC_SatellitePrecision chcPrecision) {
		if (chcPrecision == null) {
			return null;
		}
		return new SatellitePrecision(chcPrecision.getHpre(),
				chcPrecision.getVpre(), chcPrecision.getXpre(),
				chcPrecision.getYpre(), chcPrecision.getRms());
	}

	public static Position covPosition(CHC_Position3D chcPos) {
		if (chcPos == null) {
			return null;
		}
		return new Position(chcPos.getX(), chcPos.getY(), chcPos.getZ());
	}

	public static EnumSolveStatus covEnumSolveStatus(
			CHC_SOLVE_STATUS chc_SolveStatus) {
		switch (chc_SolveStatus) {
		case CHC_SOLVE_STATUS_NONE:
			return EnumSolveStatus.SOLVE_STATUS_NONE;
		case CHC_SOLVE_STATUS_BASE_RIGHT:
			return EnumSolveStatus.SOLVE_STATUS_BASE_RIGHT;
		case CHC_SOLVE_STATUS_BASE_WRONG:
			return EnumSolveStatus.SOLVE_STATUS_BASE_WRONG;
		case CHC_SOLVE_STATUS_SEARCH_SAT:
			return EnumSolveStatus.SOLVE_STATUS_SEARCH_SAT;
		case CHC_SOLVE_STATUS_SINGLE:
			return EnumSolveStatus.SOLVE_STATUS_SINGLE;
		case CHC_SOLVE_STATUS_RTD:
			return EnumSolveStatus.SOLVE_STATUS_RTD;
		case CHC_SOLVE_STATUS_FLOAT:
			return EnumSolveStatus.SOLVE_STATUS_FLOAT;
		case CHC_SOLVE_STATUS_WAAS:
			return EnumSolveStatus.SOLVE_STATUS_WAAS;
		case CHC_SOLVE_STATUS_WIDE:
			return EnumSolveStatus.SOLVE_STATUS_WIDE;
		case CHC_SOLVE_STATUS_COS:
			return EnumSolveStatus.SOLVE_STATUS_COS;
		case CHC_SOLVE_STATUS_DIFF:
			return EnumSolveStatus.SOLVE_STATUS_DIFF;
		case CHC_SOLVE_STATUS_FIX:
			return EnumSolveStatus.SOLVE_STATUS_FIX;
		default:
			return EnumSolveStatus.SOLVE_STATUS_NONE;
		}
	}

	public static Course covCourse(CHC_Course chcCourse) {
		if (chcCourse == null) {
			return null;
		}
		Course course = new Course();
		course.setCourse(chcCourse.getCourse());
		course.setSpeed(chcCourse.getSpeed());
		return course;
	}

	public static DopsInfo covDopsInfo(CHC_DopsInfo chcInfo) {
		if (chcInfo == null) {
			return null;
		}
		DopsInfo dopsInfo = new DopsInfo();
		dopsInfo.setGdop(chcInfo.getGDop());
		dopsInfo.setHdop(chcInfo.getHDop());
		dopsInfo.setPdop(chcInfo.getPDop());
		dopsInfo.setTdop(chcInfo.getTDop());
		dopsInfo.setVdop(chcInfo.getVDop());
		return dopsInfo;
	}

	public static CHC_FileRecordInfo covFileRecordInfo(FileRecordInfo info) {
		CHC_FileRecordInfo chc_info = new CHC_FileRecordInfo();
		chc_info.setName(info.name);
		chc_info.setAnteType(info.anteType);
		chc_info.setAntHeight((float) info.antHeight);
		chc_info.setCycleStorage(covShort(info.cycleStorage));
		chc_info.setDuration(info.duration);
		chc_info.setElevMask((short) info.elevMask);
		chc_info.setHcnFmt(covShort(info.hcnFmt));
		chc_info.setInternalMemory(covShort(info.internalMemory));
		chc_info.setPointName(info.pointName);
		chc_info.setPointNameLength((short) info.pointNameLength);
		chc_info.setFtpPushChannel(covCHC_FTP_SERVER(info.ftpPushChannel));
		chc_info.setMethod(covCHC_FILE_RECORD_METHOD(info.method));
		chc_info.setSampleFreq(covChcDataFrequency(info.sampleFreq));
		chc_info.setSurveyMethod(covCHC_FILE_RECORD_SURVEY_METHOD(info.surveyMethod));
		chc_info.setTimePeriod(covCHC_FILE_RECORD_RECORD_TIME_PERIOD(info.timePeriod));
		chc_info.setTotalMemory(covCHC_FILE_RECORD_TOTAL_MEMORY(info.totalMemory));
		chc_info.setRinexFmt((short) info.rinexFmt);
		chc_info.setStartTimeEnable(covShort(info.startTimeEnable));
		chc_info.setStartHour(info.startHour);
		chc_info.setStartMinute(info.startMinute);
		chc_info.setAgency(covChcAgency(info.agency));
		chc_info.setBinexFmt(covShort(info.binexFmt));
		chc_info.setClockSharp(covShort(info.clockSharp));
		chc_info.setMemeryStorage(info.memeryStorage);
		chc_info.setHcmFmt(covShort(info.hcmFmt));
		chc_info.setHrcFmt(covShort(info.hrcFmt));
		chc_info.setObserver(covChcObserver(info.observer));
		chc_info.setRecordId((short) info.recordId);
		chc_info.setRinexdFmt(covShort(info.rinexdFmt));
		chc_info.setSingleSampling(covShort(info.singleSampling));
		chc_info.setStartDateEnable(covShort(info.startDateEnable));
		chc_info.setStartYear(info.startYear);
		chc_info.setStartMonth((short) info.startMonth);
		chc_info.setStartDay((short) info.startDay);
		return chc_info;
	}

	private static CHC_StringStruct covChcObserver(StaticObserver observer) {
		if (observer == null) {
			return null;
		}
		CHC_StringStruct chc_observer = new CHC_StringStruct();
		chc_observer.setData(observer.data);
		chc_observer.setLength(observer.length);
		return chc_observer;
	}

	private static CHC_StringStruct covChcAgency(Agency agency) {
		if (agency == null) {
			return null;
		}
		CHC_StringStruct chc_agency = new CHC_StringStruct();
		chc_agency.setData(agency.data);
		chc_agency.setLength(agency.length);
		return chc_agency;
	}

	public static EnumFtpServer covEnumFtpServer(CHC_FTP_SERVER chc_server) {
		switch (chc_server) {
		case CHC_FTP_SERVER_NONE:
			return EnumFtpServer.FTP_SERVER_NONE;
		case CHC_FTP_SERVER_1:
			return EnumFtpServer.FTP_SERVER_1;
		case CHC_FTP_SERVER_2:
			return EnumFtpServer.FTP_SERVER_2;
		case CHC_FTP_SERVER_3:
			return EnumFtpServer.FTP_SERVER_3;
		default:
			return EnumFtpServer.FTP_SERVER_NONE;
		}
	}

	public static CHC_FTP_SERVER covCHC_FTP_SERVER(EnumFtpServer server) {
		switch (server) {
		case FTP_SERVER_NONE:
			return CHC_FTP_SERVER.CHC_FTP_SERVER_NONE;
		case FTP_SERVER_1:
			return CHC_FTP_SERVER.CHC_FTP_SERVER_1;
		case FTP_SERVER_2:
			return CHC_FTP_SERVER.CHC_FTP_SERVER_2;
		case FTP_SERVER_3:
			return CHC_FTP_SERVER.CHC_FTP_SERVER_3;
		default:
			return CHC_FTP_SERVER.CHC_FTP_SERVER_NONE;
		}
	}

	public static EnumFileRecordMethod covEmFileRecordMethod(
			CHC_FILE_RECORD_METHOD chc_method) {
		switch (chc_method) {
		case CHC_FILE_RECORD_METHOD_AUTO:
			return EnumFileRecordMethod.FILE_RECORD_METHOD_AUTO;
		case CHC_FILE_RECORD_METHOD_DISABLE:
			return EnumFileRecordMethod.FILE_RECORD_METHOD_DISABLE;
		case CHC_FILE_RECORD_METHOD_MANUAL:
			return EnumFileRecordMethod.FILE_RECORD_METHOD_MANUAL;
		default:
			return EnumFileRecordMethod.FILE_RECORD_METHOD_AUTO;
		}
	}

	public static CHC_FILE_RECORD_METHOD covCHC_FILE_RECORD_METHOD(
			EnumFileRecordMethod method) {
		switch (method) {
		case FILE_RECORD_METHOD_AUTO:
			return CHC_FILE_RECORD_METHOD.CHC_FILE_RECORD_METHOD_AUTO;
		case FILE_RECORD_METHOD_DISABLE:
			return CHC_FILE_RECORD_METHOD.CHC_FILE_RECORD_METHOD_DISABLE;
		case FILE_RECORD_METHOD_MANUAL:
			return CHC_FILE_RECORD_METHOD.CHC_FILE_RECORD_METHOD_MANUAL;
		default:
			return CHC_FILE_RECORD_METHOD.CHC_FILE_RECORD_METHOD_AUTO;
		}
	}

	public static EnumDataFrequency covEnumDataFrequency(
			CHC_DATA_FREQUENCY chc_req) {
		switch (chc_req) {
		case CHC_DATA_FREQUENCY_OFF:
			return EnumDataFrequency.DATA_FREQUENCY_OFF;
		case CHC_DATA_FREQUENCY_ONCE:
			return EnumDataFrequency.DATA_FREQUENCY_ONCE;
		case CHC_DATA_FREQUENCY_AUTO:
			return EnumDataFrequency.DATA_FREQUENCY_AUTO;
		case CHC_DATA_FREQUENCY_50HZ:
			return EnumDataFrequency.DATA_FREQUENCY_50HZ;
		case CHC_DATA_FREQUENCY_20HZ:
			return EnumDataFrequency.DATA_FREQUENCY_20HZ;
		case CHC_DATA_FREQUENCY_10HZ:
			return EnumDataFrequency.DATA_FREQUENCY_10HZ;
		case CHC_DATA_FREQUENCY_5HZ:
			return EnumDataFrequency.DATA_FREQUENCY_5HZ;
		case CHC_DATA_FREQUENCY_2HZ:
			return EnumDataFrequency.DATA_FREQUENCY_2HZ;
		case CHC_DATA_FREQUENCY_1HZ:
			return EnumDataFrequency.DATA_FREQUENCY_1HZ;
		case CHC_DATA_FREQUENCY_2S:
			return EnumDataFrequency.DATA_FREQUENCY_2S;
		case CHC_DATA_FREQUENCY_5S:
			return EnumDataFrequency.DATA_FREQUENCY_5S;
		case CHC_DATA_FREQUENCY_10S:
			return EnumDataFrequency.DATA_FREQUENCY_10S;
		case CHC_DATA_FREQUENCY_15S:
			return EnumDataFrequency.DATA_FREQUENCY_15S;
		case CHC_DATA_FREQUENCY_20S:
			return EnumDataFrequency.DATA_FREQUENCY_20S;
		case CHC_DATA_FREQUENCY_30S:
			return EnumDataFrequency.DATA_FREQUENCY_30S;
		case CHC_DATA_FREQUENCY_1M:
			return EnumDataFrequency.DATA_FREQUENCY_1M;
		case CHC_DATA_FREQUENCY_5M:
			return EnumDataFrequency.DATA_FREQUENCY_5M;
		case CHC_DATA_FREQUENCY_10M:
			return EnumDataFrequency.DATA_FREQUENCY_10M;
		default:
			return EnumDataFrequency.DATA_FREQUENCY_AUTO;
		}
	}

	public static CHC_DATA_FREQUENCY covChcDataFrequency(EnumDataFrequency req) {
		switch (req) {
		case DATA_FREQUENCY_OFF:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_OFF;
		case DATA_FREQUENCY_ONCE:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_ONCE;
		case DATA_FREQUENCY_AUTO:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_AUTO;
		case DATA_FREQUENCY_50HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_50HZ;
		case DATA_FREQUENCY_20HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_20HZ;
		case DATA_FREQUENCY_10HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10HZ;
		case DATA_FREQUENCY_5HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5HZ;
		case DATA_FREQUENCY_2HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_2HZ;
		case DATA_FREQUENCY_1HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_1HZ;
		case DATA_FREQUENCY_2S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_2S;
		case DATA_FREQUENCY_5S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5S;
		case DATA_FREQUENCY_10S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10S;
		case DATA_FREQUENCY_15S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_15S;
		case DATA_FREQUENCY_20S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_20S;
		case DATA_FREQUENCY_30S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_30S;
		case DATA_FREQUENCY_1M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_1M;
		case DATA_FREQUENCY_5M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5M;
		case DATA_FREQUENCY_10M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10M;
		default:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_AUTO;
		}
	}

	public static EnumFileRecordSurveyMethod covEmFileRecordSurveyMethod(
			CHC_FILE_RECORD_SURVEY_METHOD chc_method) {
		switch (chc_method) {
		case CHC_FILE_RECORD_SURVEY_METHOD_BOTTOM:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_BOTTOM;
		case CHC_FILE_RECORD_SURVEY_METHOD_PHASE_CENTER:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_PHASE_CENTER;
		case CHC_FILE_RECORD_SURVEY_METHOD_SLANT:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_SLANT;
		case CHC_FILE_RECORD_SURVEY_METHOD_TOP:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_TOP;
		case CHC_FILE_RECORD_SURVEY_METHOD_VERTICAL:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_VERTICAL;
		default:
			return EnumFileRecordSurveyMethod.FILE_RECORD_SURVEY_METHOD_BOTTOM;
		}
	}

	public static CHC_FILE_RECORD_SURVEY_METHOD covCHC_FILE_RECORD_SURVEY_METHOD(
			EnumFileRecordSurveyMethod method) {
		switch (method) {
		case FILE_RECORD_SURVEY_METHOD_BOTTOM:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_BOTTOM;
		case FILE_RECORD_SURVEY_METHOD_PHASE_CENTER:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_PHASE_CENTER;
		case FILE_RECORD_SURVEY_METHOD_SLANT:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_SLANT;
		case FILE_RECORD_SURVEY_METHOD_TOP:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_TOP;
		case FILE_RECORD_SURVEY_METHOD_VERTICAL:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_VERTICAL;
		default:
			return CHC_FILE_RECORD_SURVEY_METHOD.CHC_FILE_RECORD_SURVEY_METHOD_BOTTOM;
		}
	}

	public static EnumRecordTimeLenght covEnumRecordTimeLenght(
			CHC_FILE_RECORD_RECORD_TIME_PERIOD chc) {
		switch (chc) {
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_MANUAL:
			return EnumRecordTimeLenght.TIME_MANUAL;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_1HOUR:
			return EnumRecordTimeLenght.TIME_1H;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_2HOUR:
			return EnumRecordTimeLenght.TIME_2H;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_3HOUR:
			return EnumRecordTimeLenght.TIME_3H;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_6HOUR:
			return EnumRecordTimeLenght.TIME_6H;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_12HOUR:
			return EnumRecordTimeLenght.TIME_12H;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_1DAY:
			return EnumRecordTimeLenght.TIME_1DAY;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_2DAYS:
			return EnumRecordTimeLenght.TIME_2DAY;
		case CHC_FILE_RECORD_RECORD_TIME_PERIOD_1WEEK:
			return EnumRecordTimeLenght.TIME_1WEEK;
		default:
			return EnumRecordTimeLenght.TIME_MANUAL;
		}
	}

	public static CHC_FILE_RECORD_RECORD_TIME_PERIOD covCHC_FILE_RECORD_RECORD_TIME_PERIOD(
			EnumRecordTimeLenght len) {
		switch (len) {
		case TIME_MANUAL:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_MANUAL;
		case TIME_1H:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_1HOUR;
		case TIME_2H:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_2HOUR;
		case TIME_3H:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_3HOUR;
		case TIME_6H:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_6HOUR;
		case TIME_12H:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_12HOUR;
		case TIME_1DAY:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_1DAY;
		case TIME_2DAY:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_2DAYS;
		case TIME_1WEEK:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_1WEEK;
		default:
			return CHC_FILE_RECORD_RECORD_TIME_PERIOD.CHC_FILE_RECORD_RECORD_TIME_PERIOD_MANUAL;
		}
	}

	public static EnumFileRecordTotalMemory covEmFileRecordTotalMemory(
			CHC_FILE_RECORD_TOTAL_MEMORY chc) {
		switch (chc) {
		case CHC_FILE_RECORD_TOTAL_MEMORY_64M:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_64M;
		case CHC_FILE_RECORD_TOTAL_MEMORY_4G:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_4G;
		case CHC_FILE_RECORD_TOTAL_MEMORY_8G:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_8G;
		case CHC_FILE_RECORD_TOTAL_MEMORY_16G:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_16G;
		case CHC_FILE_RECORD_TOTAL_MEMORY_32G:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_32G;
		default:
			return EnumFileRecordTotalMemory.FILE_RECORD_TOTAL_MEMORY_64M;
		}
	}

	public static CHC_FILE_RECORD_TOTAL_MEMORY covCHC_FILE_RECORD_TOTAL_MEMORY(
			EnumFileRecordTotalMemory chc) {
		switch (chc) {
		case FILE_RECORD_TOTAL_MEMORY_64M:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_64M;
		case FILE_RECORD_TOTAL_MEMORY_4G:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_4G;
		case FILE_RECORD_TOTAL_MEMORY_8G:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_8G;
		case FILE_RECORD_TOTAL_MEMORY_16G:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_16G;
		case FILE_RECORD_TOTAL_MEMORY_32G:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_32G;
		default:
			return CHC_FILE_RECORD_TOTAL_MEMORY.CHC_FILE_RECORD_TOTAL_MEMORY_64M;
		}
	}

	public static EnumFileRecordStatus covFileRecordStatus(
			CHC_FILE_RECORD_STATUS status) {
		switch (status) {
		case CHC_FILE_RECORD_STATUS_ON:
			return EnumFileRecordStatus.FILE_RECORD_STATUS_ON;
		case CHC_FILE_RECORD_STATUS_OFF:
			return EnumFileRecordStatus.FILE_RECORD_STATUS_OFF;
		case CHC_FILE_RECORD_STATUS_ERR:
			return EnumFileRecordStatus.FILE_RECORD_STATUS_ERR;
		default:
			return EnumFileRecordStatus.FILE_RECORD_STATUS_ON;
		}
	}

	public static FileRecordInfo covFileRecordInfo(CHC_FileRecordInfo chc_info) {
		if (chc_info == null) {
			return null;
		}
		FileRecordInfo info = new FileRecordInfo();
		info.name = chc_info.getName();
		info.anteType = chc_info.getAnteType();
		info.antHeight = chc_info.getAntHeight();
		info.cycleStorage = covBoolean(chc_info.getCycleStorage());
		info.duration = (int) chc_info.getDuration();
		info.elevMask = chc_info.getElevMask();
		info.ftpPushChannel = covEnumFtpServer(chc_info.getFtpPushChannel());
		info.hcnFmt = covBoolean(chc_info.getHcnFmt());
		info.internalMemory = covBoolean(chc_info.getInternalMemory());

		info.method = covEmFileRecordMethod(chc_info.getMethod());
		info.pointName = chc_info.getPointName();
		info.pointNameLength = chc_info.getPointNameLength();
		info.rinexFmt = chc_info.getRinexFmt();
		info.sampleFreq = covEnumDataFrequency(chc_info.getSampleFreq());
		info.surveyMethod = covEmFileRecordSurveyMethod(chc_info
				.getSurveyMethod());
		info.timePeriod = covEnumRecordTimeLenght(chc_info.getTimePeriod());
		info.totalMemory = covEmFileRecordTotalMemory(chc_info.getTotalMemory());
		info.startTimeEnable = covBoolean(chc_info.getStartTimeEnable());
		info.startHour = (int) chc_info.getStartHour();
		info.startMinute = (int) chc_info.getStartMinute();

		info.agency = covAgency(chc_info.getAgency());
		info.binexFmt = covBoolean(chc_info.getBinexFmt());
		info.clockSharp = covBoolean(chc_info.getClockSharp());
		info.memeryStorage = chc_info.getMemeryStorage();
		info.hcmFmt = covBoolean(chc_info.getHcmFmt());
		info.hrcFmt = covBoolean(chc_info.getHrcFmt());
		info.observer = covStaticObserver(chc_info.getObserver());
		info.recordId = chc_info.getRecordId();
		info.rinexdFmt = covBoolean(chc_info.getRinexdFmt());
		info.singleSampling = covBoolean(chc_info.getSingleSampling());
		info.startDateEnable = covBoolean(chc_info.getStartDateEnable());
		info.startYear = chc_info.getStartYear();
		info.startMonth = chc_info.getStartMonth();
		info.startDay = chc_info.getStartDay();
		return info;
	}

	private static StaticObserver covStaticObserver(
			CHC_StringStruct chc_observer) {
		if (chc_observer == null) {
			return null;
		}
		StaticObserver observer = new StaticObserver();
		observer.data = chc_observer.getData();
		observer.length = chc_observer.getLength();
		return observer;
	}

	private static Agency covAgency(CHC_StringStruct chc_agency) {
		if (chc_agency == null) {
			return null;
		}
		Agency agency = new Agency();
		agency.data = chc_agency.getData();
		agency.length = chc_agency.getLength();
		return agency;
	}

	public static RadioInfo covRadioChannel(CHC_RadioInfo chc_info) {
		RadioInfo info = new RadioInfo();
		info.id = (int) chc_info.getId();
		info.baudrate = (int) chc_info.getBaudrate();
		info.baudrateList = chc_info.getBaudrateList();
		info.frequency = chc_info.getFrequency();
		info.maxFreq = chc_info.getMaxFreq();
		info.minFreq = chc_info.getMinFreq();
		info.power = (int) chc_info.getPower();
		info.powerList = chc_info.getPowerList();
		info.operationList = (int) chc_info.getOperationList();
		info.protocol = covEnumRadioProtocol(chc_info.getProtocol());
		info.protocolList = (int) chc_info.getProtocolList();
		info.stepper = (int) chc_info.getStepper();
		info.stepperList = chc_info.getStepperList();
		info.satelProperty = covRadioAdvanceProperty(chc_info
				.getSatelProperty());
		return info;
	}

	public static EnumRadioProtocol covEnumRadioProtocol(
			CHC_RADIO_PROTOCOL protocol) {
		switch (protocol) {
		case CHC_RADIO_PROTOCOL_CHC:
			return EnumRadioProtocol.RADIO_PROTOCOL_CHC;
		case CHC_RADIO_PROTOCOL_TRANSPARENT:
			return EnumRadioProtocol.RADIO_PROTOCOL_TRANSPARENT;
		case CHC_RADIO_PROTOCOL_TT450:
			return EnumRadioProtocol.RADIO_PROTOCOL_TT450;
		case CHC_RADIO_PROTOCOL_SATEL_3AS:
			return EnumRadioProtocol.RADIO_PROTOCOL_SATEL_3AS;
		case CHC_RADIO_PROTOCOL_PCC_4FSK:
			return EnumRadioProtocol.RADIO_PROTOCOL_PCC_4FSK;
		default:
			return EnumRadioProtocol.RADIO_PROTOCOL_CHC;
		}
	}

	public static RadioAdvanceProperty covRadioAdvanceProperty(
			CHC_RadioAdvanceProperty chc_property) {
		RadioAdvanceProperty property = new RadioAdvanceProperty();
		property.baudrate = chc_property.getBaudrate();
		property.fecEnable = covBoolean(chc_property.getFecEnable());
		property.stepper = chc_property.getStepper();
		property.supported = chc_property.getSupported();
		property.sensitivity = covEmSensitivity(chc_property.getSensitivity());
		property.protocol = chc_property.getProtocol();
		property.callsign = covDataRadioCallsign(chc_property.getCallsign());
		return property;
	}

	public static EnumSensitivity covEmSensitivity(
			CHC_RADIO_SENSITIVITY sensitivity) {
		switch (sensitivity) {
		case CHC_RADIO_SENSITIVITY_HIGH:
			return EnumSensitivity.RADIO_SENSITIVITY_HIGH;
		case CHC_RADIO_SENSITIVITY_MIDDLE:
			return EnumSensitivity.RADIO_SENSITIVITY_MIDDLE;
		case CHC_RADIO_SENSITIVITY_LOW:
			return EnumSensitivity.RADIO_SENSITIVITY_LOW;
		default:
			return EnumSensitivity.RADIO_SENSITIVITY_NONE;
		}
	}

	public static DataRadioCallsign covDataRadioCallsign(
			CHC_RadioCallSign chc_callSign) {
		DataRadioCallsign callSign = new DataRadioCallsign();
		callSign.isEnable = covBoolean(chc_callSign.getEnable());
		callSign.interval = (int) chc_callSign.getInterval();
		callSign.message = chc_callSign.getMessage();
		return callSign;
	}

	public static RadioChannelArray covRadioChannelArray(
			CHC_RadioChannelArray chc_channelList) {
		if (chc_channelList == null || chc_channelList.getLength() == 0) {
			return null;
		}
		RadioChannelArray channelList = new RadioChannelArray();
		int length = chc_channelList.getLength();
		channelList.setLength(length);
		RadioChannel[] mItems = new RadioChannel[length];
		for (int i = 0; i < length; i++) {
			if (chc_channelList.getItems()[i] == null) {
				continue;
			}
			mItems[i] = covRadioChannel(chc_channelList.getItems()[i]);
		}
		channelList.setmItems(mItems);
		return channelList;
	}

	public static RadioChannel covRadioChannel(CHC_RadioChannel chc_Channel) {
		RadioChannel channel = new RadioChannel();
		channel.setChannelFrq(chc_Channel.getChannelFrq());
		channel.setPropery(covEmRadioChannelPropery(chc_Channel.getPropty()));
		return channel;
	}

	public static EnumRadioChannelPropery covEmRadioChannelPropery(
			CHC_RADIO_CHANNEL_PROPERTY chc) {
		switch (chc) {
		case CHC_RADIO_CHANNEL_PROPERTY_DISABLE:
			return EnumRadioChannelPropery.RADIO_CHANNEL_PROPERTY_DISABLE;
		case CHC_RADIO_CHANNEL_PROPERTY_RX:
			return EnumRadioChannelPropery.RADIO_CHANNEL_PROPERTY_RX;
		case CHC_RADIO_CHANNEL_PROPERTY_RXTX:
			return EnumRadioChannelPropery.RADIO_CHANNEL_PROPERTY_RXTX;
		case CHC_RADIO_CHANNEL_PROPERTY_TX:
			return EnumRadioChannelPropery.RADIO_CHANNEL_PROPERTY_TX;
		default:
			return EnumRadioChannelPropery.RADIO_CHANNEL_PROPERTY_DISABLE;
		}
	}

	public static CHC_RADIO_PROTOCOL covChcRadioProtocol(
			EnumRadioProtocol protocol) {
		switch (protocol) {
		case RADIO_PROTOCOL_CHC:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_CHC;
		case RADIO_PROTOCOL_TRANSPARENT:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_TRANSPARENT;
		case RADIO_PROTOCOL_TT450:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_TT450;
		case RADIO_PROTOCOL_SATEL_3AS:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_SATEL_3AS;
		case RADIO_PROTOCOL_PCC_4FSK:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_PCC_4FSK;
		default:
			return CHC_RADIO_PROTOCOL.CHC_RADIO_PROTOCOL_CHC;
		}
	}

	public static CHC_RoverParams covChcRoverParams(RoverParams params) {
		CHC_RoverParams chcParams = new CHC_RoverParams();
		chcParams.setElevMask(params.getElevateAngle());
		chcParams.setPdopMask((long) params.getPdoplimist());
		return chcParams;
	}

	public static CHC_BaseParams covChcBaseParams(BaseParams params) {
		CHC_BaseParams chc_Params = new CHC_BaseParams();
		chc_Params.setAutoStart(covShort(params.isAutoStart()));
		chc_Params.setBaudRate(params.getBaudrate());
		chc_Params.setElevMask(params.getElevateAngle());
		chc_Params.setDiffType(covChcGnssDiffType(params.getEnumDiffFormat()));
		chc_Params.setPdopMask((long) params.getPdopMask());
		chc_Params.setPort(params.getPort());
		chc_Params.setLat(params.getLat());
		chc_Params.setLon(params.getLon());
		chc_Params.setHeight(params.getHeight());
		return chc_Params;
	}

	public static CHC_GNSS_DIFF_TYPE covChcGnssDiffType(EnumDiffFormat protocol) {
		switch (protocol) {
		case GNSS_DIFF_TYPE_CMR:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_CMR;
		case GNSS_DIFF_TYPE_CMR_PLUS:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_CMR_PLUS;
		case GNSS_DIFF_TYPE_SCMR:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_SCMR;
		case GNSS_DIFF_TYPE_RTCM:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTCM;
		case GNSS_DIFF_TYPE_RTCMV3:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTCMV3;
		case GNSS_DIFF_TYPE_RTCMV32:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTCMV32;
		case GNSS_DIFF_TYPE_RTCA:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTCA;
		case GNSS_DIFF_TYPE_RTCMV2:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTCMV2;
		case GNSS_DIFF_TYPE_RTD:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_RTD;
		case GNSS_DIFF_TYPE_AUTO:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_AUTO;
		case GNSS_DIFF_TYPE_NOVATELX:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_NOVATELX;
		default:
			return CHC_GNSS_DIFF_TYPE.CHC_GNSS_DIFF_TYPE_AUTO;
		}
	}

	public static BaseParams covChcBaseParams(CHC_BaseParams chc_params) {
		BaseParams params = new BaseParams();
		params.setIsAutoStart(covBoolean(chc_params.getAutoStart()));
		params.setBaudrate((int) chc_params.getBaudRate());
		params.setElevateAngle((int) chc_params.getElevMask());
		params.setEnumDiffFormat(covChcEmDiffFormat(chc_params.getDiffType()));
		params.setPdopMask(chc_params.getPdopMask());
		params.setPort((int) chc_params.getPort());
		params.setLat(chc_params.getLat());
		params.setLon(chc_params.getLon());
		params.setHeight(chc_params.getHeight());
		return params;
	}

	public static EnumDiffFormat covChcEmDiffFormat(CHC_GNSS_DIFF_TYPE type) {
		switch (type) {
		case CHC_GNSS_DIFF_TYPE_CMR:
			return EnumDiffFormat.GNSS_DIFF_TYPE_CMR;
		case CHC_GNSS_DIFF_TYPE_CMR_PLUS:
			return EnumDiffFormat.GNSS_DIFF_TYPE_CMR_PLUS;
		case CHC_GNSS_DIFF_TYPE_SCMR:
			return EnumDiffFormat.GNSS_DIFF_TYPE_SCMR;
		case CHC_GNSS_DIFF_TYPE_RTCM:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTCM;
		case CHC_GNSS_DIFF_TYPE_RTCMV3:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTCMV3;
		case CHC_GNSS_DIFF_TYPE_RTCMV32:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTCMV32;
		case CHC_GNSS_DIFF_TYPE_RTCA:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTCA;
		case CHC_GNSS_DIFF_TYPE_RTCMV2:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTCMV2;
		case CHC_GNSS_DIFF_TYPE_RTD:
			return EnumDiffFormat.GNSS_DIFF_TYPE_RTD;
		case CHC_GNSS_DIFF_TYPE_AUTO:
			return EnumDiffFormat.GNSS_DIFF_TYPE_AUTO;
		case CHC_GNSS_DIFF_TYPE_NOVATELX:
			return EnumDiffFormat.GNSS_DIFF_TYPE_NOVATELX;
		default:
			return EnumDiffFormat.GNSS_DIFF_TYPE_AUTO;
		}
	}

	public static EnumWorkWay covChcEmWorkWay(CHC_STATION_MODE mode) {
		switch (mode) {
		case CHC_STATION_MODE_ROVER:
			return EnumWorkWay.AUTO_ROVER;
		case CHC_STATION_MODE_AUTO_BASE:
			return EnumWorkWay.AUTO_BASE;
		case CHC_STATION_MODE_MANUAL_BASE:
			return EnumWorkWay.MANUAL;
		default:
			return null;
		}
	}

	public static CHC_GNSS_DATA covChcGnssData(EnumDiffFormat diffFormat) {
		switch (diffFormat) {
		case GNSS_DIFF_TYPE_CMR_PLUS:
			return CHC_GNSS_DATA.CHC_GNSS_DATA_CMR_PLUS;
		case GNSS_DIFF_TYPE_RTCMV2:
			return CHC_GNSS_DATA.CHC_GNSS_DATA_RTCMV3;
		case GNSS_DIFF_TYPE_RTCMV3:
			return CHC_GNSS_DATA.CHC_GNSS_DATA_RTCMV3;
		default:
			return CHC_GNSS_DATA.CHC_GNSS_DATA_RTCMV3;
		}
	}

	public static CHC_GPRSInfo covChcGrpsInfo(GprsInfo info) {
		CHC_GPRSInfo chc_info = new CHC_GPRSInfo();
		chc_info.setProtocol(covCHC_NETWORK_PROTOCOL(info.getProtocol()));
		chc_info.setAddressPort(covCHC_AddressPort(info.getAddressPort()));
		chc_info.setBaseId(info.getBaseId());
		return chc_info;
	}

	public static CHC_NETWORK_PROTOCOL covCHC_NETWORK_PROTOCOL(
			EnumNetworkProtocol protocol) {
		switch (protocol) {
		case NETWORK_PROTOCOL_APIS_BASE:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_APIS_BASE;
		case NETWORK_PROTOCOL_APIS_ROVER:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_APIS_ROVER;
		case NETWORK_PROTOCOL_NTRIP_BASE:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_NTRIP_BASE;
		case NETWORK_PROTOCOL_NTRIP_ROVER:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_NTRIP_ROVER;
		case NETWORK_PROTOCOL_TCP:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_TCP;
		case NETWORK_PROTOCOL_UDP:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_UDP;
		default:
			return CHC_NETWORK_PROTOCOL.CHC_NETWORK_PROTOCOL_APIS_BASE;
		}
	}

	public static CHC_AddressPort covCHC_AddressPort(AddressPort ap) {
		CHC_AddressPort chc_AddressPort = new CHC_AddressPort();
		chc_AddressPort.setAddress(ap.getDomain());
		chc_AddressPort.setPort(ap.getPort());
		chc_AddressPort.setUseDoMain(covShort(ap.isUseDoMain()));
		return chc_AddressPort;
	}

	public static CHC_CORSInfo covChcCorsInfo(CorsInfo info) {
		CHC_CORSInfo chc_info = new CHC_CORSInfo();
		chc_info.setUserName(info.getUserName());
		chc_info.setPassword(info.getPassword());
		chc_info.setSource(info.getSource());
		return chc_info;
	}

	public static CHC_MODEM_COMMUNICATION_MODE covCHC_MODEM_COMMUNICATION_MODE(
			EnumModemCommunicationMode mode) {
		switch (mode) {
		case MODEM_COMMUNICATION_MODE_CSD:
			return CHC_MODEM_COMMUNICATION_MODE.CHC_MODEM_COMMUNICATION_MODE_CSD;
		case MODEM_COMMUNICATION_MODE_GPRS:
			return CHC_MODEM_COMMUNICATION_MODE.CHC_MODEM_COMMUNICATION_MODE_GPRS;
		default:
			return CHC_MODEM_COMMUNICATION_MODE.CHC_MODEM_COMMUNICATION_MODE_NONE;
		}
	}

	public static CHC_ModemDialParams covCHC_ModemDialParams(ModemDialParams apn) {
		CHC_ModemDialParams chc_ModemDialParams = new CHC_ModemDialParams();
		chc_ModemDialParams.setApn(apn.strApn);
		chc_ModemDialParams.setDialNumber(apn.strDialNum);
		chc_ModemDialParams.setAccount(apn.strDialName);
		chc_ModemDialParams.setPassword(apn.strDialPswd);
		return chc_ModemDialParams;
	}

	public static ModemDialParams covModemDialParams(CHC_ModemDialParams params) {
		ModemDialParams apn = new ModemDialParams();
		apn.strApn = params.getApn();
		apn.strDialNum = params.getDialNumber();
		apn.strDialName = params.getAccount();
		apn.strDialPswd = params.getPassword();
		return apn;
	}

	public static DataSourceList covDataSourceList(CHC_Buffer chc_data) {
		DataSourceList data = new DataSourceList();
		data.setBytes(chc_data.getData());
		return data;
	}

	public static ModemDialStatus covModemDialStatus(
			CHC_ModemDialStatus chc_status) {
		ModemDialStatus status = new ModemDialStatus();
		status.setCount((int) chc_status.getCount());
		status.setStatus(covEmModemDialStatus(chc_status.getStatus()));
		status.setError(covEmModemDialError(chc_status.getError()));
		return status;
	}

	public static EnumModemDialStatus covEmModemDialStatus(
			CHC_MODEM_DIAL_STATUS chc_status) {
		switch (chc_status) {
		case CHC_MODEM_DIAL_STATUS_INIT:
			return EnumModemDialStatus.MODEM_DIAL_STATUS_INIT;
		case CHC_MODEM_DIAL_STATUS_DIAL_ON:
			return EnumModemDialStatus.MODEM_DIAL_STATUS_DIAL_ON;
		case CHC_MODEM_DIAL_STATUS_DIALING:
			return EnumModemDialStatus.MODEM_DIAL_STATUS_DIALING;
		default:
			return EnumModemDialStatus.MODEM_DIAL_STATUS_INIT;
		}
	}

	public static EnumModemDialError covEmModemDialError(
			CHC_MODEM_DIAL_ERROR chc_error) {
		switch (chc_error) {
		case CHC_MODEM_DIAL_ERROR_DIAL:
			return EnumModemDialError.MODEM_DIAL_ERROR_DIAL;
		case CHC_MODEM_DIAL_ERROR_LOW_SIGNAL:
			return EnumModemDialError.MODEM_DIAL_ERROR_LOW_SIGNAL;
		case CHC_MODEM_DIAL_ERROR_REGISTRATION:
			return EnumModemDialError.MODEM_DIAL_ERROR_REGISTRATION;
		case CHC_MODEM_DIAL_ERROR_SIM_CARD:
			return EnumModemDialError.MODEM_DIAL_ERROR_SIM_CARD;
		case CHC_MODEM_DIAL_ERROR_NONE:
			return EnumModemDialError.MODEM_DIAL_ERROR_NONE;
		default:
			return EnumModemDialError.MODEM_DIAL_ERROR_UNKNOWN;
		}
	}

	public static EnumNmeaType covChcEmNmeaType(CHC_NMEA_TYPE type) {
		switch (type) {
		case CHC_NMEA_TYPE_NONE:
			return EnumNmeaType.NMEA_TYPE_NONE;
		case CHC_NMEA_TYPE_GPGGA:
			return EnumNmeaType.NMEA_TYPE_GPGGA;
		case CHC_NMEA_TYPE_GPGSA:
			return EnumNmeaType.NMEA_TYPE_GPGSA;
		case CHC_NMEA_TYPE_GPGLL:
			return EnumNmeaType.NMEA_TYPE_GPGLL;
		case CHC_NMEA_TYPE_GPGST:
			return EnumNmeaType.NMEA_TYPE_GPGST;
		case CHC_NMEA_TYPE_GPGSV:
			return EnumNmeaType.NMEA_TYPE_GPGSV;
		case CHC_NMEA_TYPE_GPRMC:
			return EnumNmeaType.NMEA_TYPE_GPRMC;
		case CHC_NMEA_TYPE_GPVTG:
			return EnumNmeaType.NMEA_TYPE_GPVTG;
		case CHC_NMEA_TYPE_GPZDA:
			return EnumNmeaType.NMEA_TYPE_GPZDA;
		case CHC_NMEA_TYPE_GPALM:
			return EnumNmeaType.NMEA_TYPE_GPALM;
		case CHC_NMEA_TYPE_GPRMB:
			return EnumNmeaType.NMEA_TYPE_GPRMB;
		case CHC_NMEA_TYPE_GPGRS:
			return EnumNmeaType.NMEA_TYPE_GPGRS;
		case CHC_NMEA_TYPE_PTNL_PJK:
			return EnumNmeaType.NMEA_TYPE_PTNL_PJK;
		case CHC_NMEA_TYPE_PTNL_PJT:
			return EnumNmeaType.NMEA_TYPE_PTNL_PJT;
		case CHC_NMEA_TYPE_GPGNS:
			return EnumNmeaType.NMEA_TYPE_GPGNS;
		case CHC_NMEA_TYPE_GPRRE:
			return EnumNmeaType.NMEA_TYPE_GPRRE;
		case CHC_NMEA_TYPE_GPADV:
			return EnumNmeaType.NMEA_TYPE_GPADV;
		case CHC_NMEA_TYPE_GPGBS:
			return EnumNmeaType.NMEA_TYPE_GPGBS;
		case CHC_NMEA_TYPE_GPHDT:
			return EnumNmeaType.NMEA_TYPE_GPHDT;
		case CHC_NMEA_TYPE_PTNL_AVR:
			return EnumNmeaType.NMEA_TYPE_PTNL_AVR;
		case CHC_NMEA_TYPE_PTNL_BPQ:
			return EnumNmeaType.NMEA_TYPE_PTNL_BPQ;
		case CHC_NMEA_TYPE_PTNL_GGK:
			return EnumNmeaType.NMEA_TYPE_PTNL_GGK;
		case CHC_NMEA_TYPE_PTNL_VGK:
			return EnumNmeaType.NMEA_TYPE_PTNL_VGK;
		case CHC_NMEA_TYPE_PTNL_VHD:
			return EnumNmeaType.NMEA_TYPE_PTNL_VHD;
		case CHC_NMEA_TYPE_GPROT:
			return EnumNmeaType.NMEA_TYPE_GPROT;
		default:
			return EnumNmeaType.NMEA_TYPE_NONE;
		}
	}

	/**
	 * 获取EnumDataFrequency列表
	 */
	public static List<EnumDataFrequency> covEnumDataFrequencyList(
			CHC_DATA_FREQUENCY[] chcDataFrequencies) {
		List<EnumDataFrequency> listFreq = new ArrayList<EnumDataFrequency>();
		for (CHC_DATA_FREQUENCY f : chcDataFrequencies) {
			if (f != null) {
				EnumDataFrequency fre = covEnumDataFrequency(f);
				if (fre != null) {
					listFreq.add(fre);
				}
			}
		}
		return listFreq;
	}

	public static CHC_NMEAData covCHC_NMEAData(NmeaData data) {
		CHC_NMEAData chc_data = new CHC_NMEAData();
		chc_data.setData(covCHC_NMEA_TYPE(data.getChcEmNmeaType()));
		chc_data.setFreq(covCHC_DATA_FREQUENCY(data.getEnumDataFrequency()));
		return chc_data;
	}

	public static CHC_NMEA_TYPE covCHC_NMEA_TYPE(EnumNmeaType type) {
		switch (type) {
		case NMEA_TYPE_NONE:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_NONE;
		case NMEA_TYPE_GPGGA:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGGA;
		case NMEA_TYPE_GPGSA:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGSA;
		case NMEA_TYPE_GPGLL:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGLL;
		case NMEA_TYPE_GPGST:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGST;
		case NMEA_TYPE_GPGSV:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGSV;
		case NMEA_TYPE_GPRMC:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPRMC;
		case NMEA_TYPE_GPVTG:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPVTG;
		case NMEA_TYPE_GPZDA:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPZDA;
		case NMEA_TYPE_GPALM:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPALM;
		case NMEA_TYPE_GPRMB:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPRMB;
		case NMEA_TYPE_GPGRS:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGRS;
		case NMEA_TYPE_PTNL_PJK:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_PJK;
		case NMEA_TYPE_PTNL_PJT:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_PJT;
		case NMEA_TYPE_GPGNS:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGNS;
		case NMEA_TYPE_GPRRE:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPRRE;
		case NMEA_TYPE_GPADV:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPADV;
		case NMEA_TYPE_GPGBS:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPGBS;
		case NMEA_TYPE_GPHDT:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPHDT;
		case NMEA_TYPE_PTNL_AVR:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_AVR;
		case NMEA_TYPE_PTNL_BPQ:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_BPQ;
		case NMEA_TYPE_PTNL_GGK:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_GGK;
		case NMEA_TYPE_PTNL_VGK:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_VGK;
		case NMEA_TYPE_PTNL_VHD:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_PTNL_VHD;
		case NMEA_TYPE_GPROT:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_GPROT;
		default:
			return CHC_NMEA_TYPE.CHC_NMEA_TYPE_NONE;
		}
	}

	public static CHC_DATA_FREQUENCY covCHC_DATA_FREQUENCY(EnumDataFrequency req) {
		switch (req) {
		case DATA_FREQUENCY_OFF:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_OFF;
		case DATA_FREQUENCY_ONCE:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_ONCE;
		case DATA_FREQUENCY_AUTO:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_AUTO;
		case DATA_FREQUENCY_50HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_50HZ;
		case DATA_FREQUENCY_20HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_20HZ;
		case DATA_FREQUENCY_10HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10HZ;
		case DATA_FREQUENCY_5HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5HZ;
		case DATA_FREQUENCY_2HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_2HZ;
		case DATA_FREQUENCY_1HZ:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_1HZ;
		case DATA_FREQUENCY_2S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_2S;
		case DATA_FREQUENCY_5S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5S;
		case DATA_FREQUENCY_10S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10S;
		case DATA_FREQUENCY_15S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_15S;
		case DATA_FREQUENCY_20S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_20S;
		case DATA_FREQUENCY_30S:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_30S;
		case DATA_FREQUENCY_1M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_1M;
		case DATA_FREQUENCY_5M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_5M;
		case DATA_FREQUENCY_10M:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_10M;
		default:
			return CHC_DATA_FREQUENCY.CHC_DATA_FREQUENCY_AUTO;
		}
	}
}
