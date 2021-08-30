package kr.loplab.gnss05


import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.adapter.DialogRecyclerviewAdapter


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
    private lateinit var checklistener : MyDialogCheckClickedListener
    private lateinit var sortlistener : MyDialogSortClickedListener
    //private lateinit var cancellistener : MyDialogCancelClickedListener
    private lateinit var listlistener : MyDialogItemClickedListener
    private lateinit var listrecyclerview : RecyclerView
    private var mClickListener: DialogRecyclerviewAdapter.RecyclerItemClickListener? = null
    lateinit var title_dialog: TextView;
    lateinit var input_text: EditText;
    var input_text_str = "";
    lateinit var title_button : ImageView;
    lateinit var title_sortbutton : ImageView;

    public var firstLayoutUse = true;
    public var titlesort = false;
    public var list: ArrayList<String>? = null
    var selectedposition = 0
    var inputValue = ""  // ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>header에 넣을 값
    lateinit var adapter : DialogRecyclerviewAdapter

    fun start(content : String) {
        Log.d(TAG, "start: $content")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog1)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        var firstLayout1  : LinearLayout = dialog.findViewById(R.id.first_layout);
        firstLayout1.visibility = if(firstLayoutUse) View.VISIBLE else {View.GONE} ;
        title_button = dialog.findViewById(R.id.title_button);
        title_button.visibility = if(firstLayoutUse) View.VISIBLE else {View.GONE} ;
        title_sortbutton = dialog.findViewById(R.id.title_sort_button);
        title_button.visibility = if(firstLayoutUse && titlesort) View.VISIBLE else {View.GONE} ;
        var firstdivider  : View = dialog.findViewById(R.id.first_divider);
        firstdivider.visibility = if(firstLayoutUse) View.VISIBLE else {View.GONE} ;
        input_text = dialog.findViewById(R.id.input_text);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        if(list == null){
        for (i in 0..15) {
            list = ArrayList()
            list!!.add("/")
        }} else{

        }



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        listrecyclerview = dialog.findViewById(R.id.dialog_recyclerview)

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = DialogRecyclerviewAdapter(context2, list!!, selectedposition)
        listrecyclerview.adapter = adapter
        adapter.setClickListener(this)
        Log.d(TAG, "start: -> adapter initialize")
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
        title_button.setOnClickListener {
            checklistener.onCheckClicked(input_text.text.toString())
        }
        title_sortbutton.setOnClickListener {
            sortlistener.onSortClicked()

        }

        if (input_text_str.isNotEmpty()) {input_text.setText(input_text_str)}
        dialog.show()
    }

    fun setHeader(headerstr : String ){
        title_dialog = dialog.findViewById(R.id.title_dialog)
        if (headerstr.isNotEmpty()) title_dialog.text = headerstr
    }
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.oklistener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }
    fun setOnCheckClickedListener(listener: (String) -> Unit) {
        this.checklistener = object: MyDialogCheckClickedListener {
            override fun onCheckClicked(content: String) {
                listener(content)
            }
        }
    }

    fun setOnSortClickedListener(listener: () -> Unit) {
        this.sortlistener = object: MyDialogSortClickedListener {
            override fun onSortClicked() {
               listener()
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
    interface MyDialogCheckClickedListener {
        fun onCheckClicked(content : String)
    }
    interface MyDialogSortClickedListener {
        fun onSortClicked()
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

