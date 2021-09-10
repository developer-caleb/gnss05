package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.connection.ConnectionStatus
import kr.loplab.gnss05.connection.ConnectionTypes
import kr.loplab.gnss05.enums.ConnectType


class MainActivityViewModel : ViewModel() {
    private var name = ""
    var connect_type = MutableLiveData<ConnectType>(ConnectType.MOBILE_STATION)
    var connection_state = MutableLiveData<ConnectionStatus>(ConnectionStatus.DISCONNECT)
    init {

    }

    fun btclick() {

    }

    fun getName() = name
    fun setIntvalue(data : MutableLiveData<Int>, num : Int){
        data.value = num
    }
    fun setBoolvalue(data : MutableLiveData<Boolean>, boolvalue : Boolean){
        data.value = boolvalue
    }
    fun setConnectionState(connectionStatus : ConnectionStatus){
        connection_state.value = connectionStatus
    }
}