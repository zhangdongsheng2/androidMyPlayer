package com.example.myplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.recyclerview.RecyclerViewCursorAdapter;
import com.example.myplayer.activity.VideoPlayerActivity;
import com.example.myplayer.activity.VitamioPlayActivity;
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
//                Intent intent = new Intent(BaseActivity.getActivity(), Video。PlayerActivity.class);
                Intent intent = new Intent(BaseActivity.getActivity(), VitamioPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(VideoPlayerActivity.POSITION, position);
                bundle.putSerializable(VideoPlayerActivity.VIDEOLIST, cursorToList(getCursor()));
                intent.putExtras(bundle);
                mBaseFragment.startActivity(intent);

//                Intent intent = new Intent(BaseActivity.getActivity(), PlayService.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt(VideoPlayerActivity.POSITION, position);
//                bundle.putSerializable(VideoPlayerActivity.VIDEOLIST, cursorToList(getCursor()));
//                intent.putExtras(bundle);
//                mBaseFragment.getActivity().startService(intent);


//                WindowManager  wm = (WindowManager) mBaseFragment.getActivity().getSystemService(Context.WINDOW_SERVICE);
//
//                TextView textView = new TextView(BaseActivity.getActivity());
//                textView.setText("aaaaaaaaaaaa");
//                textView.setTextColor(Color.WHITE);
//                textView.setTextSize(33);
//
//               View  mView = textView;
//                WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
//                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//                // 修改完左上角对其
//                mParams.gravity = Gravity.LEFT + Gravity.TOP;
//                mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//                mParams.format = PixelFormat.TRANSLUCENT;
//                mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                wm.addView(mView, mParams);
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
