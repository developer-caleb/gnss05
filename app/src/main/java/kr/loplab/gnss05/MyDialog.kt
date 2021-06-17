package kr.loplab.gnss05


import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MyDialog(context : Context) : SimpleTextAdapter.RecyclerItemClickListener {
    var TAG : String = javaClass.simpleName;
    private var context2  = context;
    private val dialog = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : MyDialogOKClickedListener
    private lateinit var listrecyclerview : RecyclerView

    fun start(content : String) {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog1)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        val list: ArrayList<String> = ArrayList()
        for (i in 0..15) {
            list.add(String.format("TEXT %d", i))
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        listrecyclerview = dialog.findViewById(R.id.dialog_recyclerview)

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        val adapter = SimpleTextAdapter(context2, list)

        listrecyclerview.adapter = adapter
        adapter.setClickListener(this)


        btnOK = dialog.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            listener.onOKClicked("확인을 눌렀습니다")
            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            listener.onCancelClicked("취소를 눌렀습니다")
            dialog.dismiss()
        }



        dialog.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }

            override fun onCancelClicked(content: String) {
                listener(content)
            }
        }
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
        fun onCancelClicked(content : String)
    }

    override fun onItemClick2(view: View?, position: Int) {
        Log.d(TAG, "onItemClick2: 음.. 일단 dialog에서는 가져와짐.. $position")
    }
}

