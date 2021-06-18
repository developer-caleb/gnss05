package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class FileExportActivity : AppCompatActivity() {
    var TAG : String = javaClass.simpleName;
    lateinit var header : DefaultHeader ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_export)
        header = findViewById(R.id.header_03)

        header.setOnBackButtonClickListener { v ->
            println("hello") // 클릭시 처리 … 쓸게 없어서 println 을 입력했다.
            println("hello")
        }

    }

    fun addNormal (x: Int, y: Int): Int {
        return x + y
    }
}