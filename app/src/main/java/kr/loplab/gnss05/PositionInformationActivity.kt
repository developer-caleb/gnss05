package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityExportBinding
import kr.loplab.gnss05.databinding.ActivityPositionInformationBinding

class PositionInformationActivity : ActivityBase<ActivityPositionInformationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_position_information
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