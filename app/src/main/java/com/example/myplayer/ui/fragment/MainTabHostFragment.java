package com.example.myplayer.ui.fragment;

import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.myplayer.Const;
import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.ui.view.MainTab;
import com.example.myplayer.ui.view.MyFragmentTabHost;
import com.example.myplayer.util.ViewUtils;

import butterknife.ButterKnife;
import interf.OnTabReselectListener;

/**
 * Created by zhangdongsheng on 16/8/30.
 */
public class MainTabHostFragment extends BaseFragment {

    //    @BindView(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;
    //    @BindView(R.id.quick_option_iv)
    View mAddBt;
    /**
     * Used to store the last screen title. For use in
     * {@link #()}.
     */
    private CharSequence mTitle;

    @Override
    protected View initView() {
        View view = ViewUtils.inflateView(R.layout.fragment_tabhost_content);

        ButterKnife.bind(this, view);
        mTabHost = (MyFragmentTabHost) view.findViewById(android.R.id.tabhost);
        mAddBt = view.findViewById(R.id.quick_option_iv);
        mTitle = getResources().getString(R.string.main_tab_name_news);
        mTabHost.setup(getContext(), getChildFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        initTabs();

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
//                if (tabId.equals(getString(MainTab.ME.getResName()))) {//点击时右上角至空
//                    mBvNotice.setText("");
//                    mBvNotice.hide();
//                }
                mTitle = tabId;
//                supportInvalidateOptionsMenu();//重绘制actionBar
            }
        });

        IntentFilter filter = new IntentFilter(Const.INTENT_ACTION_NOTICE);
        filter.addAction(Const.INTENT_ACTION_LOGOUT);
//        registerReceiver(mReceiver, filter);
//        NoticeUtils.bindToService(this);

//        if (AppContext.isFristStart()) {   //Sp 中判断程序是否第一次启动
//            DataCleanManager.cleanInternalCache(AppContext.getInstance());
//            AppContext.setFristStart(false);
//        }

        return view;
    }

    @SuppressWarnings("deprecation")
    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = View.inflate(getActivity(), R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            //title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
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

//            if (mainTab.equals(MainTab.ME)) {  //右上角的数字图标
//                View cn = indicator.findViewById(R.id.tab_mes);
//                mBvNotice = new BadgeView(getActivity(), cn);
//                mBvNotice.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                mBvNotice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//                mBvNotice.setBackgroundResource(R.drawable.notification_bg);
//                mBvNotice.setGravity(Gravity.CENTER);
//            }
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean consumed = false;
                    // use getTabHost().getCurrentTabView to decide if the current tab is
                    // touched again
                    if (event.getAction() == MotionEvent.ACTION_DOWN
                            && v.equals(mTabHost.getCurrentTabView())) {
                        // use getTabHost().getCurrentView() to get a handle to the view
                        // which is displayed in the tab - and to get this views context
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
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void processClick(View view) {

    }
}
