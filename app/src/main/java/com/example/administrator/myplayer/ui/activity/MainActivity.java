package com.example.administrator.myplayer.ui.activity;

import android.view.View;
import android.view.WindowManager;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.base.BaseActivity;
import com.example.administrator.myplayer.base.BaseFragment;
import com.example.administrator.myplayer.ui.fragment.SplashFragment;


/**
 * Created by Administrator on 2016/3/23.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void initView() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_splash);
        new BaseFragment().launch(false, R.id.content, SplashFragment.class, false);
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
