package com.example.myplayer.recyclerview;

/**
 * 作者：Administrator on 2016/4/2 18:52
 * <p/>
 * 邮箱：zhangdongsheng2@sina.com
 */

import android.database.Cursor;
import android.widget.Filter;

/**
 * The CursorFilter delegates most of the work to the
 * {@link android.widget.CursorAdapter}. Subclasses should override these
 * delegate methods to run the queries and convert the results into String
 * that can be used by auto-completion widgets.
 */
class CursorFilter extends Filter {

    CursorFilterClient mClient;

    interface CursorFilterClient {
        CharSequence convertToString(Cursor cursor);
        Cursor runQueryOnBackgroundThread(CharSequence constraint);
        Cursor getCursor();
        void changeCursor(Cursor cursor);
    }

    CursorFilter(CursorFilterClient client) {
        mClient = client;
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return mClient.convertToString((Cursor) resultValue);
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Cursor cursor = mClient.runQueryOnBackgroundThread(constraint);

        FilterResults results = new FilterResults();
        if (cursor != null) {
            results.count = cursor.getCount();
            results.values = cursor;
        } else {
            results.count = 0;
            results.values = null;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        Cursor oldCursor = mClient.getCursor();

        if (results.values != null && results.values != oldCursor) {
            mClient.changeCursor((Cursor) results.values);
        }
    }
}


