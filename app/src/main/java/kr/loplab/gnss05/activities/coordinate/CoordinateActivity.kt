package kr.loplab.gnss05.activities.coordinate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.CoordinateViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityCoordinateBinding
import java.lang.reflect.Array.setDouble

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
            requestETfocus(viewBinding.etCoordinateName)
        }
        viewBinding.layoutNewtarget.setOnClickListener {
            requestETfocus(viewBinding.etNewTarget)
        }
        viewBinding.layoutVX.setOnClickListener {
            requestETfocus(viewBinding.etVX)
        }
        viewBinding.layoutVY.setOnClickListener {
           requestETfocus(viewBinding.etVY)
        }
        viewBinding.layoutVZ.setOnClickListener {
            requestETfocus(viewBinding.etVZ)
        }
        viewBinding.layoutSevenParameterDeltaAlpha.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaAlpha)
        }
        viewBinding.layoutSevenParameterDeltaBeta.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaBeta)
        }
        viewBinding.layoutSevenParameterDeltaGamma.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaGamma)
        }
        viewBinding.layoutSevenParameterDeltaX.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaX)
        }
        viewBinding.layoutSevenParameterDeltaY.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaY)
        }
        viewBinding.layoutSevenParameterDeltaZ.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterDeltaZ)

        }
        viewBinding.layoutSevenParameterScale.setOnClickListener {
            requestETfocus(viewBinding.etSevenParameterScale)
        }
        viewBinding.layoutFourParameterNorthMove.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterNorthDirectionMove)
        }
        viewBinding.layoutFourParameterEastMove.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterEastDirectionMove)
        }
        viewBinding.layoutFourParameterScale.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterScale)
        }
        viewBinding.layoutFourParameterFarNorthDirection.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterFarNorthDirection)
        }
        viewBinding.layoutFourParameterFarEastDirection.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterFarEastDirection)
        }
        viewBinding.layoutFourParameterRotation.setOnClickListener {
            requestETfocus(viewBinding.etFourParameterRotation)
        }
        viewBinding.etFourParameterNorthDirectionMove.setOnFocusChangeListener { v, hasFocus ->
        when(hasFocus)
        {
            true -> if(viewBinding.etFourParameterNorthDirectionMove.text!!.toString().toDouble() == 0.0 ){ viewBinding.etFourParameterNorthDirectionMove.setText("") }
                else -> if(viewBinding.etFourParameterNorthDirectionMove.text!!.isEmpty()) viewBinding.etFourParameterNorthDirectionMove.setText("0.0")
        }
        }
        viewBinding.etFourParameterRotation.setOnFocusChangeListener { v, hasFocus ->

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
        viewBinding.layoutConversionType.setOnClickListener {
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
        viewBinding.layoutInputSpeed.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.inputSpeed , !viewModel1.inputSpeed.value!!)
        }
        viewBinding.layoutUseSevenParameter.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.sevenParameterUsing , !viewModel1.sevenParameterUsing.value!!)
        }
        viewBinding.layoutSevenParameterMode.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.SEVEN_PARAMETER_MODE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.sevenParameterMode.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.sevenParameterMode.value = i
                dlg.dismiss()
            }
            dlg.setHeader("모드")
        }
        viewBinding.layoutFourParameterUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.fourParameterUsing , !viewModel1.fourParameterUsing.value!!)
        }

    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.ellipsoidNameNum, PrefUtil.getInt2(this, COORDINATE_ELLIPSOIDNAME))
        viewModel1.setBoolvalue(viewModel1.itrfConversion, PrefUtil.getBoolean(applicationContext, COORDINATE_ITRFCONVERSION))
        viewModel1.setBoolvalue(viewModel1.inputSpeed, PrefUtil.getBoolean(applicationContext, COORDINATE_INPUT_SPEED))
        viewModel1.setBoolvalue(viewModel1.sevenParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_SEVEN_PARAMETER_USING))
        viewModel1.setBoolvalue(viewModel1.fourParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_FOUR_PARAMETER_USING))


        viewModel1.setIntvalue(viewModel1.conversionTypeNum, PrefUtil.getInt2(this, COORDINATE_CONVERSION_TYPE))
        viewModel1.setIntvalue(viewModel1.sevenParameterMode, PrefUtil.getInt2(this, COORDINATE_SEVEN_PARAMETER_MODE))

        viewBinding.etCoordinateName.setText(PrefUtil.getString(this, COORDINATE_COORDINATE_NAME))
        viewBinding.etNewTarget.setText(PrefUtil.getString(this, COORDINATE_NEW_TARGET))
        viewBinding.etVX.setText(PrefUtil.getString(this, COORDINATE_VX))
        viewBinding.etVY.setText(PrefUtil.getString(this, COORDINATE_VY))
        viewBinding.etVZ.setText(PrefUtil.getString(this, COORDINATE_VZ))

        viewBinding.etSevenParameterDeltaX.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_X))
        viewBinding.etSevenParameterDeltaY.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_Y))
        viewBinding.etSevenParameterDeltaZ.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_Z))
        viewBinding.etSevenParameterDeltaAlpha.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_ALPHA))
        viewBinding.etSevenParameterDeltaBeta.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_BETA))
        viewBinding.etSevenParameterDeltaGamma.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_GAMMA))
        viewBinding.etSevenParameterScale.setText(PrefUtil.getString(this, COORDINATE_SEVEN_PARAMETER_DELTA_SCALE))
        viewBinding.etFourParameterNorthDirectionMove.setText(PrefUtil.getDouble(this, COORDINATE_FOUR_PARAMETER_NORTH_DIRECTION_MOVE, 0.0).toString())
        viewBinding.etFourParameterEastDirectionMove.setText(PrefUtil.getDouble(this, COORDINATE_FOUR_PARAMETER_EAST_DIRECTION_MOVE, 0.0).toString())
        viewBinding.etFourParameterScale.setText(PrefUtil.getDouble(this, COORDINATE_FOUR_PARAMETER_SCALE, 0.0).toString())
        viewBinding.etFourParameterFarNorthDirection.setText(PrefUtil.getDouble(this, COORDINATE_FOUR_PARAMETER_FAR_NORTH_DIRECTION_MOVE, 0.0).toString())
        viewBinding.etFourParameterFarEastDirection.setText(PrefUtil.getDouble(this,  COORDINATE_FOUR_PARAMETER_FAR_EAST_DIRECTION_MOVE, 0.0).toString())


    }

    fun saveSettings(){
        PrefUtil.setInt(applicationContext, Define.COORDINATE_ELLIPSOIDNAME, viewModel1.ellipsoidNameNum.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_ITRFCONVERSION, viewModel1.itrfConversion.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_INPUT_SPEED, viewModel1.inputSpeed.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_SEVEN_PARAMETER_USING, viewModel1.sevenParameterUsing.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_FOUR_PARAMETER_USING, viewModel1.fourParameterUsing.value!!)


        PrefUtil.setInt(applicationContext, Define.COORDINATE_CONVERSION_TYPE, viewModel1.conversionTypeNum.value!!)
        PrefUtil.setInt(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_MODE, viewModel1.sevenParameterMode.value!!)

        PrefUtil.setString(applicationContext, Define.COORDINATE_COORDINATE_NAME, viewBinding.etCoordinateName.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_NEW_TARGET, viewBinding.etNewTarget.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_VX, viewBinding.etVX.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_VY, viewBinding.etVY.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_VZ, viewBinding.etVZ.text.toString())

        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_X, viewBinding.etSevenParameterDeltaX.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_Y, viewBinding.etSevenParameterDeltaY.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_Z, viewBinding.etSevenParameterDeltaZ.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_ALPHA, viewBinding.etSevenParameterDeltaAlpha.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_BETA, viewBinding.etSevenParameterDeltaBeta.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_GAMMA, viewBinding.etSevenParameterDeltaGamma.text.toString())
        PrefUtil.setString(applicationContext, Define.COORDINATE_SEVEN_PARAMETER_DELTA_SCALE, viewBinding.etSevenParameterScale.text.toString())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_NORTH_DIRECTION_MOVE, viewBinding.etFourParameterNorthDirectionMove.text.toString().toDouble())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_EAST_DIRECTION_MOVE, viewBinding.etFourParameterEastDirectionMove.text.toString().toDouble())

        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_SCALE, viewBinding.etFourParameterScale.text.toString().toDouble())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_FAR_NORTH_DIRECTION_MOVE, viewBinding.etFourParameterFarNorthDirection.text.toString().toDouble())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_FAR_EAST_DIRECTION_MOVE, viewBinding.etFourParameterFarEastDirection.text.toString().toDouble())



    }


}