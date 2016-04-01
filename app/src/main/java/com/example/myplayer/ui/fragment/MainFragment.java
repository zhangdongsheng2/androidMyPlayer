package com.example.myplayer.ui.fragment;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.adapter.MainPagerAdapter;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.ui.fragment.play.AudioListFragment;
import com.example.myplayer.ui.fragment.play.VideoListFragment;
import com.example.myplayer.ui.view.StatusBarLinearLayout;
import com.example.myplayer.util.ViewUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
----------------------------------------------
          主页面
           @author ZDS
           create on 2016/3/31 15:01 */
public class MainFragment extends BaseFragment {

    TextView tabvideo;
    TextView tabaudio;
    View indicateline;
    ViewPager viewpager;
    StatusBarLinearLayout rlrootview;


    private MainPagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int lineWidth;

    @Override
    protected View initView() {
        View root = ViewUtils.inflateView(R.layout.fragment_main);
        tabvideo = (TextView) root.findViewById(R.id.tab_video);
        tabaudio = (TextView) root.findViewById(R.id.tab_audio);
        indicateline = (View) root.findViewById(R.id.indicate_line);
        viewpager = (ViewPager) root.findViewById(R.id.view_pager);
        rlrootview = (StatusBarLinearLayout) root.findViewById(R.id.rl_rootview);
        return root;
    }

    @Override
    protected void initListener() {
        tabaudio.setOnClickListener(this);
        tabvideo.setOnClickListener(this);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int targetPosition = position * lineWidth + positionOffsetPixels / fragments.size();
                ViewPropertyAnimator.animate(indicateline).translationX(targetPosition).setDuration(0);
            }

            @Override
            public void onPageSelected(int position) {
                linghtAndScaleTabTitle();

            }
        });
    }


    @Override
    protected void initData() {
        fragments.add(new VideoListFragment());
        fragments.add(new AudioListFragment());

        calculateIndicateLineWidth();

        adapter = new MainPagerAdapter(getFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        linghtAndScaleTabTitle();
    }

    private void calculateIndicateLineWidth() {
        int screenWidth = BaseActivity.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lineWidth = screenWidth / fragments.size();
        indicateline.getLayoutParams().width = lineWidth;
        indicateline.requestLayout();
    }

    private void linghtAndScaleTabTitle() {
        int currentPage = viewpager.getCurrentItem();
        tabvideo.setTextColor(currentPage == 0 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.gray_white));
        tabaudio.setTextColor(currentPage == 1 ? getResources().getColor(R.color.indicate_line) : getResources().getColor(R.color.gray_white));
        ViewPropertyAnimator.animate(tabvideo).scaleX(currentPage == 0 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
        ViewPropertyAnimator.animate(tabvideo).scaleY(currentPage == 0 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
        ViewPropertyAnimator.animate(tabaudio).scaleX(currentPage == 1 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
        ViewPropertyAnimator.animate(tabaudio).scaleY(currentPage == 1 ? 1.2f : 1).setDuration(200).setInterpolator(new CycleInterpolator(1f));
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case R.id.tab_audio:
                viewpager.setCurrentItem(1);
                break;
            case R.id.tab_video:
                viewpager.setCurrentItem(0);
                break;
        }
    }
}
