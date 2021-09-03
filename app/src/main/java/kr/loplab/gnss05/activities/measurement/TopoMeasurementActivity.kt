package kr.loplab.gnss05.activities.measurement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityStatusWorkBinding
import kr.loplab.gnss05.databinding.ActivityTopoMeasurementBinding

class TopoMeasurementActivity : ActivityBase<ActivityTopoMeasurementBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_topo_measurement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.layoutName.setOnClickListener {
            requestETfocus(viewBinding.etName)
        }
    }
    override fun initDatabinding() {

    }
}