package com.example.myplayer.ui.fragment;

import android.support.v4.app.FragmentManager;

import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.ui.fragment.play.AudioListFragment;
import com.example.myplayer.ui.fragment.play.VideoListFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int TAB_HOME = 0;
    public static final int TAB_APP = 1;
    public static final int TAB_GAME = 2;
    public static final int TAB_SUBJECT = 3;
    public static final int TAB_RECOMMEND = 4;
    public static final int MAINFRAGMENTMAP_LENGHT = 5;
    public static final int HOMEFRAGMENTMAP_LENGHT = 2;
    /**
     * 记录所有的fragment，防止重复创建
     */
    private static Map<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();
    private static Map<Integer, BaseFragment> mHomeFragmentMap = new HashMap<Integer, BaseFragment>();


    /**
     * 采用工厂类进行创建Fragment，便于扩展，已经创建的Fragment不再创建
     */
    public static BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragmentMap.get(index);

        if (fragment == null) {
            switch (index) {
                case TAB_HOME:
                    fragment = new HomeFragment();
                    break;
                case TAB_APP:
                    fragment = new NewsFragment();
                    break;
                case TAB_GAME:
                    fragment = new GoyFragment();
                    break;
                case TAB_SUBJECT:
                    fragment = new OtherFragment();
                    break;
                case TAB_RECOMMEND:
                    fragment = new SetingFragment();
                    break;
            }
            mFragmentMap.put(index, fragment);
        }
        return fragment;
    }

    /**
     * 采用工厂类进行创建Fragment，便于扩展，已经创建的Fragment不再创建
     */
    public static BaseFragment createHomeFragment(int index) {
        BaseFragment fragment = mHomeFragmentMap.get(index);

        if (fragment == null) {
            switch (index) {
                case TAB_HOME:
                    fragment = new VideoListFragment();
                    break;
                case TAB_APP:
                    fragment = new AudioListFragment();
                    break;
            }
            mHomeFragmentMap.put(index, fragment);
        }
        return fragment;
    }


}
