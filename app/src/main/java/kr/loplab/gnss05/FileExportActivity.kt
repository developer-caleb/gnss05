package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FileExportActivity : AppCompatActivity() {
    var TAG : String = javaClass.simpleName;
    lateinit var header : DefaultHeader ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_export)
        header = findViewById(R.id.header_03)

        header.setOnBackButtonClickListener { v->{
            Log.d(TAG, "onCreate: ")
            onBackPressed() } }
    }
}