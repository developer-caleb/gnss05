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



    fun addNormal (x: Int, y: Int): Int {
        return x + y
    }



    override fun init() {
        TODO("Not yet implemented")
    }

    override fun initListener() {
        viewBinding.header03.setOnBackButtonClickListener {
            onBackPressed()
            println("hello")
        }
        viewBinding.textView8.text = " \uD83D\uDCC2\uD83D\uDCC1\uD83D\uDDC2  Textview"
    }

    override fun initDatabinding() {
        TODO("Not yet implemented")
    }
}