package kr.loplab.gnss05.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.HamburgerActivity
import kr.loplab.gnss05.R
import kr.loplab.gnss05.model.MainIcons
import java.lang.Exception

class MainpageRecyclerViewAdapter internal constructor(context: Context?, data: ArrayList<MainIcons>,  resource : Int) :
    RecyclerView.Adapter<MainpageRecyclerViewAdapter.ViewHolder>() {
    var TAG :String  = javaClass.simpleName;
    var context :Context? = context
    private val mData: ArrayList<MainIcons>
    private val mInflater: LayoutInflater
    private var mClickListener: RecyclerItemClickListener? = null
    var resource = resource;

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(resource, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(mData[position].activityname != HamburgerActivity::class.java)
        {holder.itemView.setBackgroundColor(context?.resources!!.getColor(R.color.brightSkyBlue))}else{
            holder.itemView.setBackgroundColor(context?.resources!!.getColor(R.color.white))
        }
        holder.myTextView.text = mData[position].decription
        if (holder.myTextView.text.equals("원추형 편경사측설")){
            holder.myTextView.visibility = View.GONE;
            holder.myTextView2.visibility = View.VISIBLE;
        }else{
            holder.myTextView.visibility = View.VISIBLE;
            holder.myTextView2.visibility = View.GONE;
        }
        holder.icon.setImageDrawable(context?.getDrawable(mData[position].icon))
        holder.cardView?.setOnClickListener {
            if (mClickListener != null) mClickListener!!.onItemClick(holder.itemView, position)
            Log.d(TAG, "onBindViewHolder: position -> $position ")
            val intent: Intent = Intent(context, mData[position].activityname)
            startActivityForResult(context as Activity ,intent, mData[position].requestcode,null)
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
        var myTextView2: TextView
        var icon: ImageView
        var cardView: CardView?
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
            Log.d(TAG, "onBindViewHolder: position -> $position , request code : ${mData[position].requestcode}")
            val intent = Intent(context, mData[position].activityname)
            startActivityForResult(context as Activity ,intent, mData[position].requestcode,null)
          //  context?.startActivity(intent)
        }

        init {
            myTextView = itemView.findViewById(R.id.info_text)
            myTextView2 = itemView.findViewById(R.id.info_text2)
            icon  = itemView.findViewById(R.id.main_icon)
            try {
                cardView = itemView.findViewById(R.id.cardview3)
            } catch (e : Exception ){
                cardView = null;
            }

            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return mData[id].decription
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