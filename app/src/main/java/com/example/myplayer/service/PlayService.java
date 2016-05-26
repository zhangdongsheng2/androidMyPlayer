package com.example.myplayer.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.myplayer.R;
import com.example.myplayer.util.ViewUtils;

public class PlayService extends Service {

    public static final String POSITION = "position";
    public static final String VIDEOLIST = "videolist";
    private final int MSG_UPDATE_SYSTEM_TIME = 0;//更新系统时间
    private final int MSG_UPDATE_PLAY_PROGRESS = 1;//更新播放进度
    private final int MSG_HIDE_CONTROL = 2;//延时隐藏控制面板


    private WindowManager wm;
    private View mView;

    private int mScreenWidth;
    private int mScreenHeight;

    private int startX;
    private int startY;
    private WindowManager.LayoutParams mParams;
    private int moveX;
    private int moveY;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        mView = ViewUtils.inflateView(R.layout.service_video_play);
        int width = wm.getDefaultDisplay().getWidth() - 90;
        int height = (int) (width * 0.6);

        mParams = new WindowManager.LayoutParams();
        mParams.height = height;
        mParams.width = width;

        // 修改完左上角对其
        mParams.gravity = Gravity.LEFT + Gravity.TOP;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mParams.format = PixelFormat.TRANSPARENT;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wm.addView(mView, mParams);

        mView.setOnTouchListener(touchListener);

//        initView();
//        initListener();
//        initData();
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) event.getRawX();
                    moveY = (int) event.getRawY();
                    mParams.x = moveX - startX;
                    mParams.y = moveY - startY;
                    wm.updateViewLayout(mView, mParams);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
    };


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mParams.y = msg.arg1;
            wm.updateViewLayout(mView, mParams);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wm != null && mView != null) {
            wm.removeView(mView);// 从窗口移除布局
        }
    }
}
