package kr.loplab.gnss05;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.MyRecyclerViewAdapter
import kr.loplab.gnss05.R

class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter: MyRecyclerViewAdapter
        val data = arrayOf(
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

        adapter = MyRecyclerViewAdapter(this, data)
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

        }
    }
}



