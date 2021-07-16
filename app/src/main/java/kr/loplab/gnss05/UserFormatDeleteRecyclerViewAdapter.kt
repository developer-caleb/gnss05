package kr.loplab.gnss05

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserFormatDeleteRecyclerViewAdapter internal constructor(context: Context?, data: ArrayList<Array<String>>) :
    RecyclerView.Adapter<UserFormatDeleteRecyclerViewAdapter.ViewHolder>() {
    private val mData: ArrayList<Array<String>>
    private val mInflater: LayoutInflater
    private var mClickListener: RecyclerItemClickListener? = null
    var selectedPosition = 0;
    var context : Context?
    var TAG : String  = javaClass.simpleName;

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_custom_format, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = mData[position][0]
        if(position == selectedPosition) {holder.itemView.setBackgroundColor(context!!.resources.getColor(R.color.grey_cc))}
        else{holder.itemView.setBackgroundColor(context!!.resources.getColor(R.color.white))}


        /*when(mData[position][2]){
           *//* "rightarrow" -> holder.imageView.setImageResource(R.drawable.ic_rightarrow2)
            "back" -> holder.imageView.setImageResource(R.drawable.lefticon)
            "folder" -> holder.imageView.setImageResource(R.drawable.foldericon)
                ".csv" ->  holder.imageView.setImageResource(R.drawable.excelicon)
            else -> holder.imageView.setImageResource(R.drawable.unknown_file)*//*
        }*/

    }
    fun dp2px(dp: Float, context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView
        //var imageView: ImageView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemDeleteClick(view, adapterPosition)
            selectedPosition = adapterPosition
            Log.d(TAG, "onClick: recyclerview호출. $adapterPosition selected")
            notifyDataSetChanged()
        }

        init {
            myTextView = itemView.findViewById(R.id.tv_customformat_recyclerviewitems)
            //imageView = itemView.findViewById(R.id.recyclerview_icon)
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
        fun onItemDeleteClick(view: View?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
        this.context = context
    }


}