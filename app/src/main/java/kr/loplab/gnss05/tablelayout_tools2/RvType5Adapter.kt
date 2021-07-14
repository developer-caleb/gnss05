package kr.loplab.gnss05.tablelayout_tools2

import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kr.loplab.gnss05.type2.model.Type2Model
import kr.loplab.gnss05.R
import kr.loplab.gnss05.tablelayout_tools2.view.SyncHScrollView

/**
 * 주식 정보의 각 줄은 첫 번째는 주식 이름이고 다음은 가격 정보입니다.
 * Created by kotlin on 18-1-29.
 */
class RvType5Adapter(
        //목록의 헤더는 수평 슬라이딩과 연결되어야 합니다.
        var mHeadSyncRecyclerView: SyncHScrollView
) : BaseQuickAdapter<Type2Model, BaseViewHolder>(R.layout.item_layout_type5) {
    private var TAG = RvType5Adapter::class.java.name


    override fun convert(helper: BaseViewHolder, item: Type2Model) {
        //현재 아이템 위치 정보
        val productPosition = helper.adapterPosition

        helper.setText(R.id.tv_product_name, item.productName)
        val ll_item = helper.getView<LinearLayout>(R.id.ll_item)
        ll_item.removeAllViews()

        item.mPriceList?.forEach {
            val itemView = getItemView(R.layout.item_layout, null)
            itemView.findViewById<TextView>(R.id.tv_data).text = it
            ll_item.addView(itemView)
        }

        //오른쪽의 가로 목록
        val itemSyncHScrollView = helper.getView<SyncHScrollView>(R.id.hsv_list_right)

        //슬라이딩 관찰자 추가
        mHeadSyncRecyclerView.AddOnScrollChangedListener(OnScrollChangedListenerImp(itemSyncHScrollView))


        //TODO Item의 click 이벤트와 RecycleView의 터치 이벤트 간의 충돌로 인해 슬라이드가 되지 않는 현상을 해결하기 위해서는
        // 해당 항목의 터치 이벤트에 대한 대응이 필요하지만 여전히 효과가 좋지 못하며, ListView가 원활하게 사용되지 않습니다.
        helper.itemView.setOnTouchListener(ListViewAndHeadViewTouchListener())
    }

    internal inner class OnScrollChangedListenerImp(var mScrollViewArg: SyncHScrollView) :
            SyncHScrollView.OnScrollChangedListener {

        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
            mScrollViewArg.smoothScrollTo(l, t)
        }
    }


    internal inner class ListViewAndHeadViewTouchListener : View.OnTouchListener {

        override fun onTouch(arg0: View, event: MotionEvent): Boolean {
            // 열 헤더와 listView 컨트롤을 터치할 때 터치 이벤트를 ScrollView에 배포합니다
            val headScrollView = mHeadSyncRecyclerView
            headScrollView.onTouchEvent(event)
            return false
        }
    }
}
