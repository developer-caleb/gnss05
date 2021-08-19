package kr.loplab.gnss05.activities.workmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding
import kr.loplab.gnss05.databinding.ActivityWorkManagerBinding
import kr.loplab.gnss05.tableview.TableMainActivity

class WorkManagerActivity : ActivityBase<ActivityWorkManagerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_work_manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btAdd.setOnClickListener {
            intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btEdit.setOnClickListener {
            intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btDelete.setOnClickListener {  }
        viewBinding.btConfirm.setOnClickListener {  }
    }

    override fun initDatabinding() {

    }
}