package kr.loplab.gnss05.activities.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding
import kr.loplab.gnss05.databinding.ActivityWorkerBinding

class WorkerActivity :  ActivityBase<ActivityWorkerBinding>()  {
    override val layoutResourceId: Int
        get() = R.layout.activity_worker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        var db : AppDatabase = Room.databaseBuilder(this, AppDatabase::class.java, Define.WORKERS_DB)
            .allowMainThreadQueries()
            .build()
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
    }

    override fun initDatabinding() {

    }
}