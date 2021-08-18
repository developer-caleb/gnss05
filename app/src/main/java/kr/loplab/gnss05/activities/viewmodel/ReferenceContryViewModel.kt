package kr.loplab.gnss05.activities.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.common.PrefUtil

class ReferenceContryViewModel : ViewModel() {
    private var name = ""
    var onprogress = MutableLiveData<Boolean>(false)
    var bool_rawdatasave = MutableLiveData<Boolean>(false)
    init {

    }

    fun btclick() {
        onprogress.value = !onprogress.value!!
    }

    fun getName() = name
    fun setboolvalue(bool : Boolean){
        bool_rawdatasave.postValue(bool);
    }
}