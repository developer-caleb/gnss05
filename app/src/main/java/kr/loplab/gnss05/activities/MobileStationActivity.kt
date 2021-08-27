package kr.loplab.gnss05.activities

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.MobileStationViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityMobileStationBinding

class MobileStationActivity : ActivityBase<ActivityMobileStationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_mobile_station
    lateinit var viewModel1: MobileStationViewModel

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
        viewBinding.layoutOuterRadioCommunicationSpeed.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.COMMUNICATION_SPEED_LIST
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
    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.cutAngleNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_CUT_ANGLE, 1))
        viewModel1.setIntvalue(viewModel1.collectionIntervalNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_COLLECTION_INTERVAL))  //2
        viewModel1.setIntvalue(viewModel1.data_connection_type, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_DATA_CONNECTION_TYPE))  //2
        viewModel1.setBoolvalue(viewModel1.bool_rawdatasave, PrefUtil.getBoolean(applicationContext, Define.MOBILE_STATION_RAW_DATA_SAVE))  //2
        viewModel1.setIntvalue(viewModel1.innerRadioChannelNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_CHANNEL))  //7
        viewModel1.setIntvalue(viewModel1.innerRadioProtocolNum, PrefUtil.getInt2(this, MOBILE_STATION_INNER_RADIO_PROTOCOL)) //5
        viewModel1.setIntvalue(viewModel1.innerRadioIntervalNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_INTERVAL))  //8
        viewBinding.swInnerRadioFec.isChecked =PrefUtil.getBoolean(applicationContext, MOBILE_STATION_INNER_RADIO_FEC) //13
        viewModel1.setIntvalue(viewModel1.innerRadioPowerNum, PrefUtil.getInt2(applicationContext, Define.MOBILE_STATION_INNER_RADIO_POWER))  //14
        viewModel1.setIntvalue(viewModel1.outerRadioCommunicationSpeedNum, PrefUtil.getInt2(applicationContext, Define.REFERENCE_COUNTRY_OUTERRADIOCOMMUNICATION_SPEED, 9600))  //14

    }
    fun savesettings(){
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_CUT_ANGLE, viewModel1.cutAngleNum.value!!)
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_COLLECTION_INTERVAL, viewModel1.collectionIntervalNum.value!!) //2
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_DATA_CONNECTION_TYPE, viewModel1.data_connection_type.value!!) //2
        PrefUtil.setBoolean(applicationContext, Define.MOBILE_STATION_RAW_DATA_SAVE, viewModel1.bool_rawdatasave.value!!) //9
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_CHANNEL, viewModel1.innerRadioChannelNum.value!!) //7
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_PROTOCOL, viewModel1.innerRadioProtocolNum.value!!) //5
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_INTERVAL, viewModel1.innerRadioIntervalNum.value!!) //8
        PrefUtil.setBoolean(applicationContext, MOBILE_STATION_INNER_RADIO_FEC, viewBinding.swInnerRadioFec.isChecked) //13
        PrefUtil.setInt(applicationContext, Define.MOBILE_STATION_INNER_RADIO_POWER, viewModel1.innerRadioPowerNum.value!!) //14
        PrefUtil.setInt(applicationContext, REFERENCE_COUNTRY_OUTERRADIOCOMMUNICATION_SPEED, viewModel1.outerRadioCommunicationSpeedNum.value!!) //??

    }
}