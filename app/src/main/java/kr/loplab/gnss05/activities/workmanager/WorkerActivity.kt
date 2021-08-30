package kr.loplab.gnss05.activities.workmanager

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.REQUEST_WORKER_MANAGE_ADD
import kr.loplab.gnss05.common.Define.REQUEST_WORKER_MANAGE_EDIT
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
            .fallbackToDestructiveMigration()
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
        viewBinding.layoutName.setOnClickListener {
            viewBinding.etName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etName,0)
        }
        viewBinding.layoutPassword.setOnClickListener { viewBinding.etPassword.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etPassword,0)
        }
        viewBinding.layoutWorker.setOnClickListener { viewBinding.etWorker.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etWorker,0)
        }
        viewBinding.layoutUser.setOnClickListener {viewBinding.etUser.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etUser,0)
        }

        viewBinding.btConfirm.setOnClickListener {
            if (viewBinding.etWorker.text.toString().isEmpty()){
                showToast("작업자 이름은 반드시 포함되어야 합니다.")
                return@setOnClickListener}
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
                 setResult(RESULT_OK)
                 finish()
             }
            else ->{
                Log.d(TAG, "initListener: requestcode else $requestCode")
            }

        }
        }
    }
    fun keyboardtoggle(){
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
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