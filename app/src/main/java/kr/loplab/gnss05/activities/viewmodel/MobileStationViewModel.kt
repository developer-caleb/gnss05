package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.activities.cors_servermanager.Server
import kr.loplab.gnss05.activities.workmanager.Worker
import kr.loplab.gnss05.common.OptionList


class MobileStationViewModel : ViewModel() {
    private var name = ""
    var cutAngleNum = MutableLiveData<Int>(0)
    var bool_rawdatasave = MutableLiveData<Boolean>(false)
    var collectionIntervalNum = MutableLiveData<Int>(0)
    var data_connection_type = MutableLiveData<Int>(0)
    var innerRadioChannelNum = MutableLiveData<Int>(0)
    var innerRadioProtocolNum = MutableLiveData<Int>(0)
    var innerRadioIntervalNum =  MutableLiveData<Int>(0)
    var innerRadioPowerNum =  MutableLiveData<Int>(0)
    var outerRadioCommunicationSpeedNum =  MutableLiveData<Int>(0)
    var ggaUploadIntervalNum =  MutableLiveData<Int>(0)
    var networkSystemNum = MutableLiveData<Int>(0)

    var apnIndex = MutableLiveData<Int>(0)
    var corsIndex = MutableLiveData<Int>(0)
    var apn_list = MutableLiveData<ArrayList<Worker>>()
    var cors_list = MutableLiveData<ArrayList<Server>>()
    /* var onprogress = MutableLiveData<Boolean>(false)

     var networkSystemNum = MutableLiveData<Int>(0)
     var network_mode = MutableLiveData<Int>(0)
     var startModeNum = MutableLiveData<Int>(0)
     var deplaceModeNum = MutableLiveData<Int>(0)


     var auto_apn = MutableLiveData<Boolean>(false)
*/

    init {  }

   /* fun btclick() {
        onprogress.value = !onprogress.value!!
    }

    fun getName() = name
    fun setRawDatavalue(bool : Boolean){
        bool_rawdatasave.value = bool
    }
    fun setDataConnectionType(num : Int){
      data_connect_type.value = num
    }
    fun setNetworkMode(num : Int){
        network_mode.value = num
    }
    fun setNetworkSystemNum(num : Int){
        networkSystemNum.value = num
    }
    fun setAutoApn(bool : Boolean){
        auto_apn.value = bool
    }
    fun setInnerRadioProtocol(num : Int){
        innerRadioProtocolNum.value = num
    }*/
    fun setIntvalue(data : MutableLiveData<Int>, num : Int){
        data.value = num
    }
    fun setBoolvalue(data : MutableLiveData<Boolean>, boolvalue : Boolean){
        data.value = boolvalue
    }
}