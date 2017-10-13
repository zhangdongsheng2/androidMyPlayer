package com.example.myplayer.activity.second;

import android.os.Handler;

import com.example.myplayer.R;
import com.example.myplayer.activity.BaseActivity;
import com.example.myplayer.widget.BalloonRelativeLayout;

/**
 * Created by zhangdongsheng on 2017/10/13.
 */

public class BlowingBubblesActivity extends BaseActivity {
    Handler mHandler = new Handler();
    private int TIME = 100;//这里默认每隔100毫秒添加一个气泡
    private BalloonRelativeLayout mBalloonRelativeLayout;
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                mHandler.postDelayed(this, TIME);
                mBalloonRelativeLayout.addBalloon();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_blowing_bubbles;
    }

    @Override
    protected void initWidget() {
        mBalloonRelativeLayout = (BalloonRelativeLayout) findViewById(R.id.balloonRelativeLayout);
        mHandler.postDelayed(runnable, TIME);
    }
}
