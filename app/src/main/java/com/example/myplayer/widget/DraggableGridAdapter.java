package com.example.myplayer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.activity.leonids.HeartActivity;
import com.example.myplayer.util.SPHelper;
import com.example.myplayer.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DraggableGridAdapter extends BaseAdapter {
    private final String TAG = "DraggableGridAdapter";

    private Context mContext;
    private DraggableGridViewPager myGridview;
    private List<DraggableGridModule> fmvList;
    private Map<String, DraggableGridModule> map = new HashMap<String, DraggableGridModule>();
    private boolean isRemove = false;

    public DraggableGridAdapter(Context mContext, String content,
                                DraggableGridViewPager myGridview) {
        this.mContext = mContext;
        this.myGridview = myGridview;

        initData();

        fmvList = new ArrayList<DraggableGridModule>();
        fmvList = initList(content);

    }

    private void initData() {
        map.put("1", new DraggableGridModule(1, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("2", new DraggableGridModule(2, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("3", new DraggableGridModule(3, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("4", new DraggableGridModule(4, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("5", new DraggableGridModule(5, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("6", new DraggableGridModule(6, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("7", new DraggableGridModule(7, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("8", new DraggableGridModule(8, "模块编号", R.drawable.ic_nav_tweet_actived));
        map.put("10", new DraggableGridModule(10, "粒子特效", R.drawable.ic_nav_tweet_actived));

        map.put("9", new DraggableGridModule(9, "更多...", R.drawable.ic_nav_tweet_actived));
    }

    private List<DraggableGridModule> initList(String content) {
        List<DraggableGridModule> lists = new ArrayList<DraggableGridModule>();
        // 将布局记录解析出来转为集合
        String[] str = content.split(":");
        if (str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                if (map.get(str[i]) != null) {
                    lists.add(map.get(str[i]));
                }
            }
        }
        Log.d(TAG, "lists.size=" + lists.size());
        return lists;
    }

    @Override
    public int getCount() {
        if (fmvList != null) {
            if (fmvList.size() < 9) { // 规定本地文件只保留9个模块记录（包括更多模块）。
                for (int t = 0; t < 9 - fmvList.size(); t++) {
                    fmvList.add(new DraggableGridModule(0, "", 1));
                }
            }
            return 9;
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return fmvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = null;
        if (convertView == null) {
            item = new Item();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_homepage_gridview, null);
            item.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_item_homepage_title);
            item.iv_add_del = (ImageView) convertView
                    .findViewById(R.id.iv_item_homepage);
            convertView.setTag(item);
        } else {
            item = (Item) convertView.getTag();
        }
        final DraggableGridModule VO = fmvList.get(position);

        if (VO.getId() != 0) {// lists为0默认空白
            // 初始化数据
            item.tv_title.setText(VO.getName());
            item.tv_title.setCompoundDrawablesWithIntrinsicBounds(0,
                    VO.getPicture(), 0, 0);
            item.iv_add_del.setVisibility(View.GONE);
            if ("1".equals(VO.getState())) {
                item.iv_add_del.setVisibility(View.VISIBLE);
            } else {
                item.iv_add_del.setVisibility(View.GONE);
            }

        } else {
            item.tv_title.setText("");
            item.tv_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            item.iv_add_del.setVisibility(View.GONE);
        }

        item.iv_add_del.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DraggableGridModule rv = null;
                for (DraggableGridModule vo : fmvList) {
                    if ("1".equals(vo.getState())) {
                        rv = vo;
                        break;
                    }
                }
                if (rv != null) {
                    ToastUtil.showToast("已经删除:" + rv.getName());
                    return;
//                    fmvList.remove(rv);
//                    isRemove = false;
//                    refresh();
                }
            }
        });

        myGridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(TAG, "onItemClick");
                int i = -1;
                if (isRemove) {
                    isRemove = false;
                    for (DraggableGridModule vo : fmvList) {
                        vo.setState("0");
                    }
                    notifyDataSetChanged();
                } else {
                    i = fmvList.get(position).getId();
                }
                // 点击跳转
                switch (i) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:
                        break;
                    case 10:
                        mContext.startActivity(new Intent(mContext, HeartActivity.class));
                        break;
                    default:
                        break;
                }

            }

        });
        myGridview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Log.d(TAG, "onItemLongClick");
                for (DraggableGridModule vo : fmvList) {
                    vo.setState("0");
                }
                DraggableGridModule vo = fmvList.get(position);
                if (vo.getId() != 0 && vo.getId() != 9) {
                    fmvList.get(position).setState("1");
                    isRemove = true;// 存在删除操作
                    notifyDataSetChanged();// 长按模块 吃掉点击事件 显示删除按钮
                    return true;
                } else {
                    return false;// 不可拖动
                }
            }
        });

        myGridview.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {

            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Log.d(TAG, "onRearrange:" + oldIndex + ":" + newIndex);
                for (DraggableGridModule vo : fmvList) {
                    vo.setState("0");
                }
                isRemove = false;
                int newI = fmvList.get(newIndex).getId();
                int oldI = fmvList.get(oldIndex).getId();
                if (newI == 9 || oldI == 9 || newI == 0 || oldI == 0) {
                    // 此处刷新
                    notifyDataSetChanged();
                } else {
                    DraggableGridModule vo = fmvList.get(oldIndex);
                    fmvList.remove(vo);
                    fmvList.add(newIndex, vo);
                    refresh();
                }

            }
        });

        return convertView;

    }

    public void refresh() {
        StringBuilder sb = new StringBuilder();
        // 将布局结果保存
        boolean isNine = false;
        for (DraggableGridModule vo : fmvList) {
            int id = vo.getId();// 获取模块对应的编号 编号为1开始
            if (id != 0) {
                sb.append(id + ":");
            }
            if (id == 9) {
                isNine = true;//存在更多模块
            }
        }
        if (fmvList.size() < 9 && isNine == false) {//显示少于9个模块且缺少更多模块 则添加
            sb.append(9 + ":");
            fmvList.add(map.get("9"));
        }

        String order = sb.substring(0, sb.length() - 1).toString();

        SPHelper.getInstance().putString("homepage_layout_",
                order);
        // 默认显示九个格子
        notifyDataSetChanged();
    }

    class Item {
        TextView tv_title;
        ImageView iv_add_del;
    }

}
