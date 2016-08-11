package com.example.myplayer.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.ui.fragment.SplashFragment;
import com.example.myplayer.util.ViewUtils;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/3/23.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏  透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        ViewUtils.launch(false, R.id.content, SplashFragment.class, false, null,null);

    }
}
