package com.example.myplayer.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainThreeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.nav_item_news)
    MainThreeNavigationButton mNavNews;
    @BindView(R.id.nav_item_tweet)
    MainThreeNavigationButton mNavTweet;
    @BindView(R.id.nav_item_explore)
    MainThreeNavigationButton mNavExplore;
    @BindView(R.id.nav_item_me)
    MainThreeNavigationButton mNavMe;


    private int mContainerId;
    private FragmentManager mFragmentManager;
    private MainThreeNavigationButton mCurrentNavButton;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_three;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget() {
        ButterKnife.bind(this, mRootView);

        mNavNews.init(R.drawable.tab_icon_new,
                R.string.main_tab_name_news,
                BodhiFragment.class);

        mNavTweet.init(R.drawable.tab_icon_tweet,
                R.string.main_tab_name_tweet,
                NirvanaFragment.class);

        mNavExplore.init(R.drawable.tab_icon_explore,
                R.string.main_tab_name_explore,
                MeditationFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_my,
                SixVirtuesFragment.class);


        mFragmentManager = getChildFragmentManager();
        mContainerId = R.id.realtabcontent;
        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavNews);
    }

    @OnClick({R.id.nav_item_news, R.id.nav_item_tweet,
            R.id.nav_item_explore, R.id.nav_item_me,
            R.id.nav_item_tweet_pub})
    @Override
    public void onClick(View v) {
        if (v instanceof MainThreeNavigationButton) {
            MainThreeNavigationButton nav = (MainThreeNavigationButton) v;
            doSelect(nav);
        } else if (v.getId() == R.id.nav_item_tweet_pub) {
            ToastUtil.showToast("中间");
        }
    }


    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(MainThreeNavigationButton newNavButton) {
        MainThreeNavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(MainThreeNavigationButton oldNavButton, MainThreeNavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }


    private void onReselect(MainThreeNavigationButton navigationButton) {
        ToastUtil.showToast("再次点击");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
