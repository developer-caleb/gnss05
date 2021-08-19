package kr.loplab.gnss05.activities.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_worker.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding
import kr.loplab.gnss05.databinding.ActivityWorkerBinding

class WorkerActivity :  ActivityBase<ActivityWorkerBinding>()  {
    override val layoutResourceId: Int
        get() = R.layout.activity_worker
    lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, Define.WORKERS_DB)
            //.allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .build()
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d(TAG, "initListener: bt confirm")
                db.workerDao().insert(
                    Worker(viewBinding.etWorker.text.toString(),
                    viewBinding.etWorker.text.toString(),
                    viewBinding.etUser.text.toString(),
                    viewBinding.etPassword.text.toString()))
            }
        }
    }

    override fun initDatabinding() {

    }
}