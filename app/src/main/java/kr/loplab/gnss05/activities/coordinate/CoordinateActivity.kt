package kr.loplab.gnss05.activities.coordinate

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.CoordinateViewModel
import kr.loplab.gnss05.activities.viewmodel.ReferenceCountryViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.ELLIPSOID_NAME_LIST
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityCoordinateBinding

class CoordinateActivity : ActivityBase<ActivityCoordinateBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_coordinate
    lateinit var viewModel1:CoordinateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(CoordinateViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.btConfirm.setOnClickListener {
            saveSettings()
        }
        viewBinding.btExport.setOnClickListener {  }
        viewBinding.btLoad.setOnClickListener {
        }
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.layoutCoordianteName.setOnClickListener {
            viewBinding.etCoordinateName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etCoordinateName,0)
        }
        viewBinding.layoutEllipsoidName.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.ELLIPSOID_NAME_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.ellipsoidNameNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.ellipsoidNameNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("타원체명")
        }
        viewBinding.layoutItrfConversion.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.itrfConversion , !viewModel1.itrfConversion.value!!)
        }
        viewBinding.layoutEllipsoidName.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.CONVERSION_TYPE_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.conversionTypeNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.conversionTypeNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("변환 유형")
        }

    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.ellipsoidNameNum, PrefUtil.getInt2(this, COORDINATE_ELLIPSOIDNAME))
        viewModel1.setBoolvalue(viewModel1.itrfConversion, PrefUtil.getBoolean(applicationContext, COORDINATE_ITRFCONVERSION))
        viewModel1.setIntvalue(viewModel1.conversionTypeNum, PrefUtil.getInt2(this, COORDINATE_CONVERSION_TYPE))

    }

    fun saveSettings(){
        PrefUtil.setInt(applicationContext, Define.COORDINATE_ELLIPSOIDNAME, viewModel1.ellipsoidNameNum.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_ITRFCONVERSION, viewModel1.itrfConversion.value!!)
        PrefUtil.setInt(applicationContext, Define.COORDINATE_CONVERSION_TYPE, viewModel1.conversionTypeNum.value!!)
    }
}