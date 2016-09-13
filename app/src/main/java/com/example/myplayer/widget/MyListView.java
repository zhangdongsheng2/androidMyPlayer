package com.example.myplayer.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * 上下对Item进行拉伸的ListView
 * Created by zhangdongsheng on 16/9/13.
 */
public class MyListView extends ListView {
    private Set<View> list = new HashSet<>();

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    /**
     * 当listview 滚动到顶部的时候，还要下拉，还要网上滚动，那么这时就会调用该方法
     *
     * @param deltaX
     * @param deltaY
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // 滑动过头的时候回调该方法
        // 控制 imageview 的高度逐渐增加------从而达到滚动图片放大的效果
        if (isTouchEvent && deltaY < 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);

                int oldHeight = view.getHeight();
                int newHeight = oldHeight + Math.abs(deltaY) / 17;

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = newHeight;
                view.setLayoutParams(layoutParams);
                list.add(view);
            }
        } else if (deltaY > 0) {
            for (int i = 0; i < getChildCount() - 1; i++) {
                setSelection(getBottom());
                View view = getChildAt(i + 1);

                int oldHeight = view.getHeight();
                int newHeight = oldHeight + Math.abs(deltaY) / 17;

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = newHeight;
                view.setLayoutParams(layoutParams);
                list.add(view);
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            for (final View view : list) {
                final int startHeight = view.getHeight();
                // 模拟一个值改变"动画", 0.0f - 1.0f
                ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
                animator.setDuration(500);
                animator.setInterpolator(new OvershootInterpolator(1.0f));
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float percent = animation.getAnimatedFraction();
                        Integer height = evaluateInt(percent, startHeight, 162);
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = height;
                        view.setLayoutParams(layoutParams);
                    }
                });
                animator.start();
            }


        }
        return super.onTouchEvent(ev);
    }
}
