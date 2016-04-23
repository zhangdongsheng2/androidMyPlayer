package com.example.myplayer.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.myplayer.R;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
----------------------------------------------
           视频播放页面
           @author ZDS
           create on 2016/4/3 19:18 */
public class VoidPlayActivity extends Activity {
    /**
     * 初始化View 控件
     */
    private void initView() {
        setContentView(R.layout.fragment_video_play);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    protected void initData() {
//        int currentPosition = getArguments().getInt("currentPosition");
//        ArrayList<VideoItem> videoList = (ArrayList<VideoItem>) getArguments().getSerializable("videoList");

    }


    protected void processClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
