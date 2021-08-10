package kr.loplab.gnss05;

import android.Manifest
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
import com.chc.gnss.sdk.*
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kr.loplab.gnss05.SplashActivity.Gnssreceiver2
import kr.loplab.gnss05.databinding.ActivityMainBinding
import kr.loplab.gnss05.model.MainIcons
import kr.loplab.gnss05.tableview.TableMainActivity
import java.io.*
import java.lang.Exception
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(),
    MainpageRecyclerViewAdapter.RecyclerItemClickListener , DialogRecyclerviewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    private lateinit var binding : ActivityMainBinding
    lateinit var adapter: MainpageRecyclerViewAdapter
    var selectdata = ArrayList<MainIcons>()
    var data0 = ArrayList<MainIcons>()
    var data1 = ArrayList<MainIcons>()
    var data2 = ArrayList<MainIcons>()
    var data3 = ArrayList<MainIcons>()

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


        data0.add(MainIcons(R.drawable.ic_0_0_work, "작업"))
        data0.add(MainIcons(R.drawable.ic_0_1_work_group, "작업그룹"))
        data0.add(MainIcons(R.drawable.ic_0_2_coordinate, "좌표계"))
        data0.add(MainIcons(R.drawable.ic_0_3_correction, "점 보정"))
        data0.add(MainIcons(R.drawable.ic_0_4_pointsave, "점저장소"))
        data0.add(MainIcons(R.drawable.ic_0_5_export, "내보내기"))
        data0.add(MainIcons(R.drawable.ic_0_6_scan, "스캔"))
        data0.add(MainIcons(R.drawable.ic_0_7_cloud, "클라우드"))
        data0.add(MainIcons(R.drawable.ic_0_8_settings, "설정", SettingActivity::class.java))
        data0.add(MainIcons(R.drawable.ic_0_9_information, "정보"))

        data1.add(MainIcons(R.drawable.ic_1_0_connect_device, "장비연결"))
        data1.add(MainIcons(R.drawable.ic_1_1_move_country, "이동국"))
        data1.add(MainIcons(R.drawable.ic_1_2_reference_country, "기준국"))
        data1.add(MainIcons(R.drawable.ic_1_3_stop_survey, "정지측량"))
        data1.add(MainIcons(R.drawable.ic_1_4_connection_state, "연결상태"))
        data1.add(MainIcons(R.drawable.ic_1_5_connection_save, "연결저장"))
        data1.add(MainIcons(R.drawable.ic_1_6_connection_infomation, "연결정보"))
        data1.add(MainIcons(R.drawable.ic_1_7_sensor_correction, "센서보정"))
        data1.add(MainIcons(R.drawable.ic_1_8_setting_equipment, "장비설정"))
        data1.add(MainIcons(R.drawable.ic_1_9_frequency_setting, "주파수 설정"))
        data1.add(MainIcons(R.drawable.ic_1_10_change_location, "위치변경"))
        data1.add(MainIcons(R.drawable.ic_1_11_enroll_equipment, "장비등록"))
        data2.add(MainIcons(R.drawable.ic_2_0_status_work, "현황작업"))
        data2.add(MainIcons(R.drawable.ic_2_1_detail_survey, "상세 측량"))
        data2.add(MainIcons(R.drawable.ic_2_2_cad, "CAD (측설)"))
        data2.add(MainIcons(R.drawable.ic_2_3_point_stack_out, "점측설"))
        data2.add(MainIcons(R.drawable.ic_2_4_line_stack_out, "라인측설"))
        data2.add(MainIcons(R.drawable.ic_2_5_gis, "GIS"))
        data2.add(MainIcons(R.drawable.ic_2_6_road_stack_out, "도로측설"))
        data2.add(MainIcons(R.drawable.ic_2_7_road_offset, "도로옵셋"))
        data2.add(MainIcons(R.drawable.ic_2_8_cross_section_measurement, "횡단면 측정"))
        data2.add(MainIcons(R.drawable.ic_2_9_cross_survey, "횡단측량"))
        data2.add(MainIcons(R.drawable.ic_2_10_bridge_survey, "교량측량"))
        data2.add(MainIcons(R.drawable.ic_2_11_conical_superelevation_stakeout, "원추형 편경사측설"))
        data2.add(MainIcons(R.drawable.ic_2_12_wire_stakeout, "전선측설"))
        data2.add(MainIcons(R.drawable.ic_2_13_electric_stakeout, "전기 측설"))
        data2.add(MainIcons(R.drawable.ic_2_14_level_control, "레벨제어"))
        data2.add(MainIcons(R.drawable.ic_2_15_curve_stakeout, "곡선측설"))
        data2.add(MainIcons(R.drawable.ic_2_16_exist_measurement, "기존측정"))
        data2.add(MainIcons(R.drawable.ic_2_17_railroad_stakeout, "철길측설"))
        data2.add(MainIcons(R.drawable.ic_2_18_layer, "레이어"))
        data2.add(MainIcons(R.drawable.ic_2_19_setting_measuring_range, "측정 범위 설정"))
        data3.add(MainIcons(R.drawable.ic_3_0_calibration, "캘리브레이션"))
        data3.add(MainIcons(R.drawable.ic_3_1_coordinate_inverse_calculation, "좌표역계산"))
        data3.add(MainIcons(R.drawable.ic_3_2_angle_converter, "각도변환기"))
        data3.add(MainIcons(R.drawable.ic_3_3_area_calculation, "면적계산"))
        data3.add(MainIcons(R.drawable.ic_3_4_cogo_calculation, "COGO계산"))
        data3.add(MainIcons(R.drawable.ic_3_5_calculation, "계산기"))
        data3.add(MainIcons(R.drawable.ic_3_6_external_radio, "외장라디오"))
        data3.add(MainIcons(R.drawable.ic_3_7_calculation_volumn, "볼륨계산"))
        data3.add(MainIcons(R.drawable.ic_3_8_reset_location, "위치 재설정"))
        data3.add(MainIcons(R.drawable.ic_3_9_ftp_share, "FTP 공유"))
        data3.add(MainIcons(R.drawable.ic_3_10_share, "공유"))



        selectdata.addAll(data0);

        // set up the RecyclerView

        // set up the RecyclerView


        val numberOfColumns = 6
        binding.recyclerviewMain.layoutManager = LinearLayoutManager(this);
        //binding.recyclerviewMain.layoutManager = GridLayoutManager(this, 3)

        adapter = MainpageRecyclerViewAdapter(this, selectdata, R.layout.recyclerview_item_vertical)
        //adapter = MainpageRecyclerViewAdapter(this, selectdata, R.layout.recyclerview_item_grid)
        adapter.setClickListener(this)
        binding.recyclerviewMain.adapter = adapter
        permissionchecking()
    }

    fun init(){
        val gnssreceiver3 = Gnssreceiver3()

    }

    fun initListener(){
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

       selectdata.clear()
       Log.d(TAG, "selecttab: $position")
       when (position){
           0 ->{
               selectdata.addAll(data0)
           }
               1->{
                   selectdata.addAll(data1)
               }
           2->{
               selectdata.addAll(data2)
           }
           3->{
               selectdata.addAll(data3)
           }
       }
       adapter.notifyDataSetChanged();
   }


    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: $position clicked!")
        when (position){
      /*      0 ->    { intent = Intent(this, StandardPointActivity::class.java)
                startActivity(intent);}
            1 ->    { intent = Intent(this, NaverMap::class.java)
                startActivity(intent);}
            2 -> {
                val dlg = MyDialog(this)
                dlg.setClickListener(this)
                dlg.setOnOKClickedListener{ content ->
                    Log.d(TAG, "onItemClick: $content")
                }
                dlg.start("메인의 내용을 변경할까요?")
            }
            3 -> {
                val nextIntent = Intent(this, ColorPickerdialog::class.java)
                startActivity(nextIntent);
            }
            4 -> {
                val nextIntent = Intent(this, FileExportActivity::class.java)
                startActivity(nextIntent);
            }
            5 -> {
                val nextIntent = Intent(this, UserFormatMake::class.java)
                startActivity(nextIntent);
            }


            6 -> {
                //val nextIntent = Intent(this, TableActivity::class.java)
                val nextIntent = Intent(this, TableMainActivity::class.java)
                startActivity(nextIntent);
            }
            7 -> {
                //val nextIntent = Intent(this, TableActivity::class.java)
                val nextIntent = Intent(this, TableMainActivity::class.java)
                startActivity(nextIntent);
            }
            8 -> {
                //val nextIntent = Intent(this, TableActivity::class.java)
                val nextIntent = Intent(this, TableMainActivity::class.java)
                startActivity(nextIntent);

            }*/
        }
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

}



