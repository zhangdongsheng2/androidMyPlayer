package com.example.myplayer.util;

import com.example.myplayer.MyApplication;
import com.example.myplayer.db.DaoMaster;
import com.example.myplayer.db.DaoSession;

/**
 * Created by zhangdongsheng on 2016/12/31.
 */

public class APPUtil {
    private static DaoSession daoSession;

    //获取数据库DaoSession
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(MyApplication.getContext(), "dateJi.db").getWritableDatabase()).newSession();
        }
        return daoSession;
    }

}
