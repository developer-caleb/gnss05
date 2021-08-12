package kr.loplab.gnss05

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class DialogRecyclerviewAdapter internal constructor(context: Context?, data: ArrayList<List<String>>, ) :
    RecyclerView.Adapter<DialogRecyclerviewAdapter.ViewHolder>() {
    private val mData: ArrayList<List<String>>
    private val mInflater: LayoutInflater
    private var mClickListener: RecyclerItemClickListener? = null
    private var TAG : String = javaClass.simpleName;


    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_checkitem, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = mData[position][0]

        if (mData[position][1] == "1"){
            holder.checkboxicon.isChecked =true
            Log.d(TAG, "onBindViewHolder: 선택 됨");
        }else {
            holder.checkboxicon.isChecked =false
            Log.d(TAG, "onBindViewHolder: 선택 안 됨");
        }



    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var checkboxicon: CheckBox
        var myTextView: TextView

        override fun onClick(view: View) {
            Log.d(TAG, "onClick: recyclerview로 호출했을 경우 $adapterPosition ㅎㅎ")
            if (mClickListener != null) mClickListener!!.onItemClickDialog(view, adapterPosition)else{
                Log.d(TAG, "onClick: recyclerview로 호출했을 경우인데 대신에 mClickListener가 null임")
            }
        }

        init {
            myTextView = itemView.findViewById(R.id.list_text2)
            checkboxicon = itemView.findViewById(R.id.recyclerview_icon2)

            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return mData[id][0]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: RecyclerItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface RecyclerItemClickListener {
        //fun onItemClickActivity(view: View?, position: Int)
        fun onItemClickDialog(view: View?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }
}