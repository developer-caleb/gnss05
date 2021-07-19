package kr.loplab.gnss05


import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView


class MyDialog(context : Context)
    : DialogRecyclerviewAdapter.RecyclerItemClickListener
{
    var TAG : String = javaClass.simpleName;
    private var context2  = context;
    private val dialog = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var oklistener : MyDialogOKClickedListener
    private lateinit var cancellistener : MyDialogCancelClickedListener
    //private lateinit var cancellistener : MyDialogCancelClickedListener
    private lateinit var listlistener : MyDialogItemClickedListener
    private lateinit var listrecyclerview : RecyclerView
    private var mClickListener: DialogRecyclerviewAdapter.RecyclerItemClickListener? = null
    public var firstLayoutUse = true;
    public var list: ArrayList<List<String>>? = null
    lateinit var adapter : DialogRecyclerviewAdapter

    fun start(content : String) {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog1)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        var firstLayout1  : LinearLayout = dialog.findViewById(R.id.first_layout);
        firstLayout1.visibility = if(firstLayoutUse) View.VISIBLE else {View.GONE} ;
        var firstdivider  : View = dialog.findViewById(R.id.first_divider);
        firstdivider.visibility = if(firstLayoutUse) View.VISIBLE else {View.GONE} ;
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        if(list == null){
        for (i in 0..15) {
            list = ArrayList()
            list!!.add(listOf("/", "1"))
        }} else{

        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        listrecyclerview = dialog.findViewById(R.id.dialog_recyclerview)

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = DialogRecyclerviewAdapter(context2, list!!)

        listrecyclerview.adapter = adapter
        adapter.setClickListener(this)
        selectItem(0)

        btnOK = dialog.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            oklistener.onOKClicked("확인을 눌렀습니다")
            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            cancellistener.onCancelClicked("취소를 눌렀습니다")
            dialog.dismiss()
        }



        dialog.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.oklistener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }

    fun setOnCancelClickedListener(listener: (String) -> Unit) {
        this.cancellistener = object: MyDialogCancelClickedListener {
            override fun onCancelClicked(content: String) {
                listener(content)
            }
        }
    }

    fun setOnListClickedListener(listener: (View, Int) -> Unit) {

        this.listlistener = object: MyDialogItemClickedListener {
            override fun onItemClickDialog(view: View?, position: Int) {
             listener(view!!, position)
            }
        }
    }



    fun setClickListener(itemClickListener: DialogRecyclerviewAdapter.RecyclerItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }
    interface MyDialogCancelClickedListener {
        fun onCancelClicked(content : String)
    }

    interface MyDialogItemClickedListener {
        fun onItemClickDialog(view: View?, position: Int)
    }

    //fun onItemClickActivity(view: View?, position: Int)





    override fun onItemClickDialog(view: View?, position: Int) {
        listlistener.onItemClickDialog(view, position)
        Log.d(TAG, "onItemClickDialog: dialog에서 호출 $position")
    }

    fun dismiss(){
        dialog.dismiss();
    }

    fun refresh(){
       adapter.notifyDataSetChanged();
    }
    public fun selectItem(value : Int){

    }
}

