package com.example.myplayer.fragment.play;

import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.adapter.AudioListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by Administrator on 2016/3/31.
 */
public class AudioListFragment extends BaseFragment {
    private RecyclerView listView;
    private AudioListAdapter adapter;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_video);
    }

    @Override
    protected void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getContext().getContentResolver());//对数据库增删改查的异步框架  官方
        listView = ((RecyclerView) mRootView.findViewById(R.id.listview));
        adapter = new AudioListAdapter(getContext(), null, this);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DURATION};
        queryHandler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);


    }
}
