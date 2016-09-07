package com.example.myplayer.db;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import com.example.myplayer.recyclerview.RecyclerViewCursorAdapter;


/**
 * 作者：Administrator on 2016/4/2 19:17
 * <p/>  异步操作内容提供者 Android 自带
 * 邮箱：zhangdongsheng2@sina.com
 */
public class SimpleQueryHandler extends AsyncQueryHandler {
    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (cookie != null && cookie instanceof RecyclerViewCursorAdapter) {
            RecyclerViewCursorAdapter adapter = (RecyclerViewCursorAdapter) cookie;
            adapter.changeCursor(cursor);
        }
    }
}
