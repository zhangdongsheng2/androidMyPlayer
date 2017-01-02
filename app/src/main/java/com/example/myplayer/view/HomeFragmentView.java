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
import com.example.myplayer.adapter.baseadapter.MyMainFragmentPagerAdapter;
import com.example.myplayer.fragment.BaseFragment;
import com.example.myplayer.fragment.play.AudioListFragment;
import com.example.myplayer.fragment.play.VideoListFragment;
import com.example.myplayer.util.ViewUtils;
import com.example.myplayer.widget.StatusBarLinearLayout;
import com.example.myplayer.widget.ZoomOutPageTransformer;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.socks.library.KLog;

/**
 * Created by zhangdongsheng on 16/8/30.
 */
public class HomeFragmentView extends FrameLayout implements View.OnClickListener {

    TextView tabvideo;
    TextView tabaudio;
    View indicateline;
    ViewPager viewpager;
    StatusBarLinearLayout rlrootview;
    Fragment[] fragments = new BaseFragment[]{new VideoListFragment(), new AudioListFragment()};
    private BaseFragment mFragment;
    private int lineWidth;

    public HomeFragmentView(Context context, BaseFragment fragment) {
        super(context);
        mFragment = fragment;
        initView();
        initListener();
        initData();
    }

    public HomeFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void initView() {

        View root = ViewUtils.inflateView(R.layout.fragment_home, this);
        tabvideo = (TextView) root.findViewById(R.id.tab_video);
        tabaudio = (TextView) root.findViewById(R.id.tab_audio);
        indicateline = root.findViewById(R.id.indicate_line);
        viewpager = (ViewPager) root.findViewById(R.id.view_pager);
        rlrootview = (StatusBarLinearLayout) root.findViewById(R.id.rl_rootview);
    }


    protected void initListener() {
        tabaudio.setOnClickListener(this);
        tabvideo.setOnClickListener(this);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int targetPosition = position * lineWidth + positionOffsetPixels / fragments.length;
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

        viewpager.setAdapter(new HomePagerAdapter(mFragment.getChildFragmentManager()));
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        linghtAndScaleTabTitle();
    }

    private void calculateIndicateLineWidth() {
        int screenWidth =mFragment.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lineWidth = screenWidth / fragments.length;
        indicateline.getLayoutParams().width = lineWidth;
        indicateline.requestLayout();
    }

    private void linghtAndScaleTabTitle() {
        int currentPage = viewpager.getCurrentItem();
        tabvideo.setTextColor(currentPage == 0 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
        tabaudio.setTextColor(currentPage == 1 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
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


    public class HomePagerAdapter extends MyMainFragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }
}
