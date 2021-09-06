package kr.loplab.gnss05.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.R

class FiledirectoryRecyclerViewAdapter internal constructor(context: Context?, data: ArrayList<Array<String>>) :
    RecyclerView.Adapter<FiledirectoryRecyclerViewAdapter.ViewHolder>() {
    private val mData: ArrayList<Array<String>>
    private val mInflater: LayoutInflater
    private var mClickListener: RecyclerItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_listitem, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = mData[position][0]
        when(mData[position][2]){
            "rightarrow" -> holder.imageView.setImageResource(R.drawable.ic_move_category)
            "back" -> holder.imageView.setImageResource(R.drawable.ic_move_back)
            "folder" -> holder.imageView.setImageResource(R.drawable.foldericon)
                ".csv" ->  holder.imageView.setImageResource(R.drawable.excelicon)
            else -> holder.imageView.setImageResource(R.drawable.unknown_file)
        }

    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView
        var imageView: ImageView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            myTextView = itemView.findViewById(R.id.list_text2)
            imageView = itemView.findViewById(R.id.recyclerview_icon)
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
        fun onItemClick(view: View?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }
}