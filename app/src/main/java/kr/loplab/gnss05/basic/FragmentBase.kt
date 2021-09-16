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
import androidx.lifecycle.ViewModel
import kr.loplab.gnss05.GlobalApplication

abstract class FragmentBase<T : ViewDataBinding,VM: ViewModelBasic> : Fragment() {
    val TAG = javaClass.simpleName
    abstract val layoutResourceId: Int
    lateinit var viewDataBinding: T
    lateinit var title:String
    abstract val viewModel : VM

    protected abstract fun init()
    protected abstract fun initListener()
    protected abstract fun initLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        //viewDataBinding.setVariable(BR.viewModel,viewModel)
        viewDataBinding.lifecycleOwner=this

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initDefault()

        init()
        initListener()
        initLiveData()
    }



    private fun initDefault(){
        viewModel.msg.observe(viewLifecycleOwner,{
            showToast(it)
        })
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