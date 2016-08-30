package com.example.myplayer.ui.myview;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.myplayer.R;
import com.example.myplayer.adapter.VideoListAdapter;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.db.SimpleQueryHandler;
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

import java.util.ArrayList;

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

    public VideoListFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoListFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        SimpleQueryHandler queryHandler = new SimpleQueryHandler(mFragment.getActivity().getContentResolver());
        listView = ((RecyclerView) findViewById(R.id.listview));
        adapter = new VideoListAdapter(getContext(), null, 0, mFragment);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listView.setAdapter(adapter);
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};
        queryHandler.startQuery(0, adapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
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
