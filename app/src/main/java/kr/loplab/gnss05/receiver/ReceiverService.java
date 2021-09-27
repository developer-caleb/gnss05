package kr.loplab.gnss05.receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.huace.gnssserver.gnss.data.receiver.DopsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.ReceiverCmd;

import java.util.List;

import kr.loplab.gnss05.GlobalApplication;
import kr.loplab.gnss05.receiver.asw.GetBattteyLifeEventArgs;
import kr.loplab.gnss05.receiver.asw.GetDeviceInfoEventArgs;
import kr.loplab.gnss05.receiver.asw.GetGnssDopsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetModemAutoDialParamsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetModemDialStatusEventArgs;
import kr.loplab.gnss05.receiver.asw.GetNoneMagneticSetParamsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetNoneMagneticTiltInfoEventArgs;
import kr.loplab.gnss05.receiver.asw.GetPositionExEventArgs;
import kr.loplab.gnss05.receiver.asw.GetReceiverInfoEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSatelliteInfosEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSatelliteUsedNumsEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSourceListEventArgs;
import kr.loplab.gnss05.receiver.asw.GetSourceTableEventArgs;
import kr.loplab.gnss05.receiver.asw.IReceiverDataEventArgs;
import kr.loplab.gnss05.receiver.entity.ReceiverAsw;
import kr.loplab.gnss05.receiver.sourcelist.GetSourceFromReceiver;

/**
 * Huatest 분석 모듈에서 제공하는 데이터 서비스 인터페이스(시뮬레이션)
 *
 * @author wangjun
 */
public class ReceiverService {

    public static ReceiverService BUS = new ReceiverService();

    public static String RECEIVER_TYPE = "RECEIVER_TYPE";

    public static String RECEIVER_DATA = "RECEIVER_DATA";

    /***
     * 전화 걸기 상태
     */
    private ModemDialStatus mModemDialStatus = new ModemDialStatus();

    private ReceiverService() {
    }

    @Nullable
    public EnumModemDialStatus getModemDialStatus() {
        if (mModemDialStatus == null) {
            return null;
        }
        return mModemDialStatus.getStatus();
    }

    /**
     * Get broadcast filter
     *
     * @return
     */
    public static IntentFilter createReceiverAswIntentFilter(
            List<EnumReceiverCmd> cmds) {
        IntentFilter filter = new IntentFilter();
        for (int i = 0; i < cmds.size(); i++) {
            filter.addAction(cmds.get(i).name());
        }
        return filter;
    }

    /**
     * 방송 데이터 가져오기
     *
     * @return
     */
    public static ReceiverAsw getBroadcastData(Intent intent) {
        Parcelable type = intent
                .getParcelableExtra(ReceiverService.RECEIVER_TYPE);
        if (type instanceof ReceiverCmd) {
            ReceiverAsw asw = new ReceiverAsw();
            asw.setReceiverCmdType(((ReceiverCmd) type).getEnumReceiverCmd());
            asw.setParcelable(intent.getParcelableExtra(ReceiverService.RECEIVER_DATA));
            return asw;
        }
        return null;
    }

    /**
     * 수신기 정보
     */
    public void post(GetReceiverInfoEventArgs args) {
        if (args.getInfo() != null) {
            sendBroadcast(args, args.getInfo());
        }
    }

    /**
     * 장치 정보
     */
    public void post(GetDeviceInfoEventArgs args) {
        if (args.getDeviceInfo() != null) {
            sendBroadcast(args, args.getDeviceInfo());
        }
    }

    /**
     * 수신기 배터리
     */
    public void post(GetBattteyLifeEventArgs args) {
         sendBroadcast(args, args.getBatteryLife()); //int를 intent로 넣어줘야할 듯
    }

    /**
     * 가용 위성/총 위성 수
     */
    public void post(GetSatelliteUsedNumsEventArgs args) {
        if (args.getSatelliteNumber() != null) {
            sendBroadcast(args, args.getSatelliteNumber());
        }
    }

    /**
     * 위성 데이터
     */
    public void post(GetSatelliteInfosEventArgs args) {
        if (args.getSatelliteInfos() != null) {
          //   sendBroadcast(args, args.getSatelliteInfos()); // List<Parcelable> 로 가져옴 list처리를 해줘야할 듯
        }
    }

    /**
     * 위도 및 경도 정보
     */
    public void post(GetPositionExEventArgs args) {
        sendBroadcast(args, args.getPositionInfo());
        sendBroadcast(args, args.getCourse());
    }

    /**
     * 비자성 기울기 데이터
     */
    public void post(GetNoneMagneticTiltInfoEventArgs args) {
        sendBroadcast(args, args.getInfo());
    }

    /**
     * 비자성 기울기 매개변수
     */
    public void post(GetNoneMagneticSetParamsEventArgs args) {
        sendBroadcast(args, args.getInfo());
    }

    /**
     * dop값
     */
    public void post(GetGnssDopsEventArgs args) {
        DopsInfo dop = args.getDopsInfo();
        if (dop != null) {
            sendBroadcast(args, dop);
        }
    }

    /**
     * 다이얼 데이터
     *
     * @param getModemAutoDialParamsEventArgs
     */
    public void post(GetModemAutoDialParamsEventArgs args) {
        sendBroadcast(args, args.getParams());
    }

    private void sendBroadcast(IReceiverDataEventArgs args, Parcelable data) {
        Intent intent = new Intent(args.getDataType().name());
        intent.putExtra(RECEIVER_TYPE, new ReceiverCmd(args.getDataType()));
        if (data != null) {
            intent.putExtra(RECEIVER_DATA, data);
        }
        LocalBroadcastManager.getInstance(GlobalApplication.instance.getApplicationContext()).sendBroadcast(intent);
    }
    private void sendBroadcast(IReceiverDataEventArgs args, int number) {
        Intent intent = new Intent(args.getDataType().name());
        intent.putExtra(RECEIVER_TYPE, new ReceiverCmd(args.getDataType()));
        intent.putExtra(RECEIVER_DATA, number);
        LocalBroadcastManager.getInstance(GlobalApplication.instance.getApplicationContext()).sendBroadcast(intent);
    }

    private void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(GlobalApplication.instance.getApplicationContext()).sendBroadcast(intent);
    }

    /**
     * 소스 목록 알림 받기
     *
     * @param getSourceListEventArgs
     */
    public void post(GetSourceListEventArgs getSourceListEventArgs) {
        Intent intent = new Intent(GetSourceListEventArgs.class.getName());
        intent.putExtra(RECEIVER_DATA, getSourceListEventArgs);
        sendBroadcast(intent);
    }

    /**
     * 소스 목록 데이터 가져오기
     *
     * @param getSourceListEventArgs
     */
    public void post(GetSourceTableEventArgs getSourceTableEventArgs) {
        GetSourceFromReceiver.getInstance().onEventBackgroundThread(
                getSourceTableEventArgs);
    }

    /**
     * 전화 걸기 상태
     *
     * @param args
     */
    public void post(GetModemDialStatusEventArgs args) {
        mModemDialStatus = args.getStatus();
    }


}
