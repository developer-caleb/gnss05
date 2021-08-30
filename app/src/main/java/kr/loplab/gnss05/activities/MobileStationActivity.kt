package kr.loplab.gnss05.activities

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.cors_servermanager.CORSServerManagerActivity
import kr.loplab.gnss05.activities.viewmodel.MobileStationViewModel
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.activities.workmanager.WorkManagerActivity
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityMobileStationBinding
import java.lang.Exception

class MobileStationActivity : ActivityBase<ActivityMobileStationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_mobile_station
    lateinit var viewModel1: MobileStationViewModel
    var apnsPwView = false;
    var corsPwView = false;
    
    override fun init() {
        viewModel1 = ViewModelProvider(this).get(MobileStationViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.saveAndApplyBt.setOnClickListener {
            savesettings()
            Log.d(TAG, "initListener: saveAndApplyBt clicked")
        }
        viewBinding.applyBt.setOnClickListener {
            Log.d(TAG, "initListener: applyBt clicked")
        }
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.layoutConnectSave.setOnClickListener {  }
        viewBinding.corsSettingBt.setOnClickListener {
            intent = Intent(this, CORSServerManagerActivity::class.java)
            ActivityCompat.startActivityForResult(this, intent, REQUEST_CORS_SERVER_MANAGER, null)
        }
        viewBinding.btOpenWorkManager.setOnClickListener {
            intent = Intent(this, WorkManagerActivity::class.java)
            ActivityCompat.startActivityForResult(this, intent, REQUEST_WORKMANAGER, null)
        }

        viewBinding.layoutCutAngle.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.CUT_ANGLE_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvCutAngle.text.toString()
            dlg.selectedposition= alist.indexOf(viewModel1.cutAngleNum.value.toString())
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.cutAngleNum.value = alist[i].toInt()
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener { str ->
                viewModel1.cutAngleNum.value = str.toInt()
            }
            dlg.setHeader("컷 각도")
        }
        viewBinding.layoutRawDataSave.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.bool_rawdatasave,!viewModel1.bool_rawdatasave.value!!)
        }

        viewBinding.layoutCollectionInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.COLLECTION_INTERVAL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.collectionIntervalNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.collectionIntervalNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("수집간격")
        }

        viewBinding.layoutDataConnectionType.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.MOBILESTATION_DATA_CONNECTION_TYPE_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.data_connection_type.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.data_connection_type.value = i
                viewBinding.tvDataConnectionType.text = alist[i]
                dlg.dismiss()
            }
            dlg.setHeader("데이터 연결방식")
        }
        viewBinding.layoutInnerRadioChannel.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_CHANNEL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.innerRadioChannelNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.innerRadioChannelNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("채널")
        }
        viewBinding.layoutRadioModeChannel.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_CHANNEL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.radioModeChannelNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.radioModeChannelNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("채널")
        }
        viewBinding.layoutInnerRadioProtocol.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_PROTOCOL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.innerRadioProtocolNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.innerRadioProtocolNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("프로토콜")
        }
        viewBinding.layoutRadioModeProtocol.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_PROTOCOL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.radioModeProtocolNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.radioModeProtocolNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("프로토콜")
        }

        viewBinding.layoutInnerRadioInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_INTERVAL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.innerRadioIntervalNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.innerRadioIntervalNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("간격")
        }
        viewBinding.layoutInnerRadioFec.setOnClickListener {
            viewBinding.swInnerRadioFec.isChecked = !viewBinding.swInnerRadioFec.isChecked
        }

        viewBinding.layoutInnerRadioPower.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_POWER_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.innerRadioPowerNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.innerRadioPowerNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("전원")
        }
        viewBinding.layoutRadioModePower.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.INNER_RADIO_POWER_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.radioModePowerNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.radioModePowerNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("전원")
        }
        viewBinding.layoutGgaUploadInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.GGA_UPLOAD_INTERVAL_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvGgaUploadInterval.text.toString()
            dlg.selectedposition= viewModel1.ggaUploadIntervalNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.ggaUploadIntervalNum.value = alist[i].toInt()
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener { str ->
                viewModel1.ggaUploadIntervalNum.value = str.toInt()
            }
            dlg.setHeader("GGA업로드간격(s)")
        }
        viewBinding.layoutOuterRadioCommunicationSpeed.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.COMMUNICATION_SPEED_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvGgaUploadInterval.text.toString()
            dlg.selectedposition= alist.indexOf(viewModel1.outerRadioCommunicationSpeedNum.value.toString()!!)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.outerRadioCommunicationSpeedNum.value = alist[i].toInt()
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener { str ->
                viewModel1.outerRadioCommunicationSpeedNum.value = str.toInt()
            }
            dlg.setHeader("통신 속도")
        }
        viewBinding.layoutNetworkAutoConnect.setOnClickListener {
            viewBinding.swNetworkAutoConnect.isChecked = !viewBinding.swNetworkAutoConnect.isChecked
        } //12

        viewBinding.layoutNetworkSystem.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.NETWORK_SYSTEM_List
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.networkSystemNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.networkSystemNum.value = i
                //viewBinding.tvNetworkSystem.text = alist[viewModel1.networkSystemNum.value!!]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("네트워크 시스템")
        }
        viewBinding.layoutNetworkTransfer.setOnClickListener {
            viewBinding.swNetworkTransfer.isChecked = !viewBinding.swNetworkTransfer.isChecked
        } //12
        viewBinding.layoutAutoApn.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.auto_apn, !viewModel1.auto_apn.value!!)
        }

        viewBinding.layoutApnWorker.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = ArrayList<String>();
            viewModel1.apn_list.value!!.forEach { worker -> alist.add(worker.worker)  }
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.apnIndex.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                ApnSettings(i)
                dlg.dismiss()
            }
            dlg.setHeader("작업자")
        }

        viewBinding.apnPwEye.setOnClickListener {
            Log.d(TAG, "initListener: passwordview clicked")
            apnsPwView = !apnsPwView;
            viewBinding.tvApnPw.transformationMethod =
                if (apnsPwView) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
            if (apnsPwView)  viewBinding.apnPwEye.setImageResource(R.drawable.ic_eye_yes) else viewBinding.apnPwEye.setImageResource(R.drawable.ic_eye_no)
        }
    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.cutAngleNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_CUT_ANGLE, 1))
        viewModel1.setIntvalue(viewModel1.collectionIntervalNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_COLLECTION_INTERVAL))  //2
        viewModel1.setIntvalue(viewModel1.data_connection_type, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_DATA_CONNECTION_TYPE))  //2
        viewModel1.setBoolvalue(viewModel1.bool_rawdatasave, PrefUtil.getBoolean(applicationContext, Define.MOBILE_STATION_RAW_DATA_SAVE))  //2
        viewModel1.setIntvalue(viewModel1.innerRadioChannelNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_CHANNEL))  //7
        viewModel1.setIntvalue(viewModel1.radioModeChannelNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_RADIO_MODE_CHANNEL))  //7

        viewModel1.setIntvalue(viewModel1.innerRadioProtocolNum, PrefUtil.getInt2(this, MOBILE_STATION_INNER_RADIO_PROTOCOL)) //5
        viewModel1.setIntvalue(viewModel1.radioModeProtocolNum, PrefUtil.getInt2(this, MOBILE_STATION_RADIO_MODE_PROTOCOL)) //5

        viewModel1.setIntvalue(viewModel1.innerRadioIntervalNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_INTERVAL))  //8
        viewBinding.swInnerRadioFec.isChecked =PrefUtil.getBoolean(applicationContext, MOBILE_STATION_INNER_RADIO_FEC) //13
        viewModel1.setIntvalue(viewModel1.innerRadioPowerNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_POWER))  //14
        viewModel1.setIntvalue(viewModel1.radioModePowerNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_RADIO_MODE_POWER))  //14

        viewModel1.setIntvalue(viewModel1.outerRadioCommunicationSpeedNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_OUTERRADIOCOMMUNICATION_SPEED, 9600))  //14
        viewModel1.setIntvalue(viewModel1.ggaUploadIntervalNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_GGA_UPLOAD_INTERVAL, 1))  //8
        viewBinding.swNetworkAutoConnect.isChecked =PrefUtil.getBoolean(applicationContext, MOBILE_STATION_NETWORK_AUTO_CONNECT) //12
        viewBinding.swNetworkTransfer.isChecked =PrefUtil.getBoolean(applicationContext, MOBILE_STATION_NETWORK_TRANSFER) //12
        viewModel1.setIntvalue(viewModel1.networkSystemNum, PrefUtil.getInt2(applicationContext, MOBILE_STATION_NETWORK_SYSTEM))  //6
        viewModel1.setBoolvalue(viewModel1.auto_apn, PrefUtil.getBoolean(this, MOBILE_STATION_AUTO_APN)) //10 -> data, viewbinding통합


        dbsetting()
        ApnSettings(viewModel1.apnIndex.value!!)
        CorsSettings(viewModel1.corsIndex.value!!)
    }

    fun savesettings(){
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_CUT_ANGLE, viewModel1.cutAngleNum.value!!)
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_COLLECTION_INTERVAL, viewModel1.collectionIntervalNum.value!!) //2
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_DATA_CONNECTION_TYPE, viewModel1.data_connection_type.value!!) //2
        PrefUtil.setBoolean(applicationContext, Define.MOBILE_STATION_RAW_DATA_SAVE, viewModel1.bool_rawdatasave.value!!) //9
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_CHANNEL, viewModel1.innerRadioChannelNum.value!!) //7
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_RADIO_MODE_CHANNEL, viewModel1.radioModeChannelNum.value!!) //7

        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_PROTOCOL, viewModel1.innerRadioProtocolNum.value!!) //5
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_RADIO_MODE_PROTOCOL, viewModel1.radioModeProtocolNum.value!!) //5
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_INTERVAL, viewModel1.innerRadioIntervalNum.value!!) //8
        PrefUtil.setBoolean(applicationContext, MOBILE_STATION_INNER_RADIO_FEC, viewBinding.swInnerRadioFec.isChecked) //13
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_POWER, viewModel1.innerRadioPowerNum.value!!) //14
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_RADIO_MODE_POWER, viewModel1.radioModePowerNum.value!!) //14

        PrefUtil.setInt(applicationContext, MOBILE_STATION_OUTERRADIOCOMMUNICATION_SPEED, viewModel1.outerRadioCommunicationSpeedNum.value!!) //??
        PrefUtil.setInt(applicationContext, MOBILE_STATION_GGA_UPLOAD_INTERVAL, viewModel1.ggaUploadIntervalNum.value!!) //??
        PrefUtil.setBoolean(applicationContext, MOBILE_STATION_NETWORK_AUTO_CONNECT, viewBinding.swNetworkAutoConnect.isChecked) //12
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_NETWORK_SYSTEM, viewModel1.networkSystemNum.value!!) //6
        PrefUtil.setBoolean(applicationContext, MOBILE_STATION_NETWORK_TRANSFER, viewBinding.swNetworkTransfer.isChecked) //12
        PrefUtil.setBoolean(applicationContext, MOBILE_STATION_AUTO_APN, viewModel1.auto_apn.value!!) //10

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: , requestCode : $requestCode, resultCode: $resultCode")
        dbsetting()
        if(requestCode == REQUEST_WORKMANAGER)
        {
            Log.d(TAG, "onActivityResult: REQUEST_WORKMANAGER")
            when(resultCode)
            {
                RESULT_OK -> ApnSettings(data!!.getIntExtra(APNS_SELECTED_INDEX, 0))
                RESULT_CANCELED -> try {
                    ApnSettings(viewModel1.apnIndex.value!!)
                } catch (e: Exception){
                    Log.e(TAG, "onActivityResult: ", e)
                    ApnSettings(0)
                }
            }
        }

        if( requestCode == REQUEST_CORS_SERVER_MANAGER)
        {
            Log.d(TAG, "onActivityResult: REQUEST_CORS_SERVER_MANAGER")

            when(resultCode)
            {
                RESULT_OK ->CorsSettings(data!!.getIntExtra(CORS_SELECTED_INDEX, 0))
                RESULT_CANCELED -> try {
                    CorsSettings(viewModel1.corsIndex.value!!)
                } catch (e: Exception){
                    Log.e(TAG, "onActivityResult: ", e)
                    CorsSettings(0)
                } }
        }
    }

    fun dbsetting() {

        var dbApn = Room.databaseBuilder(this, AppDatabase::class.java, WORKERS_DB)
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용 -> viewmodel에서 mainthread로 넣어줘야해서,
            .fallbackToDestructiveMigration()
            .build()
        viewModel1.apn_list.value = ArrayList(dbApn.workerDao().all)
        Log.d(TAG, "onResume: ${viewModel1.apn_list}")

        var dbCors =  Room.databaseBuilder(this, AppDatabase::class.java, Define.SERVERS_DB)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용 -> viewmodel에서 mainthread로 넣어줘야해서,
            .build()
        viewModel1.cors_list.value = ArrayList(dbCors.serverDao().all)
        Log.d(TAG, "onResume: ${viewModel1.cors_list}")

    }

    fun ApnSettings(  idx : Int){
        if(viewModel1.apn_list.value!!.size <= 0  ) return
        Log.d(TAG, "ApnSettings: $idx")
        viewModel1.apnIndex.value = idx

        viewBinding.tvApnWorker.text = viewModel1.apn_list.value!![idx].worker
        viewBinding.tvApnName.text = viewModel1.apn_list.value!![idx].name
        viewBinding.tvApnUser.text = viewModel1.apn_list.value!![idx].user
        viewBinding.tvApnPw.text = viewModel1.apn_list.value!![idx].password
    }

    fun CorsSettings(  idx : Int){
        if(viewModel1.cors_list.value!!.size <= 0  ) return
        Log.d(TAG, "CorsSettings: $idx")
        viewModel1.corsIndex.value = idx

        /*viewBinding.tvCorsName.text = viewModel1.cors_list.value!![idx].name
        viewBinding.tvCorsIp.text = viewModel1.cors_list.value!![idx].ip
        viewBinding.tvCorsPort.text = viewModel1.cors_list.value!![idx].port
        //기준국 연결 포인트->
        viewBinding.tvCorsPw.text = viewModel1.cors_list.value!![idx].password*/
    }
}