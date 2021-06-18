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
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener



class MainActivity : AppCompatActivity(),
    MainpageRecyclerViewAdapter.RecyclerItemClickListener , DialogRecyclerviewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter: MainpageRecyclerViewAdapter
        val data = arrayOf(
            "0",  "1", "2",
            "3", "4", "5", "6", "7", "8",
            "9","10", "11", "12",  "13",  "14",
            "15", "16", "17",  "18", "19", "20",
            "21",  "22", "23", "24", "25", "26",
            "27",  "28", "29", "30", "31", "32",
            "33",
        )

        // set up the RecyclerView

        // set up the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvNumbers);

        val numberOfColumns = 6

        adapter = MainpageRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
        permissionchecking()




    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: $position clicked!")
        when (position){
            0 ->    { val nextIntent = Intent(this, StandardPointActivity::class.java)
                startActivity(nextIntent);}
            1 ->    { val nextIntent = Intent(this, NaverMap::class.java)
                startActivity(nextIntent);}
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
        }
    }

    override fun onItemClick2(view: View?, position: Int) {
        Log.d(TAG, "onItemClick2: prrr $position")
        Log.d(TAG, "onClick: 메인액티비티에서 부른 logd+ $position")
    }
    fun permissionchecking(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withActivity(this)
                .withPermissions(
                    Manifest.permission.CAMERA,  // 카메라
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) // 위치
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) { // 권한 여부를 다 묻고 실행되는 메소드
                        // check if all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(this@MainActivity, "모든 권한 허용", Toast.LENGTH_SHORT).show()
                        }
                    } // onPermissionsChecked()..

                    override fun onPermissionRationaleShouldBeShown(
                        list: List<PermissionRequest?>,
                        permissionToken: PermissionToken?
                    ) { // 이전 권한 여부를 거부한 권한이 있으면 실행되는 메소드
                        Toast.makeText(this@MainActivity, "list : $list", Toast.LENGTH_LONG)
                            .show() // 거부한 권한 이름이 저장된 list
                        showSettingsDialog() // 권한 거부시 앱 정보 설정 페이지를 띄우기 위한 임의 메소드
                    } // onPermissionRationaleShouldBeShown()..
                })
                .check()
        }
    }

    // 만약 권한을 거절했을 경우,  다이얼로그 띄우기 위한 임의 메소드
    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                openSettings() // 어플리케이션 정보 설정 페이지 띄움.
            })
        builder.setNegativeButton("Cancel",
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



