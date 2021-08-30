package kr.loplab.gnss05.activities.stop_survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.mobile_station.MobileStationSettingSatelliteActivity
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class StopSurveyActivity : ActivityBase<ActivityStopSurveyBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_stop_survey
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.settingSatelliteBt.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, MobileStationSettingSatelliteActivity::class.java)
            intent.putExtra("StopMode", true)
            startActivity(intent);

        }
        viewBinding.saveAndApplyBt.setOnClickListener {  }
        viewBinding.applyBt.setOnClickListener {  }
    }

    override fun initDatabinding() {

    }
}