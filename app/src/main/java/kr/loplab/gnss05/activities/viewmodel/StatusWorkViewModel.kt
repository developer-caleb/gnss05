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