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
        adapterViewpager = MainAdapterViewpager(this)
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
            intent = Intent(this, HamburgerActivity::class.java)
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
            intent = Intent(this, PositionInformationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.logoImg.setOnClickListener {
            Log.d(TAG, "onCreate: logo click")
        }
        viewModel1.connection_state.observe(this, {
            viewChanger()
        })
        viewModel1.connect_type.observe(this, {viewChanger()})
    }

    override fun initDatabinding(){
    viewModel1.setConnectionState(ConnectManager.instance!!.connectionStatus)
    }
    fun tablayoutinitialize() {
        //custom tab ????????????
        val tabtext0 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext0.text = "??????"
        tabtext0.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab1, 0, 0)
        val tabtext1 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext1.text = "??????"
        tabtext1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab2, 0, 0)
        /*val tabtext2 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext2.text = "??????"
        tabtext2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_location, 0, 0)*/
        val tabtext3 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext3.text = "??????"
        tabtext3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab3, 0, 0)
        val tabtext4 = LayoutInflater.from(this).inflate(R.layout.tool_new_customtabtextview, null) as TextView
        tabtext4.text = "??????"
        tabtext4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab4, 0, 0)
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext0))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext1))
        //binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext2))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext3))
        viewBinding.tabs.addTab(viewBinding.tabs.newTab().setCustomView(tabtext4))

        //custom tab ??????
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
        Log.d(TAG, "onClick: ???????????????????????? ?????? logd+ $position")
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

                ) // ??????
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) { // ?????? ????????? ??? ?????? ???????????? ?????????
                        // check if all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(this@MainActivity, "?????? ?????? ??????", Toast.LENGTH_SHORT).show()
                        }
                        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(this@MainActivity, "?????? ?????????", Toast.LENGTH_SHORT).show()
                            permissionchecking()
                        }
                    } // onPermissionsChecked()..

                    override fun onPermissionRationaleShouldBeShown(
                        list: List<PermissionRequest?>,
                        permissionToken: PermissionToken?
                    ) { // ?????? ?????? ????????? ????????? ????????? ????????? ???????????? ?????????
                      /*  Toast.makeText(this@MainActivity, "list : $list", Toast.LENGTH_LONG)
                            .show() // ????????? ?????? ????????? ????????? list*/
                        showSettingsDialog() // ?????? ????????? ??? ?????? ?????? ???????????? ????????? ?????? ?????? ?????????
                    } // onPermissionRationaleShouldBeShown()..
                })
                .check()
        }
    }

    // ?????? ????????? ???????????? ??????,  ??????????????? ????????? ?????? ?????? ?????????
    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("?????? ????????? ??????????????????.")
        builder.setMessage("?????? ??? ????????? ???????????? ?????? ?????? ???????????????.")
        builder.setPositiveButton("??????",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                openSettings() // ?????????????????? ?????? ?????? ????????? ??????.
            })
        builder.setNegativeButton("??????",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    } // showSettingsDialog()..


    // ?????????????????? ?????? ?????? ?????????
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    } // openSettings()..



    inner class Gnssreceiver3  {
        var filepath = ""
        var receiveref: CHC_ReceiverRef? = null
        var cmdRef: CHC_CMDRef? = null

        // CHC_Receiver = null;
        // CHC_Receiver receiver = new CHC_Receiver();
        fun initialize() {
            val chc_receiver_type = CHC_RECEIVER_TYPE.CHC_RECEIVER_TYPE_SMART_GNSS //3
            // The absolute path of the 'features.hcc'
            // CHC_OEM_TYPE oem_type = CHC_OEM_TYPE.CHC_OEM_TYPE_UNICORE;
            val oem_type = CHC_OEM_TYPE.CHC_OEM_TYPE_AUTO //?????????

            //config??? ?????? ?????? ????????????, resource??? ?????????.
            receiveref = CHC_ReceiverRef(filepath, chc_receiver_type, oem_type)
            val method = CHC_CONNECTION_METHOD.CHC_CONNECTION_METHOD_BT //4
            CHC_Receiver.CHCUpdateConnectionMethod(receiveref, method) //5
            cmdRef = CHC_CMDRef()
            CHC_Receiver.CHCGetCmdInitConnection(receiveref, cmdRef)
        }


        //?????? ?????? ??????
        fun openrawfile(): String? {
            val inputStream: InputStream = getResources().openRawResource(R.raw.features)
            val byteArrayOutputStream = ByteArrayOutputStream()
            var i: Int
            try {
                i = inputStream.read()
                while (i != -1) {
                    byteArrayOutputStream.write(i)
                    i = inputStream.read()
                }
                inputStream.close()
            } catch (e: IOException) {
                return null
            }
            return byteArrayOutputStream.toString()
        }

        fun internalfilewrite() {
            try {
                val fos: FileOutputStream = openFileOutput("features.hcc", MODE_PRIVATE)
                val dos = DataOutputStream(fos)
                dos.writeUTF(openrawfile())
                dos.flush()
                dos.close()
            } catch (e: Exception) {
                Log.e(TAG, "internalfileopenwrite: error $e")
            }
        }

        fun internalfileread(): String {
            var returnstr = ""
            try {
                val file: File = getFileStreamPath("features.hcc")
                val br = BufferedReader(FileReader(file))
                var line: String
                try{
                while (br.readLine().also { line = it } != null) {
                    Log.d(TAG, "internalfileread:--- $line")
                }} catch (e1 : Exception) {
                    Log.e(TAG, "internalfileread:---> $e1")
                }
                br.close()
                Log.d(TAG, "internalfileread: filepath-> " + file.absolutePath)
                Log.d(TAG, "internalfileread: file.tostring$file")

                //FileInputStream fis = openFileInput(file.getAbsolutePath());
                val fis: FileInputStream = openFileInput("features.hcc")
                val dis = DataInputStream(fis)
                Log.d(TAG, "internalfileread: -??????" + dis.readUTF())
                val path: String = getFileStreamPath("features.hcc").absolutePath
                Log.d(TAG, "internalfileread:->>> $path")
                returnstr = file.absolutePath
            } catch (e: Exception) {
                Log.e(TAG, "internalfileread: $e")
            }
            return returnstr
        }

        init {
            internalfilewrite()
            val receiver: CHC_Receiver? = null //1
            filepath = internalfileread() //2
            //initialize()
            //processdata();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult:")
        if (resultCode== Activity.RESULT_OK && requestCode==REQUEST_SETTING){
            Log.d(TAG, "onActivityResult: ->REQUEST_SETTING")
            adapterViewpager = MainAdapterViewpager(this)
            viewBinding.pager1.adapter = adapterViewpager
        // adapterViewpager?.init(tabposition) //????????? ????????? viewpager?????? ????????? ??? ???. ?????? ??????????????????
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
        Log.d(TAG, "onConnectStateChange: ????????????????????? ! 2 -> ${connectionStatus.name}")
        viewModel1.setConnectionState(connectionStatus)
        Log.d(TAG, "initListener: ${viewModel1.connection_state}")
        when (connectionStatus){
            ConnectionStatus.DISCONNECT -> {showToast("?????? ????????? ???????????????.")}
            ConnectionStatus.CONNECTTNG -> {}
            ConnectionStatus.CONNECTED -> {}
            ConnectionStatus.CONNECT_FAILD -> {showToast("?????? ????????? ???????????????.")}
        }
    }

    fun viewChanger(){
        Log.d(TAG, "viewChanger: viewchanage~\n" +
                " viewModel1.connection_state : ${viewModel1.connection_state.value}, viewModel1.connect_type : ${viewModel1.connect_type.value}")
        if (viewModel1.connection_state.value != ConnectionStatus.CONNECTED) {
            adapterViewpager!!.connectType(0); return
        } //?????? ?????? type1?????? ????????? return;
        when(viewModel1.connect_type.value){
            ConnectType.MOBILE_STATION -> {
                adapterViewpager!!.connectType(1); return
            }
            ConnectType.REFERENCE_COUNTRY -> {
                adapterViewpager!!.connectType(2); return
            }
            ConnectType.STOP_SURVEY -> {
                adapterViewpager!!.connectType(3); return
            }
        }
    }


    override fun onStop() {

        super.onStop()
    }
}



