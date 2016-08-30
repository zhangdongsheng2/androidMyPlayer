package com.example.myplayer.ui.fragment;

import android.view.View;

import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.ui.myview.HomeFragmentView;

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
public class HomeFragment extends BaseFragment {
    @Override
    protected View initView() {
        return HomeFragmentView.getInstance(getContext(), this);
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
