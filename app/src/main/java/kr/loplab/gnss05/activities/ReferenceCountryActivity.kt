package kr.loplab.gnss05.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_reference_country.*

import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.cors_servermanager.CORSServerManagerActivity
import kr.loplab.gnss05.activities.viewmodel.ReferenceCountryViewModel
import kr.loplab.gnss05.activities.workmanager.WorkManagerActivity
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.COLLECTION_INTERVAL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.DATA_CONNECTION_TYPE_List
import kr.loplab.gnss05.common.OptionList.Companion.DEPLACEMENT_MODE_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_CHANNEL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.INNER_RADIO_PROTOCOL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.NETWORK_MODE_List
import kr.loplab.gnss05.common.OptionList.Companion.NETWORK_SYSTEM_List
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding

class ReferenceCountryActivity : ActivityBase<ActivityReferenceCountryBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_country
    lateinit var viewModel1:ReferenceCountryViewModel
    var startModeNum = 0;
    var deplaceModeNum = 0;
    var collectionModeNum = 0;
    var wifiPasswordView = false;
    var networkSystemNum = 0;
    var innerRadioChannelNum = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";
        //viewbinding
        viewModel1 = ViewModelProvider(this).get(ReferenceCountryViewModel::class.java)
        viewBinding.referencecontryviewmodel = viewModel1
    }

    override fun initListener() {
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
        viewBinding.corsSettingBt.setOnClickListener {
            intent = Intent(this, CORSServerManagerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btOpenWorkManager.setOnClickListener {
            intent = Intent(this, WorkManagerActivity::class.java)
            startActivity(intent);
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
            dlg.selectedposition= startModeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                startModeNum = i
                viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[startModeNum]
                dlg.refresh()
                dlg.dismiss()
                if(i==1){
                    intent = Intent(this, ReferenceCoordinateSetting::class.java)
                    startActivity(intent);
                }
            }
            dlg.setHeader("시작 모드")
        }
       viewBinding.layoutDisplacementMode.setOnClickListener {
           val dlg = MyDialog(this)
           var alist = DEPLACEMENT_MODE_LIST
           var prefvalue = Define.DEPLACEMENT_MODE
           dlg.firstLayoutUse = false
           dlg.list = alist
           dlg.selectedposition= deplaceModeNum
           dlg.start("")
           dlg.setOnListClickedListener { view, i ->
               Log.d(TAG, "initListener: $i")

               deplaceModeNum = i
               viewBinding.tvDisplacementMode.text = alist[deplaceModeNum]
               dlg.refresh()
               dlg.dismiss()
           }
           dlg.setHeader("변위 모드")
       }
        viewBinding.layoutCollectionInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = COLLECTION_INTERVAL_LIST
            var prefvalue = Define.COLLECTION_INTERVAL
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= collectionModeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                collectionModeNum = i

                viewBinding.tvCollectionInterval.text = alist[collectionModeNum]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("수집간격")
        }
        viewBinding.layoutDataConnectionType.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = DATA_CONNECTION_TYPE_List
            var prefvalue = Define.DATA_CONNECTION_TYPE
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
            dlg.selectedposition= networkSystemNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                networkSystemNum = i
                viewBinding.tvNetworkSystem.text = alist[networkSystemNum]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("네트워크 시스템")
        }

        viewBinding.layoutReferenceCountryAutoplay.setOnClickListener {
            viewBinding.swReferenceCountryAutoplay.isChecked = !viewBinding.swReferenceCountryAutoplay.isChecked
        }
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
        }
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
            dlg.setHeader("네트워크 모드")
        }

        viewBinding.layoutInnerRadioChannel.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = INNER_RADIO_CHANNEL_LIST
            var prefvalue = Define.INNER_RADIO_CHANNEL
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= innerRadioChannelNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                innerRadioChannelNum = i
                viewModel1.setNetworkMode(i)
                viewBinding.tvInnerRadioChannel.text = alist[i]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("채널")
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
    }

    override fun initDatabinding() {
        startModeNum = PrefUtil.getInt2(applicationContext, Define.START_MODE)
        deplaceModeNum = PrefUtil.getInt2(applicationContext, Define.DEPLACEMENT_MODE)
        collectionModeNum = PrefUtil.getInt2(applicationContext, Define.COLLECTION_INTERVAL)
        networkSystemNum = PrefUtil.getInt2(applicationContext, NETWORK_SYSTEM)
        innerRadioChannelNum = PrefUtil.getInt2(applicationContext, INNER_RADIO_CHANNEL)
        //TODO


        viewModel1.setRawDatavalue(PrefUtil.getBoolean(this, RAW_DATA_SAVE))
        viewModel1.setDataConnectionType(PrefUtil.getInt2(this, DATA_CONNECTION_TYPE))
        viewModel1.setNetworkMode(PrefUtil.getInt2(this, NETWORK_MODE))
        viewModel1.setAutoApn(PrefUtil.getBoolean(this, AUTO_APN))
        viewModel1.setInnerRadioProtocol(PrefUtil.getInt2(this, INNER_RADIO_PROTOCOL))

        viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[startModeNum]
        viewBinding.tvDisplacementMode.text = DEPLACEMENT_MODE_LIST[deplaceModeNum]
        viewBinding.tvCollectionInterval.text = COLLECTION_INTERVAL_LIST[collectionModeNum]
        viewBinding.tvDataConnectionType.text = DATA_CONNECTION_TYPE_List[viewModel1.data_connect_type.value!!]
        viewBinding.tvNetworkMode.text = NETWORK_MODE_List[viewModel1.network_mode.value!!]
        viewBinding.tvNetworkSystem.text = NETWORK_SYSTEM_List[networkSystemNum]
        viewBinding.tvInnerRadioChannel.text = INNER_RADIO_CHANNEL_LIST[innerRadioChannelNum]
        viewBinding.tvInnerRadioProtocol.text = INNER_RADIO_PROTOCOL_LIST[viewModel1.innerRadioProtocolNum.value!!]


        viewBinding.swReferenceCountryAutoplay.isChecked =PrefUtil.getBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY)
        viewBinding.swNetworkAutoConnect.isChecked =PrefUtil.getBoolean(applicationContext, NETWORK_AUTO_CONNECT)


    }
    fun savesettings(){
        PrefUtil.setInt(applicationContext, Define.NETWORK_MODE, viewModel1.network_mode.value!!)
        PrefUtil.setInt(applicationContext, Define.START_MODE, startModeNum)
        PrefUtil.setInt(applicationContext, Define.DEPLACEMENT_MODE, deplaceModeNum)
        PrefUtil.setInt(applicationContext, Define.COLLECTION_INTERVAL, collectionModeNum)
        PrefUtil.setInt(applicationContext, Define.DATA_CONNECTION_TYPE, viewModel1.data_connect_type.value!!)
        PrefUtil.setInt(applicationContext, Define.NETWORK_SYSTEM, networkSystemNum)
        PrefUtil.setInt(applicationContext, Define.INNER_RADIO_CHANNEL, innerRadioChannelNum)
        PrefUtil.setBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY, viewBinding.swReferenceCountryAutoplay.isChecked)
        PrefUtil.setBoolean(applicationContext, NETWORK_AUTO_CONNECT, viewBinding.swNetworkAutoConnect.isChecked)
        PrefUtil.setBoolean(this, RAW_DATA_SAVE, viewModel1.bool_rawdatasave.value!!);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== RESULT_OK && requestCode == REQUEST_WORKMANAGER)
        {


        }

        if(resultCode== RESULT_OK && requestCode == REQUEST_CORS_SERVER_MANAGER)
        {
            initDatabinding()
        }
    }


}
