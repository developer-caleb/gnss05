package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
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
    }

    override fun initDatabinding() {

    }
}