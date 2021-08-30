package kr.loplab.gnss05.activities.cors_servermanager

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
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityServerAddressAddBinding
import java.lang.Exception

class ServerAddressAddActivity : ActivityBase<ActivityServerAddressAddBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_server_address_add
    lateinit var db : AppDatabase
    var requestCode= 0;
    var selectedPosition = -1;
    var serverslist : MutableList<Server>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, Define.SERVERS_DB)
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
        viewBinding.layoutIp.setOnClickListener {
            viewBinding.etIp.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etIp,0)
        }
        viewBinding.layoutPort.setOnClickListener { viewBinding.etPort.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etPort,0)
        }
        viewBinding.layoutUser.setOnClickListener {viewBinding.etUser.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etUser,0)
        }
        viewBinding.layoutPassword.setOnClickListener { viewBinding.etPassword.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etPassword,0)
        }



        viewBinding.btConfirm.setOnClickListener {
            if (viewBinding.etName.text.toString().isEmpty()){
                showToast("작업자 이름은 반드시 포함되어야 합니다.")
                return@setOnClickListener}
            when(requestCode){
                Define.REQUEST_SERVER_MANAGE_ADD ->{
                    lifecycleScope.launch(Dispatchers.IO) {
                        Log.d(TAG, "initListener: bt confirm")
                        db.serverDao().insert(
                            Server(
                                viewBinding.etName.text.toString(),
                                viewBinding.etIp.text.toString(),
                                viewBinding.etPort.text.toString(),
                                viewBinding.etUser.text.toString(),
                                viewBinding.etPassword.text.toString()))
                    }
                    setResult(RESULT_OK)
                    finish()
                }
                Define.REQUEST_SERVER_MANAGE_EDIT ->{
                    Log.d(TAG, "initListener: edit! confirm")
                    lifecycleScope.launch(Dispatchers.IO) {
                        var servermodel =  db.serverDao().all[selectedPosition]
                        servermodel.name = viewBinding.etName.text.toString()
                        servermodel.ip= viewBinding.etIp.text.toString()
                        servermodel.port= viewBinding.etPort.text.toString()
                        servermodel.user = viewBinding.etUser.text.toString()
                        servermodel.password = viewBinding.etPassword.text.toString()
                        db.serverDao().update(servermodel)
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

    override fun initDatabinding() {
        if(requestCode == Define.REQUEST_SERVER_MANAGE_EDIT) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    serverslist = db.serverDao().all
                    viewBinding.etName.setText(serverslist!![selectedPosition].name)
                    viewBinding.etIp.setText(serverslist!![selectedPosition].ip)
                    viewBinding.etPort.setText(serverslist!![selectedPosition].port)
                    viewBinding.etUser.setText(serverslist!![selectedPosition].user)
                    viewBinding.etPassword.setText(serverslist!![selectedPosition].password)
                }catch (e: Exception){
                    Log.e(TAG, "initDatabinding: ", e )
                }
            }
        }
    }
}