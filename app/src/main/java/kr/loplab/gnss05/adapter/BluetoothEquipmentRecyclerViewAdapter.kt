package kr.loplab.gnss05.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.R

class BluetoothEquipmentRecyclerViewAdapter internal constructor(context: Context?, data: ArrayList<BluetoothDevice>) :
    RecyclerView.Adapter<BluetoothEquipmentRecyclerViewAdapter.ViewHolder>() {
    private val mData: ArrayList<BluetoothDevice>
    private val mInflater: LayoutInflater
    private val context :Context? = context;
    private var mClickListener: RecyclerItemClickListener? = null
    var selectednum = -1;

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_connect_equipment, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.equipmentName.text = mData[position].name
        holder.equipmentCode.text = mData[position].address
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.main_blue))
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var equipmentName: TextView
        var equipmentCode: TextView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onBluetoothItemClick(view, adapterPosition)

        }

        init {
            equipmentName = itemView.findViewById(R.id.tv_connected_equipment_name)
            equipmentCode = itemView.findViewById(R.id.tv_connected_equipment_code)

            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return mData[id].name
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: RecyclerItemClickListener?) {
        mClickListener = itemClickListener
    }


    // parent activity will implement this method to respond to click events
    interface RecyclerItemClickListener {
        fun onBluetoothItemClick(view: View?, position: Int)
    }


    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }
}