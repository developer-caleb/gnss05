package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivitySettingSatelliteBinding
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class SettingSatelliteActivity : ActivityBase<ActivitySettingSatelliteBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_setting_satellite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_satellite)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            Log.d(TAG, "initListener: confirmbt_clicked")
        }

    }

    override fun initDatabinding() {

    }
}