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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kr.loplab.gnss05.databinding.ActivityMainBinding
import kr.loplab.gnss05.model.MainIcons
import kr.loplab.gnss05.tableview.TableMainActivity
import java.io.InputStream


class MainActivity : AppCompatActivity(),
    MainpageRecyclerViewAdapter.RecyclerItemClickListener , DialogRecyclerviewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    private lateinit var binding : ActivityMainBinding

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
        val adapter: MainpageRecyclerViewAdapter
        var data = ArrayList<MainIcons>()
        data.add(MainIcons(0, "작업"))
        data.add(MainIcons(1, "작업그룹"))
        data.add(MainIcons(2, "좌표계"))
        data.add(MainIcons(3, "점 보정"))
        data.add(MainIcons(4, "점저장소"))
        data.add(MainIcons(5, "내보내기"))
        data.add(MainIcons(6, "스캔"))
        data.add(MainIcons(7, "클라우드"))
        data.add(MainIcons(8, "설정"))

        // set up the RecyclerView

        // set up the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvNumbers);

        val numberOfColumns = 6

        adapter = MainpageRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
        permissionchecking()
    }

    fun init(){

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
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundColor(resources.getColor(R.color.blue))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundColor(resources.getColor(R.color.black))
            }

        })


    }



    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: $position clicked!")
        when (position){
            0 ->    { intent = Intent(this, StandardPointActivity::class.java)
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

            }
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






}



