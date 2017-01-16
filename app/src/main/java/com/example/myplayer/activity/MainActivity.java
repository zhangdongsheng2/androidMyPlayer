package com.example.myplayer.activity;

import android.widget.Toast;

import com.example.myplayer.R;
import com.example.myplayer.drop.Cserver.FileUtils;
import com.example.myplayer.drop.Cserver.NativeRuntime;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.RxBus;
import com.example.myplayer.util.ToastUtil;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;


/**
 * Created by Administrator on 2016/3/23.
 * 主Activity
 */
public class MainActivity extends BaseActivity {

    //==================JNI开启服务===================
//    static {
//        System.loadLibrary("moduleName");
//    }

    private long exitTime = 0L;


    private void initService() {
        Toast.makeText(this, NativeRuntime.getInstance().stringFromJNI(), Toast.LENGTH_LONG).show();
        String executable = "libmoduleName.so";
        String aliasfile = "moduleName";
        String parafind = "/data/data/" + getPackageName() + "/" + aliasfile;
        String retx = "false";
        NativeRuntime.getInstance().RunExecutable(getPackageName(), executable, aliasfile, getPackageName() + "/com.example.myplayer.service.ForeverService");

        (new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    NativeRuntime.getInstance().startService(getPackageName() + "/com.example.myplayer.service.ForeverService", FileUtils.createRootPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })).start();
//        NativeRuntime.getInstance().stopService();
    }

    //-------------------------------------------------------------------------
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        CommonUtil.runOnThread(new Runnable() {
            @Override
            public void run() {
                File file = new File(MainActivity.this.getCacheDir().getParent() + "/databases/jiji.db");
                if (file.exists()) {
                    file.delete();
                }
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                BufferedSink buffer = null;
                try {
                    buffer = Okio.buffer(Okio.sink(file));
                    buffer.writeAll(Okio.source(getAssets().open("jiji.db")));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (buffer != null) {
                        try {
                            buffer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        doubleExit();
    }

    /**
     * 双击退出
     */
    private void doubleExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            RxBus.getInstance().send(new Object(), "finish");
            finish();
        }
    }


}
