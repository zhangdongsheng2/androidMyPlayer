package com.example.myplayer.ui.fragment.play;

import android.view.View;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.util.ViewUtils;

import java.util.ArrayList;

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
public class VoidPlayFragment extends BaseFragment {
    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_video_play);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        int currentPosition = getArguments().getInt("currentPosition");
        ArrayList<VideoItem> videoList = (ArrayList<VideoItem>) getArguments().getSerializable("videoList");

        TextView viewById = (TextView) mRootView.findViewById(R.id.tv_demo);
        viewById.setText(currentPosition + "===" + videoList.size());

    }

    @Override
    protected void processClick(View view) {

    }
}
