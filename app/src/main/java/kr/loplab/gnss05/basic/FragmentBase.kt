package kr.loplab.gnss02


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kr.loplab.gnss05.GlobalApplication

abstract class FragmentBase<T : ViewDataBinding> : Fragment() {
    val TAG = javaClass.simpleName
    abstract val layoutResourceId: Int
    lateinit var viewBinding: T
    lateinit var title:String
    //abstract val viewModel : VM

    protected abstract fun init()
    protected abstract fun initListener()
    protected abstract fun initDataBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        //viewDataBinding.setVariable(BR.viewModel,viewModel)
        viewBinding.lifecycleOwner=this

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initDefault()

        init()
        initListener()
        initDataBinding()
    }



    private fun initDefault(){

    }

    fun showToast(str:String){
        if(GlobalApplication.mToast!=null){
            GlobalApplication.mToast.cancel();
        }
        GlobalApplication.mToast = Toast.makeText(context,str,Toast.LENGTH_SHORT)
        GlobalApplication.mToast.show();
        Log.d("TAG", "showToast: $str")
    }






}