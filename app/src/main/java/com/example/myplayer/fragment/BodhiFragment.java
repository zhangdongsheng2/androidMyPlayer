package com.example.myplayer.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.adapter.baseadapter.MyMainFragmentPagerAdapter;
import com.example.myplayer.fragment.play.AudioListFragment;
import com.example.myplayer.fragment.play.VideoListFragment;
import com.example.myplayer.widget.StatusBarLinearLayout;
import com.example.myplayer.widget.ZoomOutPageTransformer;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.socks.library.KLog;

/*
          主页面
           @author ZDS
           create on 2016/3/31 15:01 */
public class BodhiFragment extends BaseFragment implements View.OnClickListener {

    TextView tabvideo;
    TextView tabaudio;
    View indicateline;
    ViewPager viewpager;
    StatusBarLinearLayout rlrootview;
    Fragment[] fragments = new BaseFragment[]{new VideoListFragment(), new AudioListFragment()};
    private int lineWidth;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bodhi;
    }


    @Override
    protected void initWidget() {
        tabvideo = (TextView) mRootView.findViewById(R.id.tab_video);
        tabaudio = (TextView) mRootView.findViewById(R.id.tab_audio);
        indicateline = mRootView.findViewById(R.id.indicate_line);
        viewpager = (ViewPager) mRootView.findViewById(R.id.view_pager);
        rlrootview = (StatusBarLinearLayout) mRootView.findViewById(R.id.rl_rootview);

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

    private void linghtAndScaleTabTitle() {
        int currentPage = viewpager.getCurrentItem();
        tabvideo.setTextColor(currentPage == 0 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
        tabaudio.setTextColor(currentPage == 1 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.white));
        ViewPropertyAnimator.animate(tabvideo).scaleX(currentPage == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabvideo).scaleY(currentPage == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabaudio).scaleX(currentPage == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tabaudio).scaleY(currentPage == 1 ? 1.2f : 1).setDuration(200);
    }

    private void calculateIndicateLineWidth() {
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lineWidth = screenWidth / fragments.length;
        indicateline.getLayoutParams().width = lineWidth;
        indicateline.requestLayout();
    }

    @Override
    protected void initData() {
        calculateIndicateLineWidth();

        viewpager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        linghtAndScaleTabTitle();
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
