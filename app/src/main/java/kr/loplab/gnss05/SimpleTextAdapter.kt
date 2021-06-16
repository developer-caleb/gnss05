package kr.loplab.gnss05

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SimpleTextAdapter internal constructor(list: ArrayList<String>?) :
    RecyclerView.Adapter<SimpleTextAdapter.ViewHolder>() {
    private var mData: ArrayList<String>? = null
    private var mClickListener: MyRecyclerViewAdapter.RecyclerItemClickListener? = null
    private var TAG : String = javaClass.simpleName;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textView1: TextView

        override fun onClick(v: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(itemView , adapterPosition)
            Log.d(TAG, "onClick: recyclerview에서 부른 logd+ $adapterPosition")
        }

        init {
            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.list_text2)
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_checkitem, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = mData!![position]
        holder.textView1.text = text

    }
    // allows clicks events to be caught
    fun setClickListener(itemClickListener: MyRecyclerViewAdapter.RecyclerItemClickListener?) {
        mClickListener = itemClickListener
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    override fun getItemCount(): Int {
        return mData!!.size
    }

    interface RecyclerItemClickListener {
        fun onItemClick2(view: View?, position: Int)
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    init {
        mData = list
    }


}