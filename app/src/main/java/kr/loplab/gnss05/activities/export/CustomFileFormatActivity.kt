package kr.loplab.gnss05.activities.export

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityCustomFileFormatBinding
import kr.loplab.gnss05.databinding.ActivityWorkManagerBinding

class CustomFileFormatActivity : ActivityBase<ActivityCustomFileFormatBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_custom_file_format
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