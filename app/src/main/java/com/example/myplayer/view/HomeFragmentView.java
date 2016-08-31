package com.example.myplayer.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.fragment.FragmentFactory;
import com.example.myplayer.fragment.MyMainFragmentPagerAdapter;
import com.example.myplayer.widget.StatusBarLinearLayout;
import com.example.myplayer.widget.ZoomOutPageTransformer;
import com.example.myplayer.util.ViewUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.socks.library.KLog;

/**
 * Created by zhangdongsheng on 16/8/30.
 */
public class HomeFragmentView extends FrameLayout implements View.OnClickListener {

    private static HomeFragmentView mView;
    TextView tabvideo;
    TextView tabaudio;
    View indicateline;
    ViewPager viewpager;
    StatusBarLinearLayout rlrootview;
    private BaseFragment mFragment;
    private int lineWidth;

    private HomeFragmentView(Context context, BaseFragment fragment) {
        super(context);
        mFragment = fragment;
        initView();
        initListener();
        initData();
    }

    private HomeFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private HomeFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static synchronized HomeFragmentView getInstance(Context context, BaseFragment fragment) {
        if (mView == null) {
            mView = new HomeFragmentView(context, fragment);
        }
        return mView;
    }

    protected void initView() {

        View root = ViewUtils.inflateView(R.layout.fragment_home, this);
        tabvideo = (TextView) root.findViewById(R.id.tab_video);
        tabaudio = (TextView) root.findViewById(R.id.tab_audio);
        indicateline = (View) root.findViewById(R.id.indicate_line);
        viewpager = (ViewPager) root.findViewById(R.id.view_pager);
        rlrootview = (StatusBarLinearLayout) root.findViewById(R.id.rl_rootview);
    }


    protected void initListener() {
        tabaudio.setOnClickListener(this);
        tabvideo.setOnClickListener(this);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int targetPosition = position * lineWidth + positionOffsetPixels / FragmentFactory.HOMEFRAGMENTMAP_LENGHT;
                ViewPropertyAnimator.animate(indicateline).translationX(targetPosition).setDuration(0);
            }

            @Override
            public void onPageSelected(int position) {
                linghtAndScaleTabTitle();

            }
        });
    }


    protected void initData() {

        calculateIndicateLineWidth();

//        adapter = new MainPagerAdapter(BaseActivity.getActivity().getSupportFragmentManager());
        viewpager.setAdapter(new HomePagerAdapter(mFragment.getChildFragmentManager()));
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        linghtAndScaleTabTitle();
    }

    private void calculateIndicateLineWidth() {
        int screenWidth = BaseActivity.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lineWidth = screenWidth / FragmentFactory.HOMEFRAGMENTMAP_LENGHT;
        indicateline.getLayoutParams().width = lineWidth;
        indicateline.requestLayout();
    }

    private void linghtAndScaleTabTitle() {
        int currentPage = viewpager.getCurrentItem();
        tabvideo.setTextColor(currentPage == 0 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
        tabaudio.setTextColor(currentPage == 1 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
//        ViewPropertyAnimator.animate(tabvideo).scaleX(currentPage == 0 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
//        ViewPropertyAnimator.animate(tabvideo).scaleY(currentPage == 0 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
//        ViewPropertyAnimator.animate(tabaudio).scaleX(currentPage == 1 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
//        ViewPropertyAnimator.animate(tabaudio).scaleY(currentPage == 1 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
        ViewPropertyAnimator.animate(tabvideo).scaleX(currentPage == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabvideo).scaleY(currentPage == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabaudio).scaleX(currentPage == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabaudio).scaleY(currentPage == 1 ? 1.2f : 1).setDuration(200);
    }


    @Override
    public void onClick(View view) {
        KLog.e(viewpager.getAdapter());
        KLog.e(viewpager.getCurrentItem());
        switch (view.getId()) {
            case R.id.tab_audio:
                viewpager.setCurrentItem(1);
                break;
            case R.id.tab_video:
                viewpager.setCurrentItem(0);
                break;
        }
    }


    public static class HomePagerAdapter extends MyMainFragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return FragmentFactory.HOMEFRAGMENTMAP_LENGHT;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createHomeFragment(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }
}
