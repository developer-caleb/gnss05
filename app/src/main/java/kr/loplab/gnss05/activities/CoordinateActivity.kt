package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding
import kr.loplab.gnss05.databinding.ActivityCoordinateBinding

class CoordinateActivity : ActivityBase<ActivityCoordinateBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_coordinate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
    }

    override fun initDatabinding() {

    }
}