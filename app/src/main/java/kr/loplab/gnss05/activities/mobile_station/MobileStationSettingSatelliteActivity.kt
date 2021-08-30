package kr.loplab.gnss05.activities.mobile_station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Switch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityMobileStationSettingSatelliteBinding
import kr.loplab.gnss05.databinding.ActivityReferenceCountrySettingSatelliteBinding

class MobileStationSettingSatelliteActivity : ActivityBase<ActivityMobileStationSettingSatelliteBinding>(){
    override val layoutResourceId: Int
        get() = R.layout.activity_mobile_station_setting_satellite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            if(intent.getBooleanExtra("StopMode", false))
            Log.d(TAG, "initListener: confirmbt_clicked-> 저장하기")
            //save
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_GPS, viewBinding.swGps.isChecked )
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_GLONASS, viewBinding.swGlonass.isChecked)
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_BEIDOU, viewBinding.swBeidou.isChecked)
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_GALILEO, viewBinding.swGalieo.isChecked)
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_SBAS, viewBinding.swSbas.isChecked)
            PrefUtil.setBoolean(this, Define.MOBILE_STATION_SATELLITE_QZSS, viewBinding.swQzss.isChecked)
            onBackPressed()
        }

        //스위치
        viewBinding.layoutGps.setOnClickListener {
            swchange(viewBinding.swGps)
        }
        viewBinding.layoutGlonass.setOnClickListener {
            swchange(viewBinding.swGlonass)
        }
        viewBinding.layoutBeidou.setOnClickListener {
            viewBinding.swBeidou.isChecked =    !viewBinding.swBeidou.isChecked
        }
        viewBinding.layoutGalilieo.setOnClickListener {
            viewBinding.swGalieo.isChecked =    !viewBinding.swGalieo.isChecked
        }
        viewBinding.layoutSbas.setOnClickListener {
            swchange2(viewBinding.swSbas)
        }
        viewBinding.layoutQzss.setOnClickListener {
            swchange2(viewBinding.swQzss)
        }


    }

    override fun initDatabinding() {
        viewBinding.swGps.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_GPS)
        viewBinding.swGlonass.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_GLONASS)
        viewBinding.swBeidou.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_BEIDOU)
        viewBinding.swGalieo.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_GALILEO)
        viewBinding.swSbas.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_SBAS)
        viewBinding.swQzss.isChecked = PrefUtil.getBoolean(this, Define.MOBILE_STATION_SATELLITE_QZSS)
    }
    fun swchange(sw : Switch){
        sw.isChecked = !sw.isChecked
        //sw.performClick()
    }
    fun swchange2(sw : Switch){
        // sw.isChecked = !sw.isChecked
        sw.performClick()
    }
}