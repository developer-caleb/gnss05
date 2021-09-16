package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.activities.cors_servermanager.Server
import kr.loplab.gnss05.activities.workmanager.Worker

class PositionInformationViewModel : ViewModel(){
    public var latitude = MutableLiveData<String>("연결 되지 않음") //위도
    public var longitude = MutableLiveData<String>("연결 되지 않음") //경도
    public var altitude = MutableLiveData<String>("연결 되지 않음") //고도
    public var y = MutableLiveData<String>("연결 되지 않음") //y
    public var x = MutableLiveData<String>("연결 되지 않음") //x
    public var z = MutableLiveData<String>("연결 되지 않음") //z
    var velocity  = MutableLiveData<String>("연결 되지 않음") //속도
    var direction  = MutableLiveData<String>("연결 되지 않음") //방향
    var solutionInfo  = MutableLiveData<String>("연결 되지 않음") //솔루션 정보
    var horizontalError  = MutableLiveData<String>("연결 되지 않음") //수평오차
    var verticalError  = MutableLiveData<String>("연결 되지 않음") //수직오차
    var satellite  = MutableLiveData<String>("연결 되지 않음") //위성
    var deplacementMode = MutableLiveData<String>("연결 되지 않음") //변위모드
    var delay = MutableLiveData<String>("연결 되지 않음") //지연
    var pdop = MutableLiveData<String>("연결 되지 않음") //PDOP
    var hdop = MutableLiveData<String>("연결 되지 않음") //HDOP
    var vdop = MutableLiveData<String>("연결 되지 않음") //vDOP
    var utcTime = MutableLiveData<String>("연결 되지 않음") //UTC time
    var time = MutableLiveData<String>("연결 되지 않음") //time
    var destanceFromReferenceCountry = MutableLiveData<String>("연결 되지 않음") //기준국과의 거리

    //var vdopValue = MutableLiveData<Float>(0f) //vDOP




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
}