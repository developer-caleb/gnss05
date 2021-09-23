package kr.loplab.gnss05.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.huace.gnssserver.gnss.data.receiver.Course
import com.huace.gnssserver.gnss.data.receiver.DopsInfo
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd
import com.huace.gnssserver.gnss.data.receiver.PositionInfo
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.PositionInformationActivity
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.measurement.TopoMeasurementActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationSettingSatelliteActivity
import kr.loplab.gnss05.activities.viewmodel.ExportViewModel
import kr.loplab.gnss05.activities.viewmodel.StatusWorkViewModel
import kr.loplab.gnss05.connection.ConnectManager
import kr.loplab.gnss05.databinding.ActivitySettingBinding
import kr.loplab.gnss05.databinding.ActivityStatusWorkBinding
import kr.loplab.gnss05.enums.SurveyType
import kr.loplab.gnss05.receiver.ReceiverService
import kr.loplab.gnss05.receiver.entity.ReceiverAsw
import java.text.SimpleDateFormat
import java.util.*

class StatusWorkActivity : ActivityBase<ActivityStatusWorkBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_status_work
    lateinit var viewModel1: StatusWorkViewModel
    private val mReceiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStop() {
        try {
            unregisterReceiver(mReceiver)
        } catch (e: Exception){
            Log.e(TAG, "onStop:unregisterReceiver $e", )
        }


        super.onStop()
    }
    override fun init() {
        viewModel1 = ViewModelProvider(this).get(StatusWorkViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.btBack.setOnClickListener {
            onBackPressed()
        }

        viewBinding.btMobilestation.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, MobileStationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btPositionInformation.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, PositionInformationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btBatteryStatus.setOnClickListener {
           val alertDialog = AlertDialog.Builder(this)
               .setTitle("배터리 전원")
               .setMessage("A현재 배터리 용량:90%\nB현재 배터리 용량:80%")
               .setPositiveButton("확인"){dialog, which -> {
                   //클릭하면 뭐할지..
               }}
               .create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        }
        viewBinding.btSurveyMode.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, true);
        }
        viewBinding.backgroundDemomode.setOnClickListener{
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btTopoSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.TOPO)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btFastSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.FAST)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btAutoSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.AUTO)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btMeasurement.setOnClickListener {
            when(viewModel1.surveyType.value){
                SurveyType.FAST->{}
                SurveyType.AUTO->{}
                else->{
                    Log.d(TAG, "initListener: GO TOPO ACTIVITY")
                    intent = Intent(this, TopoMeasurementActivity::class.java)
                    startActivity(intent);
                }
            }
        }
        viewBinding.btToolbarOpen.setOnClickListener { viewModel1.setBoolvalue(viewModel1.toolbarOpen, !viewModel1.toolbarOpen.value!!); }
     /*   viewBinding.btToolbar0.setOnClickListener {  }
        viewBinding.btToolbar1.setOnClickListener {  }
        viewBinding.btToolbar2.setOnClickListener {  }
        viewBinding.btToolbar3.setOnClickListener {  }
        viewBinding.btToolbar4.setOnClickListener {  }
        viewBinding.btToolbar5.setOnClickListener {  }
        viewBinding.btToolbar6.setOnClickListener {  }
        viewBinding.btToolbar7.setOnClickListener {  }
        viewBinding.btToolbar8.setOnClickListener {  }*/
        viewBinding.btPointSave.setOnClickListener {  }
        viewBinding.btSettings.setOnClickListener {  }
        viewBinding.layoutFastSurveyName.setOnClickListener {
            requestETfocus(viewBinding.etFastSurveyName)
        }
        viewBinding.layoutFastSurveyCode.setOnClickListener {
            requestETfocus(viewBinding.etFastSurveyCode)
        }
    }

    override fun initDatabinding() {
    }
    override fun onResume() {
        super.onResume()
        when (ConnectManager.instance?.isConnected)
        {
            true ->   { Log.d(TAG, "onResume: connected")
            }
            false ->  Log.d(TAG, "onResume: unconnected")
        }
        //안드로이드 브로드캐스트 리시버에 filter를 등록함, 해당 리시버가 들어올 경우에는, filtering 해서 데이터를 받을 수 있게 됨.
        //그러면 반대로 누군가는 계속 브로드캐스트를 등록한다는 말임. 그 등록하는 애를 찾아야함.
        val cmds: MutableList<EnumReceiverCmd> = ArrayList()
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA)
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA)


        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, ReceiverService.createReceiverAswIntentFilter(cmds)
        )
    }
    private inner class MyReceiver : BroadcastReceiver() {
        val TAG : String = this.javaClass.simpleName
        override fun onReceive(context: Context, intent: Intent) {
            //브로드캐스트를 받았을 경우
            //Intent 안에 정보를 다 넣은 것 같음.
            //브로드캐스트가 아니라 백그라운드로 우리가 원하는 정보를 계속 받아오는게 최종적으로 중요할 것 같다.
            Log.d(TAG, "onReceive: ")
            val action = intent.action
            when (action){
                EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA.name ->{
                    Log.d(TAG, "onReceive: 2")
                    val asw: ReceiverAsw? = ReceiverService.getBroadcastData(intent)
                    if (asw== null){ Log.d(TAG, "onReceive: null"); return}
                    runOnUiThread {
                        Log.d(TAG, "onReceive: 3")

                        if ( asw.receiverCmdType ==   EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA && (asw.getParcelable() is PositionInfo) ) {
                            val p = asw.getParcelable() as PositionInfo
                            if (p != null && p.satellitePosition != null && p.satellitePosition
                                    .position != null) {

                                viewModel1.setStringvalue(viewModel1.x, p.satellitePosition.position.x.toString() )
                                viewModel1.setStringvalue(viewModel1.y, p.satellitePosition.position.y.toString() )
                                viewModel1.setStringvalue(viewModel1.z, p.satellitePosition.position.z.toString() )
                                viewModel1.setStringvalue(viewModel1.horizontalError, p.satellitePrecision.hpre.toString() )
                                viewModel1.setStringvalue(viewModel1.verticalError, p.satellitePrecision.vpre.toString() )
                            }
                        }else { Log.d(TAG, "onReceive: 4") }

                    }
                }
                EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA.name -> {
                    Log.d(TAG, "onReceive: 7")
                    val asw: ReceiverAsw? = ReceiverService.getBroadcastData(intent)
                    if (asw== null){
                        Log.d(TAG, "onReceive: null"); return}else{
                        Log.d(TAG, "onReceive: not null")}
                    runOnUiThread {
                        Log.d(TAG, "onReceive: 8")
                        when (asw.receiverCmdType) {
                            EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA -> if (asw.getParcelable() is DopsInfo) {
                                val p = asw.getParcelable() as DopsInfo
                                if (p != null ) {
                                    viewModel1.setStringvalue(viewModel1.pdop, p.pdop.toString() )
                                    viewModel1.setStringvalue(viewModel1.hdop, p.hdop.toString() )
                                    viewModel1.setStringvalue(viewModel1.vdop, p.vdop.toString() )
                                }
                            }
                            else -> {
                                Log.d(TAG, "onReceive: 4")
                            }
                        }
                    }
                }
            }

        }
    }
}