package com.example.myplayer;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by zhangdongsheng on 16/9/4.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private static Context mContext;
    private static Handler handler;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler();
        mContext = getApplicationContext();
    }

}
