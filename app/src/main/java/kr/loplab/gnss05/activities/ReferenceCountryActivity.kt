package kr.loplab.gnss05.activities

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.cors_servermanager.CORSServerManagerActivity
import kr.loplab.gnss05.activities.viewmodel.ReferenceCountryViewModel
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.activities.workmanager.WorkManagerActivity
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.COLLECTION_INTERVAL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.COMMUNICATION_SPEED_LIST
import kr.loplab.gnss05.common.OptionList.Companion.DATA_CONNECTION_TYPE_List
import kr.loplab.gnss05.common.OptionList.Companion.DEPLACEMENT_MODE_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_CHANNEL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_INTERVAL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_POWER_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_PROTOCOL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.NETWORK_MODE_List
import kr.loplab.gnss05.common.OptionList.Companion.NETWORK_SYSTEM_List
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding
import java.lang.Exception

class ReferenceCountryActivity : ActivityBase<ActivityReferenceCountryBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_country
    lateinit var viewModel1:ReferenceCountryViewModel
    var wifiPasswordView = false;
    var apnsPwView = false;
    var corsPwView = false;

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(ReferenceCountryViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }
    override fun initListener() {
        viewBinding.layoutConnectSave.setOnClickListener {  }
        viewBinding.settingSatelliteBt.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, SettingSatelliteActivity::class.java)
            startActivity(intent);
        }
        viewBinding.saveAndApplyBt.setOnClickListener {
            savesettings()
            Log.d(TAG, "initListener: saveAndApplyBt clicked")
        }
        viewBinding.applyBt.setOnClickListener {
            Log.d(TAG, "initListener: applyBt clicked")
        }
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btOpenWorkManager.setOnClickListener {
            intent = Intent(this, WorkManagerActivity::class.java)
            ActivityCompat.startActivityForResult(this, intent, REQUEST_WORKMANAGER, null)
        }
        viewBinding.corsSettingBt.setOnClickListener {
            intent = Intent(this, CORSServerManagerActivity::class.java)
            ActivityCompat.startActivityForResult(this, intent, REQUEST_CORS_SERVER_MANAGER, null)
        }
        viewBinding.referenceCountryIdLayout.setOnClickListener {
            viewBinding.referenceCountryId.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.referenceCountryId,0)
        }
        viewBinding.deleteId.setOnClickListener {
            viewBinding.referenceCountryId.setText("")
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(viewBinding.referenceCountryId.windowToken,0)
        }
        viewBinding.startModeLayout.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            dlg.list = OptionList.START_MODE_LIST
            dlg.selectedposition= viewModel1.startModeNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.startModeNum.value = i
                dlg.dismiss()
                if(i==1){
                    intent = Intent(this, ReferenceCoordinateSetting::class.java)
                    startActivity(intent);
                } }
            dlg.setHeader("시작 모드")
        }
       viewBinding.layoutDeplacementMode.setOnClickListener {
           val dlg = MyDialog(this)
           var alist = DEPLACEMENT_MODE_LIST
           dlg.firstLayoutUse = false
           dlg.list = alist
           dlg.selectedposition= viewModel1.deplaceModeNum.value!!
           dlg.start("")
           dlg.setOnListClickedListener { view, i ->
               viewModel1.deplaceModeNum.value = i
               dlg.dismiss()
           }
           dlg.setHeader("변위 모드")
       }
        viewBinding.layoutCollectionInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = COLLECTION_INTERVAL_LIST
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
            var alist = DATA_CONNECTION_TYPE_List
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.data_connect_type.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setDataConnectionType(i)
                viewBinding.tvDataConnectionType.text = alist[i]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("데이터 연결방식")
        }
        viewBinding.layoutNetworkSystem.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = NETWORK_SYSTEM_List
            var prefvalue = Define.NETWORK_SYSTEM
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
        viewBinding.layoutReferenceCountryAutoplay.setOnClickListener {
            viewBinding.swReferenceCountryAutoplay.isChecked = !viewBinding.swReferenceCountryAutoplay.isChecked
        } //11
        viewBinding.layoutRawDataSave.setOnClickListener {
            var bool = !viewModel1.bool_rawdatasave.value!!
            viewModel1.setRawDatavalue(bool)

        }
        viewBinding.layoutCurrentPointName.setOnClickListener {
            Log.d(TAG, "initListener: layoutCurrentPointName Clicked!")
            viewBinding.etCurrentPointName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etCurrentPointName,0)
        }
        viewBinding.layoutNetworkAutoConnect.setOnClickListener {
            viewBinding.swNetworkAutoConnect.isChecked = !viewBinding.swNetworkAutoConnect.isChecked
        } //12
        viewBinding.layoutInnerRadioFec.setOnClickListener {
            viewBinding.swInnerRadioFec.isChecked = !viewBinding.swInnerRadioFec.isChecked
        } //13
        viewBinding.layoutNetworkMode.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = NETWORK_MODE_List
            var prefvalue = Define.NETWORK_MODE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.network_mode.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setNetworkMode(i)
                viewBinding.tvNetworkMode.text = alist[i]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("네트워크 모드")
        }

        viewBinding.layoutInnerRadioProtocol.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = INNER_RADIO_PROTOCOL_LIST
            var prefvalue = Define.INNER_RADIO_PROTOCOL
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.innerRadioProtocolNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setInnerRadioProtocol(i)
                viewBinding.tvInnerRadioProtocol.text = alist[i]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("프로토콜")
        }

        viewBinding.layoutInnerRadioChannel.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = INNER_RADIO_CHANNEL_LIST
            var prefvalue = Define.INNER_RADIO_CHANNEL
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

        viewBinding.layoutInnerRadioInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = INNER_RADIO_INTERVAL_LIST
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
        viewBinding.layoutInnerRadioPower.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = INNER_RADIO_POWER_LIST
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

        viewBinding.layoutOuterRadioCommunicationSpeed.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = COMMUNICATION_SPEED_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvOuterRadioCommunicationSpeed.text.toString()
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

        viewBinding.layoutAutoApn.setOnClickListener {
           viewModel1.setAutoApn(!viewModel1.auto_apn.value!!)
        }
        viewBinding.layoutWifiPassword.setOnClickListener {
            viewBinding.etWifiPassword.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput( viewBinding.etWifiPassword,0)
        }
        viewBinding.btWifiPasswordView.setOnClickListener {
            Log.d(TAG, "initListener: passwordview clicked")
            wifiPasswordView = !wifiPasswordView;
            viewBinding.etWifiPassword.transformationMethod =
                if (wifiPasswordView) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
            if (wifiPasswordView)  viewBinding.btWifiPasswordView.setImageResource(R.drawable.ic_eye_yes) else viewBinding.btWifiPasswordView.setImageResource(R.drawable.ic_eye_no)
        }
        viewBinding.apnPwEye.setOnClickListener {
            Log.d(TAG, "initListener: passwordview clicked")
            apnsPwView = !apnsPwView;
            viewBinding.tvApnPw.transformationMethod =
                if (apnsPwView) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
            if (apnsPwView)  viewBinding.apnPwEye.setImageResource(R.drawable.ic_eye_yes) else viewBinding.apnPwEye.setImageResource(R.drawable.ic_eye_no)
        }
        viewBinding.corsPwEye.setOnClickListener {
            Log.d(TAG, "initListener: passwordview clicked")
            corsPwView = !corsPwView;
            viewBinding.tvCorsPw.transformationMethod =
                if (corsPwView) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
            if (corsPwView)  viewBinding.corsPwEye.setImageResource(R.drawable.ic_eye_yes) else viewBinding.corsPwEye.setImageResource(R.drawable.ic_eye_no)
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
        viewBinding.layoutCorsName.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = ArrayList<String>();
            viewModel1.cors_list.value!!.forEach { server -> alist.add(server.name)  }
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.corsIndex.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                CorsSettings(i)
                dlg.dismiss()
            }
            dlg.setHeader("이름")
        }
    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.startModeNum, PrefUtil.getInt2(applicationContext, Define.START_MODE))  //0
        viewModel1.setIntvalue(viewModel1.deplaceModeNum, PrefUtil.getInt2(applicationContext, Define.DEPLACEMENT_MODE))  //1
        viewModel1.setIntvalue(viewModel1.collectionIntervalNum, PrefUtil.getInt2(applicationContext, Define.COLLECTION_INTERVAL))  //2
        viewModel1.setIntvalue(viewModel1.innerRadioChannelNum, PrefUtil.getInt2(applicationContext, Define.INNER_RADIO_CHANNEL))  //7
        viewModel1.setIntvalue(viewModel1.innerRadioIntervalNum, PrefUtil.getInt2(applicationContext, Define.INNER_RADIO_INTERVAL))  //8
        viewModel1.setIntvalue(viewModel1.innerRadioPowerNum, PrefUtil.getInt2(applicationContext, Define.INNER_RADIO_POWER))  //14
        viewModel1.setIntvalue(viewModel1.outerRadioCommunicationSpeedNum, PrefUtil.getInt2(applicationContext, Define.OUTERRADIOCOMMUNICATION_SPEED, 9600))  //14
        viewModel1.setIntvalue(viewModel1.apnIndex, PrefUtil.getInt2(applicationContext, Define.APN_INDEX_NUM))  //14
        viewModel1.setIntvalue(viewModel1.corsIndex, PrefUtil.getInt2(applicationContext, Define.CORS_INDEX_NUM))  //14

        viewModel1.setDataConnectionType(PrefUtil.getInt2(this, DATA_CONNECTION_TYPE)) //3
        viewModel1.setNetworkMode(PrefUtil.getInt2(this, NETWORK_MODE)) //4
        viewModel1.setInnerRadioProtocol(PrefUtil.getInt2(this, INNER_RADIO_PROTOCOL)) //5
        viewModel1.setNetworkSystemNum(PrefUtil.getInt2(applicationContext, NETWORK_SYSTEM))  //6
        viewModel1.setRawDatavalue(PrefUtil.getBoolean(this, RAW_DATA_SAVE)) //9 -> data, Viewbinding통합
        viewModel1.setAutoApn(PrefUtil.getBoolean(this, AUTO_APN)) //10 -> data, viewbinding통합

        viewBinding.swReferenceCountryAutoplay.isChecked =PrefUtil.getBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY) //11
        viewBinding.swNetworkAutoConnect.isChecked =PrefUtil.getBoolean(applicationContext, NETWORK_AUTO_CONNECT) //12
        viewBinding.swInnerRadioFec.isChecked =PrefUtil.getBoolean(applicationContext, INNER_RADIO_FEC) //13
        dbsetting()
        ApnSettings(viewModel1.apnIndex.value!!)
        CorsSettings(viewModel1.corsIndex.value!!)

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

        viewBinding.tvCorsName.text = viewModel1.cors_list.value!![idx].name
        viewBinding.tvCorsIp.text = viewModel1.cors_list.value!![idx].ip
        viewBinding.tvCorsPort.text = viewModel1.cors_list.value!![idx].port
        //기준국 연결 포인트->
        viewBinding.tvCorsPw.text = viewModel1.cors_list.value!![idx].password
    }

    fun savesettings(){
        PrefUtil.setInt(applicationContext, Define.START_MODE, viewModel1.startModeNum.value!!) //0
        PrefUtil.setInt(applicationContext, Define.DEPLACEMENT_MODE, viewModel1.deplaceModeNum.value!!) //1
        PrefUtil.setInt(applicationContext, Define.COLLECTION_INTERVAL, viewModel1.collectionIntervalNum.value!!) //2
        PrefUtil.setInt(applicationContext, Define.DATA_CONNECTION_TYPE, viewModel1.data_connect_type.value!!) //3
        PrefUtil.setInt(applicationContext, Define.NETWORK_MODE, viewModel1.network_mode.value!!) //4
        PrefUtil.setInt(applicationContext, Define.INNER_RADIO_PROTOCOL, viewModel1.innerRadioProtocolNum.value!!) //5
        PrefUtil.setInt(applicationContext, Define.NETWORK_SYSTEM, viewModel1.networkSystemNum.value!!) //6
        PrefUtil.setInt(applicationContext, Define.INNER_RADIO_CHANNEL, viewModel1.innerRadioChannelNum.value!!) //7
        PrefUtil.setInt(applicationContext, Define.INNER_RADIO_INTERVAL, viewModel1.innerRadioIntervalNum.value!!) //8
        PrefUtil.setInt(applicationContext, Define.INNER_RADIO_POWER, viewModel1.innerRadioPowerNum.value!!) //14
        PrefUtil.setInt(applicationContext, APN_INDEX_NUM, viewModel1.apnIndex.value!!) //??
        PrefUtil.setInt(applicationContext, CORS_INDEX_NUM, viewModel1.corsIndex.value!!) //??
        PrefUtil.setInt(applicationContext, OUTERRADIOCOMMUNICATION_SPEED, viewModel1.outerRadioCommunicationSpeedNum.value!!) //??

        PrefUtil.setBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY, viewBinding.swReferenceCountryAutoplay.isChecked) //11
        PrefUtil.setBoolean(applicationContext, NETWORK_AUTO_CONNECT, viewBinding.swNetworkAutoConnect.isChecked) //12
        PrefUtil.setBoolean(applicationContext, INNER_RADIO_FEC, viewBinding.swInnerRadioFec.isChecked) //13
        PrefUtil.setBoolean(applicationContext, RAW_DATA_SAVE, viewModel1.bool_rawdatasave.value!!) //9
        PrefUtil.setBoolean(applicationContext, AUTO_APN, viewModel1.auto_apn.value!!) //10
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
              } catch (e:Exception){
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
                        } catch (e:Exception){
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
}
