package kr.loplab.gnss05.activities.coordinate

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.CoordinateViewModel
import kr.loplab.gnss05.activities.viewmodel.ReferenceCountryViewModel
import kr.loplab.gnss05.databinding.ActivityCoordinateBinding

class CoordinateActivity : ActivityBase<ActivityCoordinateBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_coordinate
    lateinit var viewModel1:CoordinateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(CoordinateViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
    }

    override fun initDatabinding() {

    }
}