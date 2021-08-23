package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ConnectEquipmentViewModel : ViewModel() {
    private var name = ""
    var connect_mode = MutableLiveData<Int>(0)
    init {

    }

    fun btclick() {

    }

    fun getName() = name
    fun setConnectMode(num : Int){
        connect_mode.postValue(num)
    }
}