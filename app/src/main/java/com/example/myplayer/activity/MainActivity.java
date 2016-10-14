package com.example.myplayer.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.util.ToastUtil;


/**
 * Created by Administrator on 2016/3/23.
 */
public class MainActivity extends BaseActivity {

    private long exitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏  透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        doubleExit();
    }


    /**
     * 双击退出
     */
    private void doubleExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
