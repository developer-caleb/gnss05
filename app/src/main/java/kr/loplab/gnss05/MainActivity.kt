package kr.loplab.gnss05;

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.chc.gnss.sdk.*
import com.google.android.material.tabs.TabLayout
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd
import com.huace.gnssserver.gnss.data.receiver.PositionInfo
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.activities.ConnectEquipmentActivity
import kr.loplab.gnss05.activities.viewmodel.ConnectEquipmentViewModel
import kr.loplab.gnss05.activities.viewmodel.MainActivityViewModel
import kr.loplab.gnss05.adapter.MainAdapterViewpager
import kr.loplab.gnss05.adapter.DialogRecyclerviewAdapter
import kr.loplab.gnss05.common.Define.REQUEST_SETTING
import kr.loplab.gnss05.connection.ConnectManager
import kr.loplab.gnss05.connection.ConnectionStatus
import kr.loplab.gnss05.databinding.ActivityMainBinding
import kr.loplab.gnss05.enums.ConnectType
import kr.loplab.gnss05.net.RoverNetActivity
import kr.loplab.gnss05.receiver.ReceiverService
import kr.loplab.gnss05.receiver.entity.ReceiverAsw
import java.io.*
import java.lang.Exception
import java.util.ArrayList
import kotlin.concurrent.thread

