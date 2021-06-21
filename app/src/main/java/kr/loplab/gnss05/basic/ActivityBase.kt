package kr.loplab.gnss02

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar


abstract class ActivityBase<T : ViewDataBinding>: AppCompatActivity() {
    val TAG = javaClass.simpleName

    abstract val layoutResourceId: Int
    lateinit var viewBinding: T

    protected abstract fun init()

    protected abstract fun initListener()
    protected abstract fun initDatabinding()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutResourceId)
       // viewDataBinding.setVariable( BR.viewModel ,viewModel) viewmodel은 직접 넣어주자.. 어떻게 넣어주야하지?
        viewBinding.lifecycleOwner = this

        initDefault()
        init()
        initListener()
        initDatabinding()
    }

    private fun initDefault(){

    }


    fun snackbarShow(str:String){
        Snackbar.make(window.decorView.rootView,str, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(str:String){
        Log.d(TAG, "showToast: str")
        Toast.makeText(applicationContext,str, Toast.LENGTH_LONG).show()
    }

}