package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding
import kr.loplab.gnss05.databinding.ActivityMobileStationBinding

class MobileStationActivity : ActivityBase<ActivityMobileStationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_mobile_station
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