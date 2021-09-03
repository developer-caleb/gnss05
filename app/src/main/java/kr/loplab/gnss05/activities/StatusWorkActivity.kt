package kr.loplab.gnss05.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.mobile_station.MobileStationActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationSettingSatelliteActivity
import kr.loplab.gnss05.databinding.ActivitySettingBinding
import kr.loplab.gnss05.databinding.ActivityStatusWorkBinding

class StatusWorkActivity : ActivityBase<ActivityStatusWorkBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_status_work

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
    }

    override fun initListener() {
        viewBinding.btBack.setOnClickListener {
            onBackPressed()
        }

        viewBinding.btMobilestation.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, MobileStationActivity::class.java)
            startActivity(intent);
        }
    }

    override fun initDatabinding() {
    }
}