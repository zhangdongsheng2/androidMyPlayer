package com.example.myplayer.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.ViewUtils;
import com.example.myplayer.widget.MainTab;
import com.example.myplayer.widget.MyFragmentTabHost;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import interf.OnTabReselectListener;

/**
 * Created by zhangdongsheng on 16/8/30.
 */
public class MainTabHostFragment extends BaseFragment {

    @BindView(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;
    @BindView(R.id.quick_option_iv)
    View mAddBt;


    private CharSequence mTitle;

    @Override
    protected View initView() {
        View view = ViewUtils.inflateView(R.layout.fragment_tabhost_content);
        ButterKnife.bind(this, view);
        mTitle = getResources().getString(R.string.main_tab_name_news);
        mTabHost.setup(getContext(), getChildFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        // 中间按键图片触发
        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                final int size = mTabHost.getTabWidget().getTabCount();
                for (int i = 0; i < size; i++) {
                    View v = mTabHost.getTabWidget().getChildAt(i);
                    if (i == mTabHost.getCurrentTab()) {
                        v.setSelected(true);
                    } else {
                        v.setSelected(false);
                    }
                }
                if (tabId.equals(getString(MainTab.ME.getResName()))) {//点击时右上角至空

                }
                mTitle = tabId;
            }
        });
        return view;
    }

    @Override
    protected void initListener() {

    }

    private void setMargin(View view) {
        //Button 压边是 放大的效果
        ViewHelper.setScaleX(view, 1.2f);
        ViewHelper.setScaleY(view, 1.2f);
    }

    @Override
    protected void initData() {
        MainTab[] tabs = MainTab.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = View.inflate(getActivity(), R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);
            setMargin(indicator);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(getActivity());
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);

            if (mainTab.equals(MainTab.ME)) {  //右上角的数字图标

            }
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean consumed = false;
                    if (event.getAction() == MotionEvent.ACTION_DOWN
                            && v.equals(mTabHost.getCurrentTabView())) {
                        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentByTag(
                                mTabHost.getCurrentTabTag());
                        if (currentFragment != null
                                && currentFragment instanceof OnTabReselectListener) {
                            OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                            listener.onTabReselect();//当再次点击时刷新页面联网
                            consumed = true;
                        }
                    }
                    return consumed;
                }
            });
        }
    }

    @Override
    protected void processClick(View view) {

    }
}
