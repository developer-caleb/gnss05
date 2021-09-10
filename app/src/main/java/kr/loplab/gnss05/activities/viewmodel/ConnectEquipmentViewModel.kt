package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.connection.ConnectionStatus


class ConnectEquipmentViewModel : ViewModel() {
    private var name = ""
    var connect_mode = MutableLiveData<Int>(0)
    var scanning = MutableLiveData<Boolean>(false)
    var connection_state = MutableLiveData<ConnectionStatus>(ConnectionStatus.DISCONNECT)
    init {

    }

    fun btclick() {

    }

    fun getName() = name
    fun setConnectMode(num : Int){
        connect_mode.postValue(num)
    }
    fun setIntvalue(data : MutableLiveData<Int>, num : Int){
        data.value = num
    }
    fun setBoolvalue(data : MutableLiveData<Boolean>, boolvalue : Boolean){
        data.value = boolvalue
    }
    fun setConnectionState(connectionStatus : ConnectionStatus){
        connection_state.postValue(connectionStatus)
    }
}