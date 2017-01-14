package com.example.myplayer.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能左右划的ViewPager
 *
 * @author Kevin
 */
public class NoScrollViewPager extends LazyViewPager {

    public RestoreListener mListener;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    // 表示事件是否拦截, 返回false表示不拦截, 可以让嵌套在内部的viewpager相应左右划的事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    /**
     * 重写onTouchEvent事件,什么都不用做
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    public void setOnRestoreListener(RestoreListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (mListener != null) {
            mListener.onResotreFinish();
        }
    }

    public interface RestoreListener {
        public void onResotreFinish();
    }
}
