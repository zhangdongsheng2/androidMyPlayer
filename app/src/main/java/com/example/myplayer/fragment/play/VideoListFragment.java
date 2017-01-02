package com.example.myplayer.fragment.play;

import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.adapter.VideoListAdapter;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.fragment.BaseFragment;
import com.example.myplayer.recyclerview.DividerItemDecoration;
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
    private RecyclerView listView;
    private VideoListAdapter adapter;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_video);
    }

    @Override
    protected void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getContext().getContentResolver());//对数据库增删改查的异步框架  官方
        listView = ((RecyclerView) mRootView.findViewById(R.id.listview));
        adapter = new VideoListAdapter(getContext(), null, this);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};
        queryHandler.startQuery(0, adapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
    }
}
