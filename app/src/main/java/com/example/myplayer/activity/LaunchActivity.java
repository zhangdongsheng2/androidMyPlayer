package com.example.myplayer.activity;

import android.content.Intent;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;


/**
 * 应用启动界面
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected int getContentView() {

        doMerge();
        return R.layout.app_start;

    }


    private void doMerge() {

        // Delay...
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 完成后进行跳转操作
        redirectTo();
    }

    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
