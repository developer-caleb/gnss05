package kr.loplab.gnss05

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandardPointViewModel : ViewModel() {
    private var name = ""
    var onprogress = MutableLiveData<Boolean>(false)

    init {

    }

    fun btclick() {
        onprogress.value = !onprogress.value!!
    }

    fun getName() = name
}