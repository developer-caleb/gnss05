package kr.loplab.gnss05.activities

import android.content.Intent
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityExportBinding
import kr.loplab.gnss05.tableview.TableMainActivity

class ExportActivity :  ActivityBase<ActivityExportBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_export
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //UserFormatMake

    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.btAddFileFormat.setOnClickListener {
            intent = Intent(this, TableMainActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btExport.setOnClickListener {
            intent = Intent(this, FileExportActivity::class.java)
            startActivity(intent);
        }
    }

    override fun initDatabinding() {

    }
}