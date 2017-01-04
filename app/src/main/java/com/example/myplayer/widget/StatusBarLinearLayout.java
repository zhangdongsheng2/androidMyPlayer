package com.example.myplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.myplayer.util.ViewUtils;
/*
         佛祖保佑       永无BUG
         Padding 状态栏高度的View
           @author ZDS
           create on 2016/3/30  22:17 */

public class StatusBarLinearLayout extends LinearLayout {
    public StatusBarLinearLayout(Context context) {
        this(context, null);
    }

    public StatusBarLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusBarLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setPadding(getPaddingLeft(), getPaddingTop() + ViewUtils.getStatusBarHeight(getContext()), getPaddingRight(), getPaddingBottom());
    }
}
