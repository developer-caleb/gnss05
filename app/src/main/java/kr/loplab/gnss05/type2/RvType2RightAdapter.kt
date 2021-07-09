package kr.loplab.gnss05.type2

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kr.loplab.gnss05.R


/**
 * Created by kotlin on 18-1-29.
 */
class RvType2RightAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_layout_type1) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_data, item)
    }
}
