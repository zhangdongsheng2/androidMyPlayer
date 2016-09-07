package com.example.myplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.recyclerview.RecyclerViewCursorAdapter;

import java.util.ArrayList;

/*
         佛祖保佑       永无BUG
---------------------------------------------
          视频列表适配器
          别人做的支持Cursor的适配器
          @author ZDS
          create on 2016/4/2  18:32 */

public abstract class BasePlayAdapter<T extends RecyclerView.ViewHolder, H> extends RecyclerViewCursorAdapter<T> {
    protected BaseFragment mBaseFragment;

    /**
     * 构造函数重写 获取Fragment
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BasePlayAdapter(Context context, Cursor c, BaseFragment baseFragment) {
        super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);//最后一个参数表示是否注册监听数据库变化,变化了重新加载页面
        this.mBaseFragment = baseFragment;
    }

    @Override
    protected void onContentChanged() {

    }

    /**
     * 将cursor中的数据取出来放入集合当中
     *
     * @param cursor
     * @return
     */
    protected abstract ArrayList<H> cursorToList(Cursor cursor);

}
