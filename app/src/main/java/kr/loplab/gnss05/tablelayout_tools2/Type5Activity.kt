package kr.loplab.gnss05.tablelayout_tools2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_type5.*
import kotlinx.android.synthetic.main.item_layout_type5.*
import kr.loplab.gnss05.R
import kr.loplab.gnss05.type2.model.Type2Model


class Type5Activity : AppCompatActivity() {
    private var TAG = Type5Activity::class.java.name
    private var numberOfColumn = 10
    private var numberOfRow = 40

    private var mProductAdapter: RvType5Adapter? = null
    private var mProductDataList: MutableList<Type2Model>? = null
    private var lastposition = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type5)

        for (indexTitle in 0..numberOfColumn) {
            val titleView = LayoutInflater.from(this).inflate(R.layout.item_layout, null)
            titleView.findViewById<TextView>(R.id.tv_data).text = "머리글》${indexTitle}"
            titleView.setBackgroundResource(R.color.shadow_background_color)
            ll_item.addView(titleView)
        }

        layout_head.isFocusable = true
        layout_head.isClickable = true

        layout_head.setOnTouchListener(ListViewAndHeadViewTouchListener())
        rv_list_product.setOnTouchListener(ListViewAndHeadViewTouchListener())


        mProductAdapter = RvType5Adapter(hsv_list_right)
        mProductDataList = mutableListOf()
        for (index in 0..numberOfRow) {
            val productModel = Type2Model()
            productModel.productName = "주식명${index}"
            val priceList: MutableList<String> = mutableListOf()

            for (indexPrice in 0..numberOfColumn) {
                priceList.add("스톡${index}가격${indexPrice}")
            }
            productModel.mPriceList = priceList

            mProductDataList!!.add(productModel)
        }
        mProductAdapter!!.setNewData(mProductDataList)
        rv_list_product.adapter = mProductAdapter

        //TODO 여기에서 click 이벤트가 설정되면 RecycleView로 설정된 OnTouchListener와 충돌합니다.
        mProductAdapter?.setOnItemClickListener { adapter, view, position ->
            Log.d(TAG, "position>>" + position)
           // view.setBackgroundResource(R.color.colorAccent)
            //recyclerView에서 position에 select로 바꿔주면 될 듯?
            ( adapter.data[position] as Type2Model).isPressed = true;
            if (lastposition!=position && lastposition!= -1){
                ( adapter.data[lastposition] as Type2Model).isPressed = false;
                adapter.notifyItemChanged(lastposition)
            }
            adapter.notifyItemChanged(position)
            lastposition= position;
           // runOnUiThread { adapter.notifyDataSetChanged() }
        }

    }

    internal inner class ListViewAndHeadViewTouchListener : View.OnTouchListener {

        override fun onTouch(arg0: View, event: MotionEvent): Boolean {
            // 열 헤더와 listView 컨트롤을 터치할 때 터치 이벤트를 ScrollView에 배포합니다.
            val headScrollView = hsv_list_right
            headScrollView.onTouchEvent(event)
            return false
        }
    }
}
