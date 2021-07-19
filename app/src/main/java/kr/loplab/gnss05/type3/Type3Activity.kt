package kr.loplab.gnss05.type3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.loplab.gnss05.type3.model.Type3PriceModel
import kr.loplab.gnss05.type3.model.Type3ProductModel
import kotlinx.android.synthetic.main.activity_type3.*
import kr.loplab.gnss05.R

class Type3Activity : AppCompatActivity() {
    private var TAG = Type3Activity::class.java.name

    private var mLeftAdapter: RvType3LeftAdapter? = null

    private var mRightAdapter: RvType3RightAdapter? = null

    private var mDataList: MutableList<Type3ProductModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type3)

        mLeftAdapter = RvType3LeftAdapter()
        mRightAdapter = RvType3RightAdapter()


        for (produceIndex in 10..60) {
            val productModel = Type3ProductModel()
            productModel.productName = "주식명$produceIndex"

            for (priceIndex in 1..4) {
                val mainPriceModel = Type3PriceModel()
                mainPriceModel.priceName = "스톡${produceIndex}가격-${priceIndex}"
                productModel.mRightDataList.add(mainPriceModel)
            }

            mDataList.add(productModel)
        }


        mLeftAdapter!!.setNewData(mDataList)
        rv_list_left.adapter = mLeftAdapter

        mRightAdapter!!.setNewData(mDataList)
        rv_list_right.adapter = mRightAdapter

        mLeftAdapter!!.mRvType3RightAdapter = mRightAdapter
        mRightAdapter!!.mRvType3LeftAdapter = mLeftAdapter

        //등록 항목을 들으려면 클릭하십시오
        mLeftAdapter?.setOnItemClickListener { adapter, view, position ->
            Log.d(TAG, "mLeftAdapter click position >> " + position)
        }
        mRightAdapter?.setOnItemClickListener { adapter, view, position ->
            Log.d(TAG, "mRightAdapter click position >> " + position)
        }


        //TODO 조심해, 우리는 시험에 집중해야 해
        //TODO 왼쪽의 RecyclerView와 오른쪽의 RecyclerView의 수직 슬라이딩은 서로를 모니터링하여 연동 효과를 구현합니다.
        rv_list_left.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    rv_list_right.scrollBy(dx, dy)
                }
            }
        })
        rv_list_right.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    rv_list_left.scrollBy(dx, dy)
                }
            }
        })

    }
}
