package kr.loplab.gnss05;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),
    MainpageRecyclerViewAdapter.RecyclerItemClickListener , DialogRecyclerviewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter: MainpageRecyclerViewAdapter
        val data = arrayOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30",
            "31",
            "32",
            "33",
        )

        // set up the RecyclerView

        // set up the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvNumbers);

        val numberOfColumns = 6

        adapter = MainpageRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter





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
}



