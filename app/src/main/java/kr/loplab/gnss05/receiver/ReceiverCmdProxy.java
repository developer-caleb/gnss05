package kr.loplab.gnss05.receiver;

import android.util.Log;

import com.chc.gnss.sdk.CHC_CMD;
import com.chc.gnss.sdk.CHC_CMDRef;
import com.chc.gnss.sdk.CHC_CORSInfo;
import com.chc.gnss.sdk.CHC_DATA_FREQUENCY;
import com.chc.gnss.sdk.CHC_FILE_RECORD_CHANNEL_ID;
import com.chc.gnss.sdk.CHC_GNSS_IO_ID;
import com.chc.gnss.sdk.CHC_GPRSInfo;
import com.chc.gnss.sdk.CHC_MODEM_COMMUNICATION_MODE;
import com.chc.gnss.sdk.CHC_ModemDialParams;
import com.chc.gnss.sdk.CHC_NMEAData;
import com.chc.gnss.sdk.CHC_NONE_MAGNETIC_MEASURE_TYPE;
import com.chc.gnss.sdk.CHC_Receiver;
import com.chc.gnss.sdk.CHC_ReceiverConstants;
import com.chc.gnss.sdk.CHC_ReceiverRef;
import com.chc.gnss.sdk.CHC_RoverParams;
import com.huace.gnssserver.gnss.data.receiver.EnumModemDialStatus;
import com.huace.gnssserver.gnss.data.receiver.NoneMagneticTiltStartInfo;
import com.huace.gnssserver.gnss.data.receiver.RegisterCode;

import kr.loplab.gnss05.receiver.cmd.GetCmdDialModemEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdDisableOtherIOsEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdOutputNMEAEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdOutputPosDataEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdPowerModemEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdRegReceiverEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdStartNoneMagneticTiltEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdStartRoverEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateCorsInfoEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateGprsInfoEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateModemCommunicationModeEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateModemDialParamsEventArgs;
import kr.loplab.gnss05.receiver.cmd.IReceiverCmdEventArgs;
import kr.loplab.gnss05.receiver.entity.Cmd;

/**
 * baggage and send the command
 *
 * @author wangjun
 */
public class ReceiverCmdProxy {
    String TAG = this.getClass().getSimpleName();
    private Receiver mReceiver;

    private CHC_ReceiverRef mReceiverRef;

    private CHC_CMDRef mCmdRef;

    public static ReceiverCmdProxy BUS = new ReceiverCmdProxy();

    private ReceiverCmdProxy() {
        mReceiver = Receiver.getInstance();
    }

