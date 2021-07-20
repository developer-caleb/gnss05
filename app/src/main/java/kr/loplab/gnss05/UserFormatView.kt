package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding
import kr.loplab.gnss05.databinding.ActivityUserFormatViewBinding

class UserFormatView : ActivityBase<ActivityUserFormatViewBinding>(){
    override val layoutResourceId: Int
        get() = R.layout.activity_user_format_view
    private var numberOfColumn = 4
    private var numberOfRow = 3

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header05.setOnBackButtonClickListener{onBackPressed()}
    }

    override fun initDatabinding() {
    }
}