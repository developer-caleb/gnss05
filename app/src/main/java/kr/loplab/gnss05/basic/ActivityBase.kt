package kr.loplab.gnss02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import kr.loplab.gnss05.GlobalApplication
import kr.loplab.gnss05.common.Define.REQUEST_CODE_STRING


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
        if(GlobalApplication.mToast!=null){
            GlobalApplication.mToast.cancel();
        }
        GlobalApplication.mToast = Toast.makeText(baseContext,str,Toast.LENGTH_SHORT)
        GlobalApplication.mToast.show();
        Log.d("TAG", "showToast: $str")
    }
    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        intent!!.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        intent!!.putExtra(REQUEST_CODE_STRING, requestCode);
        super.startActivityForResult(intent, requestCode, options)
    }

    fun requestETfocus(editText : com.google.android.material.textfield.TextInputEditText){
        editText.requestFocus()
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText,0)
    }
}

