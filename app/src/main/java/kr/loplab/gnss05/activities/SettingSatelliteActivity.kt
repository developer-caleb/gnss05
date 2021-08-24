package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting_satellite.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivitySettingSatelliteBinding
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class SettingSatelliteActivity : ActivityBase<ActivitySettingSatelliteBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_setting_satellite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            Log.d(TAG, "initListener: confirmbt_clicked-> 저장하기")
            //save
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GPS , viewBinding.swGps.isChecked )
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GLONASS, viewBinding.swGlonass.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_BEIDOU, viewBinding.swBeidou.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GALILEO, viewBinding.swGalieo.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_SBAS, viewBinding.swSbas.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_QZSS, viewBinding.swQzss.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_LBAND, viewBinding.swLband.isChecked)
            


            onBackPressed()
        }
        viewBinding.layoutGps.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange(viewBinding.swGps)
        }
        viewBinding.layoutGlonass.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange(viewBinding.swGlonass)
        }
        viewBinding.layoutBeidou.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            viewBinding.swBeidou.isChecked =    !viewBinding.swBeidou.isChecked
        }
        viewBinding.layoutGalilieo.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            viewBinding.swGalieo.isChecked =    !viewBinding.swGalieo.isChecked
        }
        viewBinding.layoutSbas.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swSbas)
        }
        viewBinding.layoutQzss.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swQzss)
        }
        viewBinding.layoutLband.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swLband)
        }
        viewBinding.layoutCutAngle.setOnClickListener {
            Log.d(TAG, "initListener: cutangle")
        }
        viewBinding.layoutPdopLimit.setOnClickListener { Log.d(TAG, "initListener: pdoplimit") }
        viewBinding.layoutDelay.setOnClickListener { Log.d(TAG, "initListener: delay") }


    }
    fun swchange(sw : Switch){
        sw.isChecked = !sw.isChecked
        //sw.performClick()
    }
    fun swchange2(sw : Switch){
        // sw.isChecked = !sw.isChecked
        sw.performClick()
    }

    override fun initDatabinding() {
        viewBinding.swGps.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GPS)
        viewBinding.swGlonass.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GLONASS)
        viewBinding.swBeidou.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_BEIDOU)
        viewBinding.swGalieo.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GALILEO)
        viewBinding.swSbas.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_SBAS)
        viewBinding.swQzss.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_QZSS)
        viewBinding.swLband.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_LBAND)


    }
}