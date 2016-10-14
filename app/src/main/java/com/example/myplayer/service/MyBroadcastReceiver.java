package com.example.myplayer.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.socks.library.KLog;

/**
 * Created by zhangdongsheng on 2016/10/13.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isServiceRunning = false;

        KLog.e("MyBroadcastReceiver================");
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

            //检查Service状态

            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("scom.example.myplayer.service.ForeverService".equals(service.service.getClassName()))

                {
                    isServiceRunning = true;
                }

            }
            if (!isServiceRunning) {
                Intent i = new Intent(context, ForeverService.class);
                context.startService(i);
            }


        }
    }
}
