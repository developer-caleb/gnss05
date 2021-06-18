package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityFileExportBinding

class FileExportActivity : ActivityBase<ActivityFileExportBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_file_export

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header03.setOnBackButtonClickListener {
            onBackPressed()
            println("hello")
        }
        viewBinding.textView8.text = "\uD83D\uDDC2    Textview"
    }

    override fun initDatabinding() {

    }
}