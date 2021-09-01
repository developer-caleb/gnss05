package kr.loplab.gnss05.common

import android.content.Context
import android.util.TypedValue

class Gnss_Constant {
    fun dp2px(dp: Float, context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    //val px = dp2px(dp , context)


}