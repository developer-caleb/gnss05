package kr.loplab.gnss05.activities.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReferenceContryViewModel : ViewModel() {
    private var name = ""
    var onprogress = MutableLiveData<Boolean>(false)

    init {

    }

    fun btclick() {
        onprogress.value = !onprogress.value!!
    }

    fun getName() = name
}