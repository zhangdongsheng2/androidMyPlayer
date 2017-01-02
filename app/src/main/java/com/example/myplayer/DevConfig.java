package com.example.myplayer;

import com.socks.library.KLog;

/**
 * Created by zhangdongsheng on 2017/1/1.
 */

public class DevConfig {
    public static boolean DEBUG;

    public static void init(String buildType) {
        if (buildType.equals("debug")) {
            DEBUG = true;
        } else if (buildType.equals("release")) {
            DEBUG = false;
        }
        initThirdParty();
    }

    /**
     * 初始化第三方库
     */
    private static void initThirdParty() {
        //KLog打印工具类
        KLog.init(DevConfig.DEBUG);
    }


}
