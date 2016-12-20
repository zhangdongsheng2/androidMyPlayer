package com.example.myplayer.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.activity.VitamioPlayActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MeditationFragment extends BaseFragment {


    private RecyclerView listview;
    private ArrayList<String> objects;

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_audio);
    }

    @Override
    protected void initListener() {
        listview = (RecyclerView) mRootView.findViewById(R.id.listview);
        objects = new ArrayList<String>() {{
            add("str01");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
            add("str02");
        }};
    }

    @Override
    protected void initData() {
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        listview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        listview.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = ViewUtils.inflateView(R.layout.adapter_void_item);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), VitamioPlayActivity.class);
                        String video = "http://kscdn.ku6.com/v505/34/61/e89b5fb0a577c8827d0d609a35e05d9f-f4v-h264-aac-238-32-131704.0-4528945-1482060613818-250b0e7fe54ed716ba691a5adab2504f-1-00-00-00.f4v.mp4?AccessKeyId=nhYNYRxiQI%2BTNpwVdAH3&Expires=1482238446&Signature=6MIxcZiYpR4p6IRvh1e%2FNBp%2F1sI%3D&";
                        intent.setDataAndType(Uri.parse(video), "video/*");
                        startActivity(intent);
                    }
                });
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tvTitle.setText(objects.get(position));
            }

            @Override
            public int getItemCount() {
                return objects.size();
            }
        });
    }

    @Override
    protected void processClick(View view) {

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
