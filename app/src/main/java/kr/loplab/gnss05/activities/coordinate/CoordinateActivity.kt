package kr.loplab.gnss05.activities.coordinate

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.CoordinateViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.common.Utilities
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
        viewBinding.btConfirm.setOnClickListener { saveSettings() }
        viewBinding.btExport.setOnClickListener {  }
        viewBinding.btLoad.setOnClickListener {
        }
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.layoutCoordianteName.setOnClickListener { requestETfocus(viewBinding.etCoordinateName) }
        viewBinding.layoutNewtarget.setOnClickListener { requestETfocus(viewBinding.etNewTarget) }
        viewBinding.layoutVX.setOnClickListener { requestETfocus(viewBinding.etVX) }
        viewBinding.layoutVY.setOnClickListener { requestETfocus(viewBinding.etVY) }
        viewBinding.layoutVZ.setOnClickListener { requestETfocus(viewBinding.etVZ) }
        viewBinding.layoutSevenParameterDeltaAlpha.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaAlpha) }
        viewBinding.layoutSevenParameterDeltaBeta.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaBeta) }
        viewBinding.layoutSevenParameterDeltaGamma.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaGamma) }
        viewBinding.layoutSevenParameterDeltaX.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaX) }
        viewBinding.layoutSevenParameterDeltaY.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaY) }
        viewBinding.layoutSevenParameterDeltaZ.setOnClickListener { requestETfocus(viewBinding.etSevenParameterDeltaZ) }
        viewBinding.layoutSevenParameterScale.setOnClickListener { requestETfocus(viewBinding.etSevenParameterScale) }
        viewBinding.layoutFourParameterNorthMove.setOnClickListener { requestETfocus(viewBinding.etFourParameterNorthDirectionMove) }
        viewBinding.layoutFourParameterEastMove.setOnClickListener { requestETfocus(viewBinding.etFourParameterEastDirectionMove) }
        viewBinding.layoutFourParameterScale.setOnClickListener { requestETfocus(viewBinding.etFourParameterScale) }
        viewBinding.layoutFourParameterFarNorthDirection.setOnClickListener { requestETfocus(viewBinding.etFourParameterFarNorthDirection) }
        viewBinding.layoutFourParameterFarEastDirection.setOnClickListener { requestETfocus(viewBinding.etFourParameterFarEastDirection) }
        viewBinding.layoutFourParameterRotation.setOnClickListener { requestETfocus(viewBinding.etFourParameterRotation) }
        viewBinding.layoutVerticalControlParameterA0.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA0) }
        viewBinding.layoutVerticalControlParameterA1.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA1) }
        viewBinding.layoutVerticalControlParameterA2.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA2) }
        viewBinding.layoutVerticalControlParameterA3.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA3) }
        viewBinding.layoutVerticalControlParameterA4.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA4) }
        viewBinding.layoutVerticalControlParameterA5.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterA5) }
        viewBinding.layoutVerticalControlParameterX0.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterX0) }
        viewBinding.layoutVerticalControlParameterY0.setOnClickListener { requestETfocus(viewBinding.etVerticalControlParameterY0) }
        viewBinding.layoutVerticalAdjustmentParameterAdjustmentConstant.setOnClickListener { requestETfocus(viewBinding.etVerticalAdjustmentParameterAdjustmentConstant) }
        viewBinding.layoutVerticalAdjustmentParameterNorthSuperelevation.setOnClickListener { requestETfocus(viewBinding.etVerticalAdjustmentParameterNorthSuperelevation) }
        viewBinding.layoutVerticalAdjustmentParameterEastSuperelevation.setOnClickListener { requestETfocus(viewBinding.etVerticalAdjustmentParameterEastSuperelevation) }
        viewBinding.layoutVerticalAdjustmentParameterFarNorthDirection.setOnClickListener { requestETfocus(viewBinding.etVerticalAdjustmentParameterFarNorthDirection) }
        viewBinding.layoutVerticalAdjustmentParameterFarEastDirection.setOnClickListener { requestETfocus(viewBinding.etVerticalAdjustmentParameterFarEastDirection) }
        viewBinding.layoutInputParameterX.setOnClickListener { requestETfocus(viewBinding.etInputParameterX) }
        viewBinding.layoutInputParameterY.setOnClickListener { requestETfocus(viewBinding.etInputParameterY) }
        viewBinding.layoutInputParameterLevel.setOnClickListener { requestETfocus(viewBinding.etInputParameterLevel) }


        viewBinding.etFourParameterRotation.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                var doubleValue = if (viewBinding.etFourParameterRotation.text!!.isEmpty()) 0.0 else{ viewBinding.etFourParameterRotation.text!!.toString().toDouble()}
                Log.d(TAG, "initListener: -> nofocus, doubleValue : $doubleValue")
                viewBinding.etFourParameterRotation.setText(Utilities.doubledegree(doubleValue).toString())
            }
        }
        viewBinding.etFourParameterNorthDirectionMove.setOnFocusChangeListener { v, hasFocus ->
        when(hasFocus)
        {
            true ->
                if(viewBinding.etFourParameterNorthDirectionMove.text!!.isNotEmpty())
                {if(viewBinding.etFourParameterNorthDirectionMove.text!!.toString().toDouble() == 0.0 ){ viewBinding.etFourParameterNorthDirectionMove.setText("") }}
                else -> if(viewBinding.etFourParameterNorthDirectionMove.text!!.isEmpty()) viewBinding.etFourParameterNorthDirectionMove.setText("0.0")
        }
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
        viewBinding.layoutVerticalControlParameterUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.verticalControlParameterUsing , !viewModel1.verticalControlParameterUsing.value!!)
        }
        viewBinding.layoutGridFileUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.gridFileUsing , !viewModel1.gridFileUsing.value!!)
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
        viewBinding.layoutVerticalAdjustmentParameterUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.verticalAdjustmentParameterUsing , !viewModel1.verticalAdjustmentParameterUsing.value!!)
        }
        viewBinding.layoutGridFileRoute.setOnClickListener {  }
        viewBinding.layoutInputParameterUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.inputParameterUsing , !viewModel1.inputParameterUsing.value!!)
        }
    }

    override fun initDatabinding() {
        viewModel1.setIntvalue(viewModel1.ellipsoidNameNum, PrefUtil.getInt2(this, COORDINATE_ELLIPSOIDNAME))
        viewModel1.setBoolvalue(viewModel1.itrfConversion, PrefUtil.getBoolean(applicationContext, COORDINATE_ITRFCONVERSION))
        viewModel1.setBoolvalue(viewModel1.inputSpeed, PrefUtil.getBoolean(applicationContext, COORDINATE_INPUT_SPEED))
        viewModel1.setBoolvalue(viewModel1.sevenParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_SEVEN_PARAMETER_USING))
        viewModel1.setBoolvalue(viewModel1.fourParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_FOUR_PARAMETER_USING))
        viewModel1.setBoolvalue(viewModel1.verticalControlParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_VERTICAL_CONTROL_PARAMETER_USING))
        viewModel1.setBoolvalue(viewModel1.verticalAdjustmentParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_USING))
        viewModel1.setBoolvalue(viewModel1.inputParameterUsing, PrefUtil.getBoolean(applicationContext, COORDINATE_INPUT_PARAMETER_USING))

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
        viewBinding.etVerticalControlParameterA0.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A0, 0.0).toString())
        viewBinding.etVerticalControlParameterA1.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A1, 0.0).toString())
        viewBinding.etVerticalControlParameterA2.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A2, 0.0).toString())
        viewBinding.etVerticalControlParameterA3.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A3, 0.0).toString())
        viewBinding.etVerticalControlParameterA4.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A4, 0.0).toString())
        viewBinding.etVerticalControlParameterA5.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_A5, 0.0).toString())
        viewBinding.etVerticalControlParameterX0.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_X0, 0.0).toString())
        viewBinding.etVerticalControlParameterY0.setText(PrefUtil.getDouble(this,  COORDINATE_VERTICAL_CONTROL_PARAMETER_Y0, 0.0).toString())
        viewBinding.etVerticalAdjustmentParameterAdjustmentConstant.setText(PrefUtil.getInt2(this,  COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_AdjustmentConstant).toString())
        viewBinding.etVerticalAdjustmentParameterNorthSuperelevation.setText(PrefUtil.getInt2(this,  COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_NorthSuperelevation).toString())
        viewBinding.etVerticalAdjustmentParameterEastSuperelevation.setText(PrefUtil.getInt2(this,  COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_EastSuperelevation).toString())
        viewBinding.etVerticalAdjustmentParameterFarNorthDirection.setText(PrefUtil.getInt2(this,  COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_FarNorthDirection).toString())
        viewBinding.etVerticalAdjustmentParameterFarEastDirection.setText(PrefUtil.getInt2(this,  COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_FarEastDirection).toString())
        viewBinding.etInputParameterX.setText(PrefUtil.getInt2(this,  COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_X).toString())
        viewBinding.etInputParameterY.setText(PrefUtil.getInt2(this,  COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_Y).toString())
        viewBinding.etInputParameterLevel.setText(PrefUtil.getInt2(this,  COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_LEVEL).toString())




    }

    fun saveSettings(){
        PrefUtil.setInt(applicationContext, Define.COORDINATE_ELLIPSOIDNAME, viewModel1.ellipsoidNameNum.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_ITRFCONVERSION, viewModel1.itrfConversion.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_INPUT_SPEED, viewModel1.inputSpeed.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_SEVEN_PARAMETER_USING, viewModel1.sevenParameterUsing.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_FOUR_PARAMETER_USING, viewModel1.fourParameterUsing.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_VERTICAL_CONTROL_PARAMETER_USING, viewModel1.verticalControlParameterUsing.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_USING, viewModel1.verticalAdjustmentParameterUsing.value!!)
        PrefUtil.setBoolean(applicationContext, COORDINATE_INPUT_PARAMETER_USING, viewModel1.inputParameterUsing.value!!)


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
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_NORTH_DIRECTION_MOVE, viewBinding.etFourParameterNorthDirectionMove.text.toString())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_EAST_DIRECTION_MOVE, viewBinding.etFourParameterEastDirectionMove.text.toString())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_SCALE, viewBinding.etFourParameterScale.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_FAR_NORTH_DIRECTION_MOVE, viewBinding.etFourParameterFarNorthDirection.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_FOUR_PARAMETER_FAR_EAST_DIRECTION_MOVE, viewBinding.etFourParameterFarEastDirection.text.toString().isEmpty())

        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A0, viewBinding.etVerticalControlParameterA0.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A1, viewBinding.etVerticalControlParameterA1.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A2, viewBinding.etVerticalControlParameterA2.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A3, viewBinding.etVerticalControlParameterA3.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A4, viewBinding.etVerticalControlParameterA4.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_A5, viewBinding.etVerticalControlParameterA5.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_X0, viewBinding.etVerticalControlParameterX0.text.toString().isEmpty())
        PrefUtil.setDouble(applicationContext, Define.COORDINATE_VERTICAL_CONTROL_PARAMETER_Y0, viewBinding.etVerticalControlParameterY0.text.toString().isEmpty())

        PrefUtil.setInt(applicationContext, Define.COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_AdjustmentConstant, viewBinding.etVerticalAdjustmentParameterAdjustmentConstant.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_NorthSuperelevation, viewBinding.etVerticalAdjustmentParameterNorthSuperelevation.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_EastSuperelevation, viewBinding.etVerticalAdjustmentParameterEastSuperelevation.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_FarNorthDirection, viewBinding.etVerticalAdjustmentParameterFarNorthDirection.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_VERTICAL_ADJUSTMENT_PARAMETER_FarEastDirection, viewBinding.etVerticalAdjustmentParameterFarEastDirection.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_X, viewBinding.etInputParameterX.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_Y, viewBinding.etInputParameterY.text.toString().toInt())
        PrefUtil.setInt(applicationContext, Define.COORDINATE_INPUT_PARAMETER_INPUT_PARAMETER_LEVEL, viewBinding.etInputParameterLevel.text.toString().toInt())



    }


}