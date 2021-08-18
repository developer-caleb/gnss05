package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_reference_coordinate_setting.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityMobileStationBinding
import kr.loplab.gnss05.databinding.ActivityReferenceCoordinateSettingBinding

class ReferenceCoordinateSetting : ActivityBase<ActivityReferenceCoordinateSettingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_coordinate_setting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.nameLayout.setOnClickListener {    viewBinding.tvName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.tvName,0) }
        viewBinding.codeLayout.setOnClickListener {    viewBinding.tvCode.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.tvCode,0) }
        viewBinding.btSave.setOnClickListener {
            savepref()
            finish()
        }
        viewBinding.btNotice.setOnClickListener {
            finish()
        }
    }

    override fun initDatabinding() {

    }
    private fun savepref(){
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_NAME, viewBinding.tvName.text.toString());
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_CODE, viewBinding.tvCode.text.toString());
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_COORDINATE_TYPE, viewBinding.tvCoordinateType.text.toString());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_X, viewBinding.tvX.text.toString().toFloat())
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_Y, viewBinding.tvY.text.toString().toFloat());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_LEVEL, viewBinding.tvLevel.toString().toFloat());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_POLE_HEIGHT, viewBinding.tvPoleHeight.toString().toFloat());
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_HEIGHT_CALCULATION, viewBinding.tvHeightCalc.text.toString());
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_CALCULATED_HEIGHT, viewBinding.tvCalculatedHeight.text.toString());
        PrefUtil.setString(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_ANTENNA_TYPE, viewBinding.tvAntennaType.text.toString());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_R, viewBinding.tvR.text.toString().toFloat())
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_H, viewBinding.tvH.text.toString().toFloat());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_HL1, viewBinding.tvHl1.toString().toFloat());
        PrefUtil.setFloat(applicationContext, REFERENCE_COUNTRY_COORDINATE_SETTING_HL2, viewBinding.tvHl2.toString().toFloat());

        //Log.d(TAG, "savepref: ${viewBinding.tvName.text.toString()}")
    }
}