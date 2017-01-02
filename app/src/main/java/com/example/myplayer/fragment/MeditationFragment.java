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
import com.example.myplayer.recyclerview.DividerItemDecoration;
import com.example.myplayer.util.ViewUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 * Three
 */
public class MeditationFragment extends BaseFragment {


    private RecyclerView listview;
    private ArrayList<String> objects;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audio;
    }

    @Override
    protected void initWidget() {
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
                        String video = "http://us.sinaimg.cn/003LJLe6jx06YIIuX2Wj05040100zlb20k01.mp4?KID=unistore,video&Expires=1483344831&ssig=Nx9Qjl%2FJ2A&KID=unistore,video&Expires=1483344831&ssig=Nx9Qjl%2FJ2A";
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
