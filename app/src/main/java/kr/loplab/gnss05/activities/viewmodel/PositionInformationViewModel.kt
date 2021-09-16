package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.activities.cors_servermanager.Server
import kr.loplab.gnss05.activities.workmanager.Worker

class PositionInformationViewModel : ViewModel(){
    var latitudeValue = MutableLiveData<Float>(0f) //위도
    var longitudeValue = MutableLiveData<Float>(0f) //경도
    var altitudeValue = MutableLiveData<Float>(0f) //고도
    var yValue = MutableLiveData<Float>(0f) //y
    var xValue = MutableLiveData<Float>(0f) //x
    var zValue = MutableLiveData<Float>(0f) //z
    var velocityValue  = MutableLiveData<Float>(0f) //속도
    var directionValue  = MutableLiveData<Float>(0f) //방향
    var solutionInfoValue  = MutableLiveData<Int>(0) //솔루션 정보
    var horizontalErrorValue  = MutableLiveData<Float>(0f) //수평오차
    var verticalErrorValue  = MutableLiveData<Float>(0f) //수직오차
    var satelliteValue  = MutableLiveData<String>("") //위성
    var deplacementModeValue = MutableLiveData<String>("") //변위모드
    var delayValue = MutableLiveData<Int>(0) //지연
    var pdopValue = MutableLiveData<Float>(0f) //PDOP
    var hdopValue = MutableLiveData<Float>(0f) //HDOP
    var vdopValue = MutableLiveData<Float>(0f) //vDOP
    //var vdopValue = MutableLiveData<Float>(0f) //vDOP




    init {  }


    fun setIntvalue(data : MutableLiveData<Int>, num : Int){
        data.value = num
    }
    fun setBoolvalue(data : MutableLiveData<Boolean>, boolvalue : Boolean){
        data.value = boolvalue
    }
}