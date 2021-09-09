package kr.loplab.gnss05.receiver;

import com.chc.gnss.sdk.CHC_DATA_FREQUENCY;
import com.chc.gnss.sdk.CHC_NONE_MAGNETIC_SUPPORT_TYPE;
import com.chc.gnss.sdk.CHC_NoneMagneticTiltInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumNoneMagneticSupportType;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticSetParams;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticTiltInfo;

/**
 * 기울기 측정 데이터 변환
 * Created by wangjun on 2018/9/6.
 */

public class CovMagneticTilt {

    /**
     * SDK 데이터 구조를 자체의 non-skew 데이터 구조로 변환
     */
    public static NoneMagneticTiltInfo covNoneMagneticTiltInfo(CHC_NoneMagneticTiltInfo chcTiltInfo) {
        if (chcTiltInfo == null) {
            return null;
        }
        NoneMagneticTiltInfo info = new NoneMagneticTiltInfo();
        info.setControlStatus(ConversionDataStruct.covBoolean(chcTiltInfo.getControlStatus()));
        info.setSecond(chcTiltInfo.getSecond());
        info.setWeek(chcTiltInfo.getWeek());
        info.setBTiltMeasure(chcTiltInfo.getBTiltMeasure());
        info.setVarE(chcTiltInfo.getVar_E());
        info.setVarN(chcTiltInfo.getVar_N());
        info.setVarU(chcTiltInfo.getVar_U());
        info.setVarTilt(chcTiltInfo.getVar_tilt());
        info.setVarDirect(chcTiltInfo.getVar_direct());
        info.setLatitude(chcTiltInfo.getLatitude());
        info.setLontitude(chcTiltInfo.getLontitude());
        info.setHeight(chcTiltInfo.getHeight());
        info.setTime(ConversionDataStruct.covTime(chcTiltInfo.getTime()));
        info.setSolveStatus(ConversionDataStruct.covEnumSolveStatus(chcTiltInfo.getSolveStatus()));
        info.setSatellitePrecision(ConversionDataStruct.covSatellitePrecision(chcTiltInfo.getPrecision()));
        info.setHeightOffset(chcTiltInfo.getHeightOffset());
        info.setDiffAge(chcTiltInfo.getDiffAge());
        info.setPitch(chcTiltInfo.getPitch());
        info.setRoll(chcTiltInfo.getRoll());
        info.setHeading(chcTiltInfo.getHeading());
        return info;
    }

    public static NoneMagneticSetParams covNoneMagneticSetParams(short[] controlStatus,
                                                                 double[] antennaHeight,
                                                                 CHC_DATA_FREQUENCY[] freq) {
        NoneMagneticSetParams params = new NoneMagneticSetParams();
        params.setControlStatus(ConversionDataStruct.covBoolean(controlStatus[0]));
        params.setAntennaHeight(antennaHeight[0]);
        params.setFrequency(ConversionDataStruct.covEnumDataFrequency(freq[0]));
        return params;
    }

    public static EnumNoneMagneticSupportType covNoneMagneticSupportType(CHC_NONE_MAGNETIC_SUPPORT_TYPE mode) {
        switch (mode) {
            case CHC_NONE_MAGNETIC_SUPPORT_NOT_SUPPORT:
                return EnumNoneMagneticSupportType.CHC_NONE_MAGNETIC_SUPPORT_NOT_SUPPORT;
            case CHC_NONE_MAGNETIC_SUPPORT_WALK:
                return EnumNoneMagneticSupportType.CHC_NONE_MAGNETIC_SUPPORT_WALK;
            case CHC_NONE_MAGNETIC_SUPPORT_SHAKE:
                return EnumNoneMagneticSupportType.CHC_NONE_MAGNETIC_SUPPORT_SHAKE;
            default:
                return EnumNoneMagneticSupportType.CHC_NONE_MAGNETIC_SUPPORT_NOT_SUPPORT;
        }
    }

}
