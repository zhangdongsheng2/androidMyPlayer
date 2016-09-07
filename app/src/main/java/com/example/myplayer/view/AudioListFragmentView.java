package com.example.myplayer.view;

import android.content.Context;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.example.myplayer.R;
import com.example.myplayer.adapter.AudioListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by zhangdongsheng on 2016/9/7.
 */
public class AudioListFragmentView extends FrameLayout {

    private static AudioListFragmentView mView;
    private RecyclerView listView;
    private AudioListAdapter adapter;
    private BaseFragment mFragment;

    public AudioListFragmentView(Context context, BaseFragment fragment) {
        super(context);
        mFragment = fragment;
        initView();
        initData();
    }

    public static synchronized AudioListFragmentView getInstance(Context context, BaseFragment fragment) {
        if (mView == null) {
            mView = new AudioListFragmentView(context, fragment);
        }
        return mView;
    }


    protected void initView() {
        ViewUtils.inflateView(R.layout.fragment_video, this);
    }


    protected void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(mFragment.getActivity().getContentResolver());//对数据库增删改查的异步框架  官方
        listView = ((RecyclerView) findViewById(R.id.listview));
        adapter = new AudioListAdapter(getContext(), null, mFragment);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {Media._ID, Media.DISPLAY_NAME, Media.ARTIST, Media.DATA, Media.DURATION};
        queryHandler.startQuery(0, adapter,  Media.EXTERNAL_CONTENT_URI, projection, null, null, null);



    }
}
