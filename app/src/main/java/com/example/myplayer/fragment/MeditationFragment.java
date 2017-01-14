package com.example.myplayer.fragment;

import com.example.myplayer.R;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by Administrator on 2016/3/31.
 * Three
 */
public class MeditationFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meditation;
    }

    @Override
    protected void initWidget() {
        DatePicker picker = (DatePicker) mRootView.findViewById(R.id.main_dp);
        picker.setDate(2015, 10);
        picker.setMode(DPMode.SINGLE);
    }

    @Override
    protected void initData() {

    }
}
