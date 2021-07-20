package kr.loplab.gnss05.tablelayout_tools2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 관련 슬라이딩을 실현할 수 있는 RecyclerView, 실현 방법은 옵저버 메커니즘
 * Created by xiaoyulaoshi on 2018/1/31.
 */

public class SyncRecyclerView extends RecyclerView {

    private final String TAG = SyncRecyclerView.class.getName();

    //관찰자
    private SyncRecyclerViewObserver mSyncRecyclerViewObserver = new SyncRecyclerViewObserver();


    public SyncRecyclerView(Context context) {
        super(context);
    }

    public SyncRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        Log.i(TAG, "onScrolled >> dx=" + dx + "\tdy=" + dy);
        //
        //발생하는 슬라이딩 변경을 관찰자의 모든 슬라이딩 모니터에 알립니다.
        if (mSyncRecyclerViewObserver != null) {
            mSyncRecyclerViewObserver.notifyOnScrollChange(dx, dy);
        }
        super.onScrolled(dx, dy);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    /**
     *
     * 현재 컨트롤의 슬라이딩 이벤트 리스너를 관찰자에게 추가합니다.
     */
    public void addOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mSyncRecyclerViewObserver.addOnScrollChangeListener(onScrollChangeListener);
    }


    /**
     *
     * 관찰자에서 현재 컨트롤의 슬라이딩 이벤트 수신기를 제거합니다.
     */
    public void removeOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mSyncRecyclerViewObserver.removeOnScrollChangeListener(onScrollChangeListener);
    }


    /**
     * 맞춤형 슬라이딩 모니터
     */
    public interface OnScrollChangeListener {
        void onScrollChanged(int dx, int dy);
    }

    /**
     *
     * 슬라이딩과 관련된 여러 목록의 실현에 사용되는 슬라이딩 관찰자
     */
    public static class SyncRecyclerViewObserver {
        //
        //각 목록에는 리스너가 있으므로 연결된 리스너 모음을 만듭니다.
        List<OnScrollChangeListener> mList;

        public SyncRecyclerViewObserver() {
            super();
            mList = new ArrayList<>();
        }

        /**
         * 슬라이딩 리스너 추가
         *
         * @param onScrollChangeListener 슬라이딩 리스너
         */
        public void addOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
            mList.add(onScrollChangeListener);
        }

        /**
         * 슬라이딩 리스너 제거
         *
         * @param onScrollChangeListener 리스너를 스와이프하여 삭제
         */
        public void removeOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
            mList.remove(onScrollChangeListener);
        }

        /**
         * 슬라이딩 변경 사항 브로드캐스트, 각 슬라이딩 수신기에 알림
         */
        public void notifyOnScrollChange(int dx, int dy) {
            if (mList == null || mList.size() == 0) {
                return;
            }

            //
            //루프의 모든 슬라이딩 리스너에게 알림
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i) != null) {
                    mList.get(i).onScrollChanged(dx, dy);
                }
            }
        }

    }
}
