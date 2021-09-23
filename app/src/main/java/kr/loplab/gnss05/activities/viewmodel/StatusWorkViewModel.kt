package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.activities.cors_servermanager.Server
import kr.loplab.gnss05.activities.workmanager.Worker
import kr.loplab.gnss05.enums.SurveyType

class StatusWorkViewModel : ViewModel(){

    private var name = ""
    var surveyModeLayout = MutableLiveData<Boolean>(false)
    var surveyType = MutableLiveData<SurveyType>(SurveyType.TOPO)
    var toolbarOpen = MutableLiveData<Boolean>(false)
    var horizontalError  = MutableLiveData<String>("0.00") //수평오차
    var verticalError  = MutableLiveData<String>("0.00") //수직오차
    var calSatelliteNum  = MutableLiveData<String>("0") //연산 중인 위성
    var allSatelliteNum  = MutableLiveData<String>("0") //확인 된 위성
    public var y = MutableLiveData<String>("0") //y
    public var x = MutableLiveData<String>("0") //x
    public var z = MutableLiveData<String>("0") //z
    var pdop = MutableLiveData<String>("0") //PDOP
    var hdop = MutableLiveData<String>("0") //HDOP
    var vdop = MutableLiveData<String>("0") //vDOP

    /*  var ellipsoidNameNum = MutableLiveData<Int>(0)
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

    /*
      var data_connection_type = MutableLiveData<Int>(0)
      var innerRadioChannelNum = MutableLiveData<Int>(0)
      var radioModeChannelNum = MutableLiveData<Int>(0)
      var innerRadioProtocolNum = MutableLiveData<Int>(0)
      var radioModeProtocolNum = MutableLiveData<Int>(0)


      var innerRadioIntervalNum =  MutableLiveData<Int>(0)
      var innerRadioPowerNum =  MutableLiveData<Int>(0)
      var radioModePowerNum = MutableLiveData<Int>(0)

      var outerRadioCommunicationSpeedNum =  MutableLiveData<Int>(0)
      var ggaUploadIntervalNum =  MutableLiveData<Int>(0)
      var networkSystemNum = MutableLiveData<Int>(0)

     */



    init {  }
    fun setStringvalue(data : MutableLiveData<String>, str : String){
        data.value = str
    }
    fun setIntvalue(data : MutableLiveData<Int>, num : Int){
        data.value = num
    }
    fun setBoolvalue(data : MutableLiveData<Boolean>, boolvalue : Boolean){
        data.value = boolvalue
    }
    fun setSurveyType(data : MutableLiveData<SurveyType>, value : SurveyType){
        data.value = value
    }
}