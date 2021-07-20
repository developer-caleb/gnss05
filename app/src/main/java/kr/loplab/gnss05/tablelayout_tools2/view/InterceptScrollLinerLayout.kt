package kr.loplab.gnss05.tablelayout_tools2.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout


/**
 * 터치 이벤트를 가로채기 위한 컨테이너
 * Created by xiaoyulaoshi on 2018/1/31.
 */

class InterceptScrollLinerLayout : LinearLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    /**
     * 터치 이벤트 가로채기
     * @see android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "onInterceptTouchEvent" + ev)
        return true
    }

    companion object {
        private val TAG = InterceptScrollLinerLayout::class.java.name
    }
}
