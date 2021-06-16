package kr.loplab.gnss05

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandardPointViewModel : ViewModel() {
    private var name = ""

    fun setName(newName: String) {
        name = newName
    }

    fun getName() = name
}