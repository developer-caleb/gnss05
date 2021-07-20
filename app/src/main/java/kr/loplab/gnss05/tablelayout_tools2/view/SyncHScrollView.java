package kr.loplab.gnss05.tablelayout_tools2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyulaoshi on 2018/1/31.
 * 사용자 정의 스크롤 컨트롤
 * 과부하 {@link SyncHScrollView#onScrollChanged}（스크롤바 변경）,모든 변경 사항을 모니터링하고 관찰자에게 알립니다.
 * (이것은 변경)관찰자
 * 사용할 수 있다 {@link SyncHScrollView#AddOnScrollChangedListener(OnScrollChangedListener) } 이 컨트롤의 스크롤 막대 변경 사항을 구독하려면
 */

public class SyncHScrollView extends HorizontalScrollView {
    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

    public SyncHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SyncHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncHScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        /*
         *
스크롤바가 움직이면 스크롤 이벤트가 발생합니다. 관찰자에게 알리면 관찰자는 이를 다른 항목의 스크롤 뷰에 전달합니다.
		 */
        if (mScrollViewObserver != null) {
            mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /*
     *
이 컨트롤의 스크롤 막대 변경 이벤트를 구독합니다.
     * */
    public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.AddOnScrollChangedListener(listener);
    }

    /*
     *
이 컨트롤의 스크롤 막대 변경 이벤트 구독 취소
     * */
    public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.RemoveOnScrollChangedListener(listener);
    }

    /*
     * 스크롤 이벤트가 발생했을 때
     */
    public static interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    /**
     * 관찰자
     */
    public static class ScrollViewObserver {
        List<OnScrollChangedListener> mList;

        public ScrollViewObserver() {
            super();
            mList = new ArrayList<>();
        }

        public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
            mList.add(listener);
        }

        public void RemoveOnScrollChangedListener(
                OnScrollChangedListener listener) {
            mList.remove(listener);
        }

        public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (mList == null || mList.size() == 0) {
                return;
            }
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i) != null) {
                    mList.get(i).onScrollChanged(l, t, oldl, oldt);
                }
            }
        }
    }
}