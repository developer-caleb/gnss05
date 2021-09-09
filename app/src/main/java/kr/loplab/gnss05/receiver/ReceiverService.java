package kr.loplab.gnss05.receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.huace.gnssserver.gnss.data.receiver.DopsInfo;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.ReceiverCmd;

import java.util.List;

import kr.loplab.gnss05.receiver.entity.ReceiverAsw;

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
     * 获取广播过滤器
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
            asw.setParcelable(intent
                    .getParcelableExtra(ReceiverService.RECEIVER_DATA));
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
     * 设备信息
     */
    public void post(GetDeviceInfoEventArgs args) {
        if (args.getDeviceInfo() != null) {
            sendBroadcast(args, args.getDeviceInfo());
        }
    }

    /**
     * 接收机电量
     */
    public void post(GetBattteyLifeEventArgs args) {
        // sendBroadcast(args, args.getBatteryLife());
    }

    /**
     * 可用卫星/卫星总数
     */
    public void post(GetSatelliteUsedNumsEventArgs args) {
        if (args.getSatelliteNumber() != null) {
            sendBroadcast(args, args.getSatelliteNumber());
        }
    }

    /**
     * 卫星数据
     */
    public void post(GetSatelliteInfosEventArgs args) {
        if (args.getSatelliteInfos() != null) {
            // sendBroadcast(args, args.getSatelliteInfos());
        }
    }

    /**
     * 经纬度信息
     */
    public void post(GetPositionExEventArgs args) {
        sendBroadcast(args, args.getPositionInfo());
    }

    /**
     * 无磁倾斜数据
     */
    public void post(GetNoneMagneticTiltInfoEventArgs args) {
        sendBroadcast(args, args.getInfo());
    }

    /**
     * 无磁倾斜参数
     */
    public void post(GetNoneMagneticSetParamsEventArgs args) {
        sendBroadcast(args, args.getInfo());
    }

    /**
     * dop值
     */
    public void post(GetGnssDopsEventArgs args) {
        DopsInfo dop = args.getDopsInfo();
        if (dop != null) {
            sendBroadcast(args, dop);
        }
    }

    /**
     * 拨号数据
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
        App.getInstance().sendBroadcast(intent);
    }

    private void sendBroadcast(Intent intent) {
        App.getInstance().sendBroadcast(intent);
    }

    /**
     * 获取到源列表通知
     *
     * @param getSourceListEventArgs
     */
    public void post(GetSourceListEventArgs getSourceListEventArgs) {
        Intent intent = new Intent(GetSourceListEventArgs.class.getName());
        intent.putExtra(RECEIVER_DATA, getSourceListEventArgs);
        sendBroadcast(intent);
    }

    /**
     * 获取到源列表数据
     *
     * @param getSourceListEventArgs
     */
    public void post(GetSourceTableEventArgs getSourceTableEventArgs) {
        GetSourceFromReceiver.getInstance().onEventBackgroundThread(
                getSourceTableEventArgs);
    }

    /**
     * 拨号状态
     *
     * @param args
     */
    public void post(GetModemDialStatusEventArgs args) {
        mModemDialStatus = args.getStatus();
    }


}