class MainActivity :  ActivityBase<ActivityMainBinding>(),
     DialogRecyclerviewAdapter.RecyclerItemClickListener , ConnectManager.ConnectStateChangeListener{
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    var adapterViewpager: MainAdapterViewpager? = null
    lateinit var viewModel1: MainActivityViewModel


    var tabposition =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_qservice);

    }




    override fun init(){
        viewModel1 = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewBinding.viewmodel = viewModel1
        tablayoutinitialize()
        //setContentView(R.layout.activity_main)
        permissionchecking()
        // set up the RecyclerView
        adapterViewpager = MainAdapterViewpager(this, viewModel1.connect_type.value!!)
        viewBinding.pager1.adapter = adapterViewpager

    }


    override fun initListener(){
        viewBinding.pager1.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {
                tabposition = viewBinding.pager1.currentItem
                tabs.selectTab(tabs.getTabAt(viewBinding.pager1.currentItem))
            }
        })

        viewBinding.imageHamburger.setOnClickListener { Log.d(TAG, "onCreate: hamburger")
            intent = Intent(this, RoverNetActivity::class.java)
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_enter_fill_after, R.anim.hold)
        }
        viewBinding.btReceiver.setOnClickListener {
            Log.d(TAG, "onCreate: receiver click")
            intent = Intent(this, ConnectEquipmentActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btSatellite.setOnClickListener {
            Log.d(TAG, "onCreate: satellite click")
            if(!ConnectManager.instance!!.isConnected ){
               Log.e(TAG, "onItemClick: 장비 연결 후 시도", )
               showToast("장비 연결 후 시도해주세요."); return@setOnClickListener;
            }
            intent = Intent(this, PositionInformationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.logoImg.setOnClickListener {
            Log.d(TAG, "onCreate: logo click")
        }
        viewModel1.connection_state.observe(this, {
            viewChanger()
        })

    }

    override fun initDatabinding(){
    viewModel1.setConnectionState(ConnectManager.instance!!.connectionStatus)
        viewChanger()
    }
    fun tablayoutinitialize() {
        //custom tab 구현시작
        val tabtext0 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext0.text = "작업"
        tabtext0.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab1, 0, 0)
        val tabtext1 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext1.text = "연결"
        tabtext1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab2, 0, 0)
        /*val tabtext2 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext2.text = "위치"
        tabtext2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_location, 0, 0)*/
        val tabtext3 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext3.text = "측정"
        tabtext3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab3, 0, 0)
        val tabtext4 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext4.text = "도구"
        tabtext4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab4, 0, 0)
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext0))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext1))
        //binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext2))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext3))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext4))

        //custom tab 완성
        viewBinding.tabs.getTabAt(0)!!.select()

        viewBinding.tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
               // tab.view.setBackgroundColor(resources.getColor(R.color.black))
                selecttab(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
              //  tab.view.setBackgroundColor(resources.getColor(R.color.blue))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
              //  tab.view.setBackgroundColor(resources.getColor(R.color.black))
            }

        })
    }


   fun selecttab(position : Int){
       tabposition = position;
       if (viewBinding.pager1.currentItem != position) {viewBinding.pager1.currentItem = position}
   }





    override fun onItemClickDialog(view: View?, position: Int) {
        Log.d(TAG, "onItemClick2: prrr $position")
        Log.d(TAG, "onClick: 메인액티비티에서 부른 logd+ $position")
    }
    fun permissionchecking(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withActivity(this)
                .withPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,

                ) // 위치
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) { // 권한 여부를 다 묻고 실행되는 메소드
                        // check if all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                          showToast("권한 허용 완료")
                        }
                        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                            showToast("권한 재확인")
                            permissionchecking()
                        }
                    } // onPermissionsChecked()..

                    override fun onPermissionRationaleShouldBeShown(
                        list: List<PermissionRequest?>,
                        permissionToken: PermissionToken?
                    ) { // 이전 권한 여부를 거부한 권한이 있으면 실행되는 메소드
                      /*  Toast.makeText(this@MainActivity, "list : $list", Toast.LENGTH_LONG)
                            .show() // 거부한 권한 이름이 저장된 list*/
                        showSettingsDialog() // 권한 거부시 앱 정보 설정 페이지를 띄우기 위한 임의 메소드
                    } // onPermissionRationaleShouldBeShown()..
                })
                .check()
        }
    }

    // 만약 권한을 거절했을 경우,  다이얼로그 띄우기 위한 임의 메소드
    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("권한 허용을 하셔야합니다.")
            builder.setMessage("거부 된 기능은 설정에서 권한 허용 가능합니다.")
            builder.setPositiveButton("설정",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    openSettings() // 어플리케이션 정보 설정 페이지 띄움.
                })
            builder.setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
    } // showSettingsDialog()..


    // 어플리케이션 정보 설정 페이지
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    } // openSettings()..


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult:")
        if (resultCode== Activity.RESULT_OK && requestCode==REQUEST_SETTING){
            Log.d(TAG, "onActivityResult: ->REQUEST_SETTING")
            adapterViewpager = MainAdapterViewpager(this, viewModel1.connect_type.value!!)
            viewBinding.pager1.adapter = adapterViewpager
           // adapterViewpager?.init(tabposition) //변경은 되는데 viewpager에서 변경이 안 됨. 다시 만들어져야함
           // adapterViewpager?.notifyDataSetChanged()

        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        ConnectManager.instance!!.setOnConnectStateChangeListener(this)
        initDatabinding()
    }

    override fun onConnectStateChange(connectionStatus: ConnectionStatus) {
        Log.d(TAG, "onConnectStateChange: 커넥션스테이트 ! 2 -> ${connectionStatus.name}")
        viewModel1.setConnectionState(connectionStatus)
        Log.d(TAG, "initListener: ${viewModel1.connection_state}")
        when (connectionStatus){
            ConnectionStatus.DISCONNECT -> {showToast("장비 연결이 끊겼습니다.")}
            ConnectionStatus.CONNECTTNG -> {}
            ConnectionStatus.CONNECTED -> {}
            ConnectionStatus.CONNECT_FAILD -> {showToast("장비 연결이 끊겼습니다.")}
        }
    }

    fun viewChanger(){
        Log.d(TAG, "viewChanger: viewchanage~\n" +
                " viewModel1.connection_state : ${viewModel1.connection_state.value}, viewModel1.connect_type : ${viewModel1.connect_type.value}")
        if (viewModel1.connection_state.value != ConnectionStatus.CONNECTED) {
            viewModel1.setConnectionType(ConnectType.DISCONNECTED)
        } //뷰를 모두 type1으로 바꾸고 return;
        adapterViewpager!!.connectType(viewModel1.connect_type.value!!);
    }


    override fun onStop() {
        super.onStop()
    }
}