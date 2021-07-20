package kr.loplab.gnss05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_type5.*
import kotlinx.android.synthetic.main.item_layout_type5.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding
import kr.loplab.gnss05.databinding.ActivityUserFormatViewBinding
import kr.loplab.gnss05.tablelayout_tools2.RvType5Adapter
import kr.loplab.gnss05.type2.model.Type2Model

class UserFormatView : ActivityBase<ActivityUserFormatViewBinding>(){
    override val layoutResourceId: Int
        get() = R.layout.activity_user_format_view
    private var numberOfColumn = 10
    private var numberOfRow = 30

    private var mProductAdapter: RvType5Adapter? = null
    private var mProductDataList: MutableList<Type2Model>? = null
    private var lastposition = -1;

    override fun init() {
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

    override fun initListener() {
        viewBinding.header05.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.btAdd1.setOnClickListener {
            val nextIntent = Intent(this, UserFormatView::class.java)
            startActivity(nextIntent);
        }
        viewBinding.btModify.setOnClickListener {
            val nextIntent = Intent(this, UserFormatView::class.java)
            startActivity(nextIntent);
        }
    }

    override fun initDatabinding() {
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