    /**
     * get the command and send
     *
     * @param args the event args of the command
     */
    public void post(IReceiverCmdEventArgs args) {
        if (!mReceiver.isIsRun()) {
            return;
        }
        synchronized (mReceiver.getLock()) {
            mReceiverRef = mReceiver.getReceiverRef();
            if (mReceiverRef == null) {
                return;
            }
            mCmdRef = new CHC_CMDRef();
            switch (args.getCmdType()) {
                case MSG_RECEIVER_CMD__SET_INIT:
                    CHC_Receiver.CHCGetCmdInitReceiver(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_GET_RECEIVER_INFO:
                    // the information of the receiver
                    CHC_Receiver.CHCGetCmdQueryReceiverInfo(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_GET_MODEM_POWER_STATUS:
                    // 신규로 내가 추가함
                    CHC_Receiver.CHCGetCmdQueryModemPowerStatus(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_MODEM_POWERON:
                    // 신규로 내가 추가함
                    if (args instanceof GetCmdPowerModemEventArgs) {
                        Log.d(TAG, "post: isdial->"+ ((GetCmdPowerModemEventArgs)args).isDial());
                        Log.d(TAG, "post: 파워 1" + CHC_Receiver.CHCGetCmdPowerModem(mReceiverRef, ((GetCmdPowerModemEventArgs)args).isDial(), mCmdRef));
                    }
                    break;
                case RECEIVER_CMD_QUERY_NEW_DEVINFO:
                    //// the information of the Device
                    CHC_Receiver.CHCGetCmdQueryDeviceInfo(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_START_NONE_MAGNETIC_TILT:
                    //Start Magnetic Tilt
                    if (args instanceof GetCmdStartNoneMagneticTiltEventArgs) {
                        handleEventBus((GetCmdStartNoneMagneticTiltEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_STOP_NONE_MAGNETIC_TILT:
                    //Stop Magnetic Tilt
                    CHC_Receiver.CHCGetCmdStopNoneMagneticTilt(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_GNSS_POSDATA:
                    // set the position information output from the posdata
                    if (args instanceof GetCmdOutputPosDataEventArgs) {
                        handleEventBus((GetCmdOutputPosDataEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_GET_FILERECORD_STATUS:
                    // static information to begin with the static
                    CHC_Receiver
                            .CHCGetCmdQueryFileRecordStatus(
                                    mReceiverRef,
                                    CHC_FILE_RECORD_CHANNEL_ID.CHC_FILE_RECORD_CHANNEL_ID_1,
                                    mCmdRef);
                    break;

                case RECEIVER_CMD_GET_FILERECORD_PARAM:
                    // query the static params
                    CHC_Receiver
                            .CHCGetCmdQueryFileRecordParams(
                                    mReceiverRef,
                                    CHC_FILE_RECORD_CHANNEL_ID.CHC_FILE_RECORD_CHANNEL_ID_1,
                                    mCmdRef);
                    break;
                case RECEIVER_CMD_GET_BATTERYLIFE:
                    // get battery life from the receiver
                    CHC_Receiver.CHCGetCmdQueryBatteryLife(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_OTHER_IO_IDS_DISABLED:
                    // close all the other power,which we don not use for the rover/base
                    if (args instanceof GetCmdDisableOtherIOsEventArgs) {
                        handleEventBus((GetCmdDisableOtherIOsEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_GNSS_STARTROVER:
                    // start rover
                    if (args instanceof GetCmdStartRoverEventArgs) {
                        handleEventBus((GetCmdStartRoverEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_GET_GNSS_BASEPARAM:
                    // get params from where you set from the base
                    CHC_Receiver.CHCGetCmdQueryBaseParamsEx(mReceiverRef,
                            CHC_GNSS_IO_ID.CHC_GNSS_IO_ID_WIFI, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_GNSS_UNLOGALL:
                    // stop all the command output
                    CHC_Receiver.CHCGetCmdSetGNSSDataUnLogall(mReceiverRef,
                            CHC_GNSS_IO_ID.CHC_GNSS_IO_ID_BT, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_GPRS_INFO:
                    // set ntrip's ip and address
                    if (args instanceof GetCmdUpdateGprsInfoEventArgs) {
                        handleEventBus((GetCmdUpdateGprsInfoEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_CORS_INFO:
                    // set ntrip's username and password
                    if (args instanceof GetCmdUpdateCorsInfoEventArgs) {
                        handleEventBus((GetCmdUpdateCorsInfoEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_GET_MODEM_DIAL_PARAM:
                    // the mode params for the 3G
                    Log.d(TAG, "post: modeldial param 받기");
                    CHC_Receiver.CHCGetCmdQueryModemDialParams(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_MODEM_COMMUNICATION_MODE:
                    // change th internet to the 3G
                    if (args instanceof GetCmdUpdateModemCommunicationModeEventArgs) {
                        handleEventBus((GetCmdUpdateModemCommunicationModeEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_MODEM_DIAL_PARAM:
                    // set 3G Params
                    if (args instanceof GetCmdUpdateModemDialParamsEventArgs) {
                        handleEventBus((GetCmdUpdateModemDialParamsEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_GPRS_BREAK:
                    // close 3G
                    CHC_Receiver.CHCGetCmdBreakGPRS(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_GET_SOURCE_TABLE:
                    // get the source table from the server
                    Log.d(TAG, "post: 마운트 포인트 가져오기 cmd");
                    CHC_Receiver.CHCGetCmdQuerySourceTable(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_GPRS_LOGIN:
                    // log in Ntrip
                    if (ReceiverService.BUS.getModemDialStatus() == EnumModemDialStatus.MODEM_DIAL_STATUS_INIT) {
                        CHC_Receiver.CHCGetCmdUpdateModemAutoDial(mReceiverRef,
                                (short) 1, mCmdRef);
                        sendCmds(mCmdRef.getCmds());
                        mCmdRef.delete();
                        mCmdRef = new CHC_CMDRef();
                        CHC_Receiver.CHCGetCmdDialModem(mReceiverRef, (short) 1,
                                mCmdRef);
                        sendCmds(mCmdRef.getCmds());
                        mCmdRef.delete();
                        mCmdRef = new CHC_CMDRef();
                    }
                    CHC_Receiver.CHCGetCmdLoginGPRS(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_MODEM_DIALON:
                    // 3G Dial On/Off
                    if (args instanceof GetCmdDialModemEventArgs) {
                        handleEventBus((GetCmdDialModemEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_GET_MODEM_GSM_DIAL_STATUS:
                    // GSM dial status
                    CHC_Receiver.CHCGetCmdQueryCSDDialStatus(mReceiverRef, mCmdRef);
                    break;
                case RECEIVER_CMD_SET_GNSS_NMEADATA:
                    // set nmea output
                    if (args instanceof GetCmdOutputNMEAEventArgs) {
                        handleEventBus((GetCmdOutputNMEAEventArgs) args);
                    }
                    break;
                case RECEIVER_CMD_SET_REGCODE:
                    //register receiver
                    if (args instanceof GetCmdRegReceiverEventArgs) {
                        handleEventBus((GetCmdRegReceiverEventArgs) args);
                    }
                    break;
                default:
                    break;
            }
            sendCmds(mCmdRef.getCmds());
            mCmdRef.delete();
        }
    }

    private void handleEventBus(GetCmdStartNoneMagneticTiltEventArgs args) {
        NoneMagneticTiltStartInfo startInfo = args.getNoneMagneticTiltStartInfo();
        CHC_Receiver.CHCGetCmdStartNoneMagneticTiltEx(mReceiverRef, startInfo.getAntennaHeight(),
                ConversionDataStruct.covCHC_DATA_FREQUENCY(startInfo.getFrequency()),
                CHC_NONE_MAGNETIC_MEASURE_TYPE.CHC_NONE_MAGNETIC_MEASURE_SHAKE, mCmdRef);
    }

    /**
     * set the position information output as a frequence
     */
    private void handleEventBus(GetCmdOutputPosDataEventArgs args) {
        CHC_DATA_FREQUENCY frequency = ConversionDataStruct
                .covChcDataFrequency(args.getEnumDataFrequency());
        CHC_Receiver.CHCGetCmdOutputPosData(mReceiverRef, frequency, mCmdRef);
    }

    /***
     * stop all the controller we don not need for
     */
    public void handleEventBus(GetCmdDisableOtherIOsEventArgs args) {
        CHC_Receiver.CHCGetCmdDisableOtherIOs(mReceiverRef, args.getPort(),
                mCmdRef);
    }

    /***
     * start rover
     **/
    public void handleEventBus(GetCmdStartRoverEventArgs args) {
        CHC_RoverParams params = ConversionDataStruct.covChcRoverParams(args
                .getRoverParams());
        CHC_Receiver.CHCGetCmdStartRover(mReceiverRef, params, mCmdRef);
    }

    /***
     * set ntrip params for the ip and address
     **/
    public void handleEventBus(GetCmdUpdateGprsInfoEventArgs args) {
        CHC_GPRSInfo info = ConversionDataStruct.covChcGrpsInfo(args.getInfo());
        int iReturn = CHC_Receiver.CHCGetCmdUpdateGPRSInfo(mReceiverRef, info,
                mCmdRef);
        if (iReturn == CHC_ReceiverConstants.CHC_ERROR_FUNCTION) {
            // not support
        }
    }

    /***
     * set ntrip username and password
     **/
    public void handleEventBus(GetCmdUpdateCorsInfoEventArgs args) {
        CHC_CORSInfo info = ConversionDataStruct.covChcCorsInfo(args.getInfo());
        CHC_Receiver.CHCGetCmdUpdateCORSInfo(mReceiverRef, info, mCmdRef);
    }

    /***
     * set 3G/GSM
     **/
    public void handleEventBus(GetCmdUpdateModemCommunicationModeEventArgs args) {
        CHC_MODEM_COMMUNICATION_MODE mode = ConversionDataStruct
                .covCHC_MODEM_COMMUNICATION_MODE(args.getMode());
        CHC_Receiver.CHCGetCmdUpdateModemCommunicationMode(mReceiverRef, mode,
                mCmdRef);
    }

    /***
     * set 3g params to dial
     **/
    public void handleEventBus(GetCmdUpdateModemDialParamsEventArgs args) {
        CHC_ModemDialParams params = ConversionDataStruct
                .covCHC_ModemDialParams(args.getModemDialParams());
        CHC_Receiver.CHCGetCmdUpdateModemDialParams(mReceiverRef, params,
                mCmdRef);
    }

    /***
     * 3g dial
     **/
    public void handleEventBus(GetCmdDialModemEventArgs args) {
        short dial = ConversionDataStruct.covShort(args.isDial());
        CHC_Receiver.CHCGetCmdDialModem(mReceiverRef, dial, mCmdRef);
    }

    // set the nmea you wann't the receiver output with a frequence
    public void handleEventBus(GetCmdOutputNMEAEventArgs args) {
        CHC_NMEAData[] data = new CHC_NMEAData[args.getParam().getData().length];
        for (int i = 0; i < args.getParam().getData().length; i++) {
            CHC_NMEAData d = ConversionDataStruct.covCHC_NMEAData(args
                    .getParam().getData()[i]);
            data[i] = d;
        }
        long methods = args.getParam().getMethods();
        short save = ConversionDataStruct.covShort(args.getParam().isSave());
        CHC_Receiver.CHCGetCmdOutputNMEA(mReceiverRef, data, methods, save,
                mCmdRef);
    }

    //register the receiver
    public void handleEventBus(GetCmdRegReceiverEventArgs args) {
        RegisterCode code = args.getCode();
        CHC_Receiver.CHCGetCmdRegReceiver(mReceiverRef, code.getCodeOne(), code.getCodeTwo(), code.getCodeThree(), mCmdRef);
    }

    /**
     * send more than one commands to the receiver
     */
    public static void sendCmds(CHC_CMD[] cmds) {
        if (cmds == null) {
            return;
        }
        for (CHC_CMD cmd : cmds) {
            if (cmd.getCmd().length > 1) {// CMD length default :1
                // send to Bluetooth、wifi\port，then send to receiver。
                sendCmd(cmd);
            }
        }
    }

    /**
     * send one command with a sleep time to the receiver
     */
    public static void sendCmd(CHC_CMD chcCmd) {
        Cmd cmd = ConversionDataStruct.covCmd(chcCmd);
        sendCmd(cmd);
    }

    /**
     * only send one command to the receiver
     */
    public static void sendCmd(Cmd cmd) {
        CmdManager.getInstance().sendCmd(cmd);
    }
}
