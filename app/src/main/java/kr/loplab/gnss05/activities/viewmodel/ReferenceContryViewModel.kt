package kr.loplab.gnss05.activities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ReferenceContryViewModel : ViewModel() {
    private var name = ""
    var onprogress = MutableLiveData<Boolean>(false)
    var bool_rawdatasave = MutableLiveData<Boolean>(false)
    var data_connect_type = MutableLiveData<Int>(0)
    var network_mode = MutableLiveData<Int>(0)
    init {

    }

    fun btclick() {
        onprogress.value = !onprogress.value!!
    }

    fun getName() = name
    fun setRawDatavalue(bool : Boolean){
        bool_rawdatasave.postValue(bool);
    }
    fun setDataConnectionType(num : Int){
      data_connect_type.postValue(num)
    }
    fun setNetworkMode(num : Int){
        network_mode.postValue(num)
    }
}