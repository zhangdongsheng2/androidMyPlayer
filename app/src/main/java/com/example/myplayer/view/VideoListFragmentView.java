package com.example.myplayer.view;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.example.myplayer.R;
import com.example.myplayer.adapter.VideoListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by zhangdongsheng on 16/8/30.
 */
public class VideoListFragmentView extends FrameLayout {

    private static VideoListFragmentView mView;
    private RecyclerView listView;
    private VideoListAdapter adapter;
    private BaseFragment mFragment;

    public VideoListFragmentView(Context context, BaseFragment fragment) {
        super(context);
        mFragment = fragment;
        initView();
        initData();
    }

    public static synchronized VideoListFragmentView getInstance(Context context, BaseFragment fragment) {
        if (mView == null) {
            mView = new VideoListFragmentView(context, fragment);
        }
        return mView;
    }

    protected void initView() {
        ViewUtils.inflateView(R.layout.fragment_video, this);
    }


    protected void initData() {

        SimpleQueryHandler queryHandler = new SimpleQueryHandler(mFragment.getActivity().getContentResolver());//对数据库增删改查的异步框架  官方
        listView = ((RecyclerView) findViewById(R.id.listview));
        adapter = new VideoListAdapter(getContext(), null, mFragment);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};
        queryHandler.startQuery(0, adapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
    }
}