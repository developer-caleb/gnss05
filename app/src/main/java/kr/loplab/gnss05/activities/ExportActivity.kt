package kr.loplab.gnss05.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.UserFormatMake
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding
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