package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityStandardPointBinding
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding

class UserFormat : ActivityBase<ActivityUserFormatBinding>()  {
    override val layoutResourceId: Int
        get() = R.layout.activity_user_format
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun init() {
     //
    }

    override fun initListener() {
      //
        viewBinding.header04.setOnBackButtonClickListener{onBackPressed()}
    }

    override fun initDatabinding() {
       //
    }
}