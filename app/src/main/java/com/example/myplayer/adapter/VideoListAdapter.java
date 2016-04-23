package com.example.myplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.recyclerview.RecyclerViewCursorAdapter;
import com.example.myplayer.ui.activity.VoidPlayActivity;
import com.example.myplayer.util.DateUtil;
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
          视频列表适配器
          @author ZDS
          create on 2016/4/2  18:32 */

public class VideoListAdapter extends RecyclerViewCursorAdapter<VideoListAdapter.MyViewHolder> {
    private BaseFragment mBaseFragment;

    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter;
     *                Currently it accept {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public VideoListAdapter(Context context, Cursor c, int flags, BaseFragment baseFragment) {
        super(context, c, flags);
        this.mBaseFragment = baseFragment;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final Cursor cursor, final int position) {
        VideoItem videoItem = VideoItem.fromCursor(cursor);
        holder.tvTitle.setText(videoItem.getTitle());
        holder.tvDuration.setText(DateUtil.formatVideoDuration(videoItem.getDuration()));
        holder.tvSize.setText(Formatter.formatFileSize(mContext, videoItem.getSize()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("currentPosition", position);
                bundle.putSerializable("videoList", cursorToList(getCursor()));
                mBaseFragment.launch(true,VoidPlayActivity.class, bundle);
            }
        });
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

    @Override
    protected void onContentChanged() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ViewUtils.inflateView(R.layout.adapter_void_item);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDuration;
        private TextView tvSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}
