package kr.loplab.gnss02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

//rxjava2용 뷰모델
open class ViewModelBasic :ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    protected var TAG = javaClass.simpleName
    protected var _msg = MutableLiveData<String>()
    val msg: LiveData<String> get() = _msg

    //구독 등록
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    //구독취소 subscribe 취소시킴 (메모리 관리)
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}