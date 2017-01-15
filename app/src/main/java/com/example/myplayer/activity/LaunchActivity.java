package com.example.myplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.myplayer.R;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.RxBus;

import rx.functions.Action1;


/**
 * 应用启动界面
 */
public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtil.runOnThread(new Runnable() {
            @Override
            public void run() {
                doMerge();
            }
        });
        RxBus.getInstance().toObservable(Object.class, "finish").subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                finish();
            }
        });
        setContentView(R.layout.app_start);
    }


    private void doMerge() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 完成后进行跳转
        redirectTo();
    }

    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
