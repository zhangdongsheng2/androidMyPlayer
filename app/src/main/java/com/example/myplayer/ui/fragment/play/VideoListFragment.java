package com.example.myplayer.ui.fragment.play;

import android.provider.MediaStore.Video.Media;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.adapter.VideoListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.util.ViewUtils;

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
---------------------------------------------
          视频列表
           @author ZDS
            create on 2016/4/2  16:09 */

public class VideoListFragment extends BaseFragment {

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_video);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        RecyclerView listView = ((RecyclerView) mRootView.findViewById(R.id.listview));
        VideoListAdapter adapter = new VideoListAdapter(getActivity(), null, 0);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listView.setAdapter(adapter);
        String[] projection = {Media._ID, Media.TITLE, Media.SIZE, Media.DURATION, Media.DATA};
        queryHandler.startQuery(0, adapter, Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
    }

    @Override
    protected void processClick(View view) {

    }
}
