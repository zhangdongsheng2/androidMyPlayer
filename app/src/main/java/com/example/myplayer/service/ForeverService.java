package com.example.myplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.socks.library.KLog;

/**
 * Created by zhangdongsheng on 2016/10/13.
 */

public class ForeverService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentInfo("补充内容");
//        builder.setContentText("主内容区");
//        builder.setContentTitle("通知标题");
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        builder.setTicker("新消息");
//        builder.setAutoCancel(true);
//        builder.setWhen(System.currentTimeMillis());
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
//        startForeground(1235, notification);//该方法已创建通知管理器，设置为前台优先级后，点击通知不再自动取消
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(2000);
                    KLog.e("还在================");
                }
            }
        }.start();


//        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
//
//        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
//        registerReceiver(receiver, filter);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = Service.START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.e("=========destroy====");
    }

}
