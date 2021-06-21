package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.internal.util.ArrayListSupplier
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityFileExportBinding

class FileExportActivity : ActivityBase<ActivityFileExportBinding>(),  FiledirectoryRecyclerViewAdapter.RecyclerItemClickListener  {
    override val layoutResourceId: Int
        get() = R.layout.activity_file_export

    override fun init() {
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
        viewBinding.textView8.text = "\uD83D\uDDC2    Textview"
    }

    override fun initDatabinding() {

    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "Recyclerview onItemClick: $position 클릭했음")
    }
}