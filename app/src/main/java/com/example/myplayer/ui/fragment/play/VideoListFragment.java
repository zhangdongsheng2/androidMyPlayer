package com.example.myplayer.ui.fragment.play;

import android.database.Cursor;
import android.provider.MediaStore.Video.Media;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.adapter.VideoListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.recyclerview.DividerItemDecoration;
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
    protected void initListener() {

    }

    @Override
    protected void initData() {

        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        listView = ((RecyclerView) mRootView.findViewById(R.id.listview));
        adapter = new VideoListAdapter(getActivity(), null, 0, this);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {Media._ID, Media.TITLE, Media.SIZE, Media.DURATION, Media.DATA};
        queryHandler.startQuery(0, adapter, Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
    }

    @Override
    protected void processClick(View view) {
    }

    /**
     * 将cursor中的数据取出来放入集合当中
     *
     * @param cursor
     * @return
     */
    private ArrayList<VideoItem> cursorToList(Cursor cursor) {
        ArrayList<VideoItem> list = new ArrayList<VideoItem>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            list.add(VideoItem.fromCursor(cursor));
        }
        return list;
    }
}
