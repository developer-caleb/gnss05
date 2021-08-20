package kr.loplab.gnss05.activities.workmanager

import android.content.Intent
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
import kr.loplab.gnss05.common.Define.REQUEST_WORKER_MANAGE_ADD
import kr.loplab.gnss05.common.Define.REQUEST_WORKER_MANAGE_EDIT
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding
import kr.loplab.gnss05.databinding.ActivityWorkerBinding
import java.lang.Exception

class WorkerActivity :  ActivityBase<ActivityWorkerBinding>()  {
    override val layoutResourceId: Int
        get() = R.layout.activity_worker
    lateinit var db : AppDatabase
    var requestCode= 0;
    var selectedPosition = -1;
     var workerslist : MutableList<Worker>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, Define.WORKERS_DB)
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .build()

        if (intent.hasExtra(Define.REQUEST_CODE_STRING) )
        {
            requestCode = intent.getIntExtra(Define.REQUEST_CODE_STRING,0)
            Log.d(TAG, "init: requestcode $requestCode")
        }
        if (intent.hasExtra("selectPosition") )
        {
            selectedPosition = intent.getIntExtra("selectPosition",0)
            Log.d(TAG, "init: selectedPosition $selectedPosition")
        }
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            when(requestCode){
                REQUEST_WORKER_MANAGE_ADD->{
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d(TAG, "initListener: bt confirm")
                db.workerDao().insert(
                    Worker(viewBinding.etWorker.text.toString(),
                    viewBinding.etName.text.toString(),
                    viewBinding.etUser.text.toString(),
                    viewBinding.etPassword.text.toString()))
            }
            setResult(RESULT_OK)
            finish()}
             REQUEST_WORKER_MANAGE_EDIT ->{
                 Log.d(TAG, "initListener: edit! confirm")
                 lifecycleScope.launch(Dispatchers.IO) {
                     var workermodel =  db.workerDao().all[selectedPosition]
                     workermodel.worker = viewBinding.etWorker.text.toString()
                     workermodel.name = viewBinding.etName.text.toString()
                     workermodel.user = viewBinding.etUser.text.toString()
                     workermodel.password = viewBinding.etPassword.text.toString()
                     db.workerDao().update(workermodel)
                 }
             }
            else ->{
                Log.d(TAG, "initListener: requestcode else $requestCode")
            }

        }
        }
    }

    override fun initDatabinding() {
        if(requestCode == REQUEST_WORKER_MANAGE_EDIT) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    workerslist = db.workerDao().all
                    viewBinding.etWorker.setText(workerslist!![selectedPosition].worker)
                    viewBinding.etName.setText(workerslist!![selectedPosition].name)
                    viewBinding.etUser.setText(workerslist!![selectedPosition].user)
                    viewBinding.etPassword.setText(workerslist!![selectedPosition].password)
                }catch (e:Exception){
                    Log.e(TAG, "initDatabinding: ", e )
                }
            }
        }
    }

}