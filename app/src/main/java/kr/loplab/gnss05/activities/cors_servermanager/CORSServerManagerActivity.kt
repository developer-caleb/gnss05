package kr.loplab.gnss05.activities.cors_servermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityCorsServerManagerBinding
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class CORSServerManagerActivity : ActivityBase<ActivityCorsServerManagerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_cors_server_manager
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