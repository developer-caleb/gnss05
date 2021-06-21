package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import io.reactivex.internal.util.ArrayListSupplier
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityFileExportBinding
import java.io.File

class FileExportActivity : ActivityBase<ActivityFileExportBinding>(),  FiledirectoryRecyclerViewAdapter.RecyclerItemClickListener  {
    override val layoutResourceId: Int
        get() = R.layout.activity_file_export
        var backdirectory : String = "";
        //
    private var items : ArrayList<String> = ArrayList();
    private var rootPath = "";
    private var nextPath = "";
    private var prevPath = "";
    private var currentPath = "";
    private lateinit var  messageView: TextView;
    //



    override fun init() {
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        var result : Boolean = initdirectory(rootPath);





        var data = ArrayList<Array<String>>()
        data.add(arrayOf( "기본 저장 디렉토리로 이동", "이동할 디렉토리 주소"), )
        data.add(arrayOf( "내부 저장소 루트 디렉토리로 이동", "이동할 디렉토리 주소"), )
        data.add(arrayOf( "프로그램 저장 디렉토리로 이동", "이동할 디렉토리 주소"))
        data.add(arrayOf( "SD카드 루트 디렉토리로 이동", "이동할 디렉토리 주소"))
        data.add(arrayOf( "돌아가기", "이동할 디렉토리 주소"))
        for(x in 0..5){
            data.add(arrayOf("ddd", x.toString()))
        }
        val adapter = FiledirectoryRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        viewBinding.recyclerview.adapter = adapter
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
    }

    public fun initdirectory(rootPath : String) :Boolean   {
        // 파일 객체 생성
        var fileRoot: File =  File(rootPath);
        if(fileRoot.isDirectory() == false)        {
            showToast("Not Directory");
            return false;
        }
        viewBinding.strDirectory.setText(rootPath);

        // 파일 리스트 가져오기
        var fileList  = fileRoot.list();
        if ( fileList == null )        {
            showToast("Could not find List");
            return false;
        }

        // 아이템 리스트 전부 삭제
        items.clear();

        // 리스트의 첫 항목은 뒤로가기 위해 ".." 세팅
        items.add("..");
        fileList.forEachIndexed { index, string ->  items.add(fileList[index]);}

        // 리스트 뷰에 적용
        listAdapter.notifyDataSetChanged();
        return true;
    }



}