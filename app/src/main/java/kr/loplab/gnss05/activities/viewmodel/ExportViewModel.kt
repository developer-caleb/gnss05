package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.activities.cors_servermanager.Server
import kr.loplab.gnss05.activities.workmanager.Worker

class ExportViewModel : ViewModel(){
    var roadCrossSecionOutputUsing = MutableLiveData<Boolean>(false)

    private var name = ""
    var degreeFormNum = MutableLiveData<Int>(0)
    var pointAssist = MutableLiveData<Boolean>(false)
    var pointMeasurement = MutableLiveData<Boolean>(false)
    var controlMeasurement = MutableLiveData<Boolean>(false)
    var pointInput = MutableLiveData<Boolean>(false)
    var pointCalculation = MutableLiveData<Boolean>(false)
    var pointSkate = MutableLiveData<Boolean>(false)
    var screenPoint = MutableLiveData<Boolean>(false)
    var referencePoint = MutableLiveData<Boolean>(false)
    var fileFormNum1 = MutableLiveData<Int>(0)
    var fileFormNum2 = MutableLiveData<Int>(0)
    var fileFormList1 = MutableLiveData<String>("")
    var fileFormList2 = MutableLiveData<String>("")

    /* var ellipsoidNameNum = MutableLiveData<Int>(0)
     var itrfConversion = MutableLiveData<Boolean>(false)
     var inputSpeed = MutableLiveData<Boolean>(false)
     var sevenParameterUsing = MutableLiveData<Boolean>(false)
     var conversionTypeNum = MutableLiveData<Int>(0)
     var sevenParameterMode = MutableLiveData<Int>(0)
     var fourParameterUsing = MutableLiveData<Boolean>(false)
     var verticalControlParameterUsing = MutableLiveData<Boolean>(false)
     var verticalAdjustmentParameterUsing = MutableLiveData<Boolean>(false)
     var gridFileUsing = MutableLiveData<Boolean>(false)
     var inputParameterUsing = MutableLiveData<Boolean>(false)*/



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
    fun setStringValue(data : MutableLiveData<String>, stringValue : String){
        data.value = stringValue
    }
}