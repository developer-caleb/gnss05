package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.reactivex.internal.util.ArrayListSupplier
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityFileExportBinding
import java.io.File

class FileExportActivity : ActivityBase<ActivityFileExportBinding>(),  FiledirectoryRecyclerViewAdapter.RecyclerItemClickListener  {
    override val layoutResourceId: Int
        get() = R.layout.activity_file_export
        var backdirectory : String = "";
        //
    //private var items : ArrayList<String> = ArrayList();
    var data = ArrayList<Array<String>>()
    private var rootPath = "";
    private var nextPath = "";
    private var prevPath = "";
    private var currentPath = "";
    private lateinit var  messageView: TextView;
    private lateinit var adapter : FiledirectoryRecyclerViewAdapter
    //



    override fun init() {
        adapter  = FiledirectoryRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        viewBinding.recyclerview.adapter = adapter
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        var result : Boolean = initdirectory(rootPath);








    }

    override fun initListener() {
        viewBinding.header03.setOnBackButtonClickListener {
            onBackPressed()
            println("hello")
        }
        viewBinding.strDirectory.text = "\uD83D\uDDC2    Textview"
    }

    override fun initDatabinding() {

    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "Recyclerview onItemClick: $position 클릭했음")

          Log.d("KJH_TEST",  "$position : " + data.get(position).toString());
                currentPath = viewBinding.strDirectory.text.toString();
                var path : String= data.get(position).toString();
                if (path.equals("..")) {
                    prevPath(path);
                } else {
                    nextPath(path);
                }


    }

    fun initdirectory(rootPath : String) :Boolean   {
        Log.d(TAG, "initdirectory: $rootPath")
        // 파일 객체 생성
        var fileRoot: File =  File(rootPath);
        //디렉토리가 아님
        if(!fileRoot.isDirectory())        {
            showToast("Not Directory");
            return false;
        }
        viewBinding.strDirectory.setText(rootPath);

        //파일 리스트 가져오기 ->//파일 리스트가 없음
        var fileList  = fileRoot.list();
        if ( fileList == null )        {
            showToast("Could not find List");
            return false;
        }

        // 아이템 리스트 전부 삭제
        data.clear();

        // 리스트의 첫 항목은 뒤로가기 위해 ".." 세팅
        ////items.add("..");
        data.add(arrayOf( "기본 저장 디렉토리로 이동", "이동할 디렉토리 주소"), )
        data.add(arrayOf( "내부 저장소 루트 디렉토리로 이동", "이동할 디렉토리 주소"), )
        data.add(arrayOf( "프로그램 저장 디렉토리로 이동", "이동할 디렉토리 주소"))
        data.add(arrayOf( "SD카드 루트 디렉토리로 이동", "이동할 디렉토리 주소"))
        data.add(arrayOf( "돌아가기", "이동할 디렉토리 주소"))
       /* for(x in 0..5){
            data.add(arrayOf("ddd", x.toString()))
        }*/




        fileList.forEachIndexed { index, string ->  data.add(arrayOf(fileList[index].toString(), fileList[index].toString()));}

        // 리스트 뷰에 적용
        adapter.notifyDataSetChanged();
        return true;
    }


    fun nextPath(str: String) {
        prevPath = currentPath

        // 현재 경로에서 / 와 다음 경로 붙이기
        nextPath = "$currentPath/$str"
        Log.d(TAG, "nextPath: $nextPath")
        val file = File(nextPath)
        if (file.isDirectory == false) {
            showToast("Not Directory");
            return
        }
        val fileList = file.list()
        data.clear()
        data.add(arrayOf("..", ",,"))
        for (i in fileList.indices) {
            data.add(arrayOf(fileList[i], fileList[i] ))
        }
        viewBinding.strDirectory.text = nextPath
        adapter!!.notifyDataSetChanged()
    }

    fun prevPath(str: String?) {
        nextPath = currentPath
        prevPath = currentPath


        // 마지막 / 의 위치 찾기
        val lastSlashPosition = prevPath.lastIndexOf("/")

        // 처음부터 마지막 / 까지의 문자열 가져오기
        prevPath = prevPath.substring(0, lastSlashPosition)
        Log.d(TAG, "prevPath: $prevPath")
        val file = File(prevPath)
        if (file.isDirectory == false) {
            showToast("Not Directory")
            return
        }
        val fileList = file.list()
        data.clear()
        data.add(arrayOf("..", ".."))
        for (i in fileList.indices) {
            data.add(arrayOf(fileList[i], fileList[i] ))
        }
        viewBinding.strDirectory.text = prevPath
        adapter!!.notifyDataSetChanged()
    }



}