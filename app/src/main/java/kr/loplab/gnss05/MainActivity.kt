package kr.loplab.gnss05;

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.chc.gnss.sdk.*
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss05.SplashActivity.Gnssreceiver2
import kr.loplab.gnss05.adapter.AdapterViewpager
import kr.loplab.gnss05.common.Define.REQUEST_SETTING
import kr.loplab.gnss05.databinding.ActivityMainBinding
import kr.loplab.gnss05.model.MainIcons
import kr.loplab.gnss05.tableview.TableMainActivity
import java.io.*
import java.lang.Exception
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(),
     DialogRecyclerviewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    private lateinit var binding : ActivityMainBinding
    var adapterViewpager: AdapterViewpager? = null


    var tabposition =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_qservice);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initDefault()
        init()
        initListener()
        initDatabinding()


    }


    fun initDefault(){
        tablayoutinitialize()
        //setContentView(R.layout.activity_main)
        permissionchecking()
        // set up the RecyclerView

    }

    fun init(){
        adapterViewpager = AdapterViewpager(this)
        binding.pager1.adapter = adapterViewpager

    }

    fun initListener(){
        binding.pager1.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {
                tabposition = binding.pager1.currentItem
                tabs.selectTab(tabs.getTabAt(binding.pager1.currentItem))
            }
        })

        binding.imageHamburger.setOnClickListener { Log.d(TAG, "onCreate: hamburger")
            intent = Intent(this, HamburgerActivity::class.java)
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_enter_fill_after, R.anim.hold)
        }

        binding.btReceiver.setOnClickListener {
            Log.d(TAG, "onCreate: receiver click")
        }
        binding.btSatellite.setOnClickListener {
            Log.d(TAG, "onCreate: satellite click")
        }
        binding.logoImg.setOnClickListener {
            Log.d(TAG, "onCreate: logo click")
        }
    }

    fun initDatabinding(){

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
        binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext0))
        binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext1))
        //binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext2))
        binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext3))
        binding.tabs.addTab(binding.tabs.newTab().setCustomView(tabtext4))

        //custom tab 완성
        binding.tabs.getTabAt(0)!!.select()
        binding.tabs.getTabAt(0)!!.view.setBackgroundColor(resources.getColor(R.color.black))

        binding.tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.setBackgroundColor(resources.getColor(R.color.black))
                selecttab(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundColor(resources.getColor(R.color.blue))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundColor(resources.getColor(R.color.black))
            }

        })
    }


   fun selecttab(position : Int){
       tabposition = position;
       if (binding.pager1.currentItem != position) {binding.pager1.currentItem = position}
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

                ) // 위치
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) { // 권한 여부를 다 묻고 실행되는 메소드
                        // check if all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(this@MainActivity, "권한 허용 완료", Toast.LENGTH_SHORT).show()
                        }
                        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(this@MainActivity, "권한 재확인", Toast.LENGTH_SHORT).show()
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


    fun showToast(str:String){
        if(GlobalApplication.mToast!=null){
            GlobalApplication.mToast.cancel();
        }
        GlobalApplication.mToast = Toast.makeText(baseContext,str,Toast.LENGTH_SHORT)
        GlobalApplication.mToast.show();
        Log.d("TAG", "showToast: $str")
    }

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
            val oem_type = CHC_OEM_TYPE.CHC_OEM_TYPE_AUTO //안되면

            //config에 있는 파일 넣어놓고, resource로 부르기.
            receiveref = CHC_ReceiverRef(filepath, chc_receiver_type, oem_type)
            val method = CHC_CONNECTION_METHOD.CHC_CONNECTION_METHOD_BT //4
            CHC_Receiver.CHCUpdateConnectionMethod(receiveref, method) //5
            cmdRef = CHC_CMDRef()
            CHC_Receiver.CHCGetCmdInitConnection(receiveref, cmdRef)
        }

        fun processdata() {
            Log.d(TAG, "processdata: --> test")
            thread {
                var i =0;
                while (CHC_Receiver.CHCParseData(receiveref) == 0 && i < 50) {
                    Log.d(TAG, "processdata: 시도횟수 : $i")
                    Log.d(TAG, "CHC_Receiver.CHCParseData(receiveref): " + CHC_Receiver.CHCParseData(receiveref))
                    val info = CHC_MessageInfo()
                    CHC_Receiver.CHCGetMessageInfo(receiveref, info)
                    Log.d(TAG, "processdata: ${info.msgType.toString()}")
                  /*  when (info.msgType) {
                        CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_SYSTEM -> {
                            Log.d(TAG, "processdata: CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_SYSTEM")
                            //Process system data
                            //onSystemInfo(info.ulmsg)
                        }
                        CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_GNSS -> {
                            Log.d(TAG, "processdata: CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_GNSS")
                            //Process GNSS OEM board data
                            //onGNSSInfo(info.ulmsg)
                        }
                        CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_PDA -> {
                            Log.d(TAG, "processdata: CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_PDA")
                            //Process WIFI or modem data
                            //onPDAInfo(info.ulmsg)
                        }
                        CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_DATALINK -> {
                            Log.d(TAG, "processdata: CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_DATALINK")
                            //Process system data
                          //  onDataLinkInfo(info.ulmsg)
                        }
                        else -> {
                        }
                    }
*/

                    Log.d(TAG, "processdata: 131${info.ulmsg}, 132${CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_INIT_CONNECTION.toLong()}")
                    if (info.msgType == CHC_MESSAGE_TYPE.CHC_MESSAGE_TYPE_SYSTEM) {
                        if (info.ulmsg == CHC_ReceiverConstants.CHC_MESSAGE_SYSTEM_INIT_CONNECTION.toLong()) {
                            Log.d(TAG, "processdata: -> connection is successful")
                            val size = 0
                            CHC_Receiver.CHCGetCmdInitReceiver(receiveref, cmdRef)
                        }
                    }
                    Thread.sleep(1000); i++
                }
            }

        }

        //작동 가능 확인
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
                Log.d(TAG, "internalfileread: -읽기" + dis.readUTF())
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
            initialize()
            processdata();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult:")
        if (resultCode== Activity.RESULT_OK && requestCode==REQUEST_SETTING){
            Log.d(TAG, "onActivityResult: ->REQUEST_SETTING")
            adapterViewpager = AdapterViewpager(this)
            binding.pager1.adapter = adapterViewpager




        // adapterViewpager?.init(tabposition) //변경은 되는데 viewpager에서 변경이 안 됨. 다시 만들어져야함
           // adapterViewpager?.notifyDataSetChanged()
        }
    }


}



