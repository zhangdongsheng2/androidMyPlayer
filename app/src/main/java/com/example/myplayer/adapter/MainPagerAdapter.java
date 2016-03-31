package com.example.myplayer.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<android.app.Fragment> fragments;

    public MainPagerAdapter(FragmentManager fm, ArrayList<android.app.Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}
