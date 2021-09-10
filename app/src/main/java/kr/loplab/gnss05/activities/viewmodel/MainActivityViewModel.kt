package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.connection.ConnectionStatus


class MainActivityViewModel : ViewModel() {
    private var name = ""
    var connect_type = MutableLiveData<Int>(0)
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
}