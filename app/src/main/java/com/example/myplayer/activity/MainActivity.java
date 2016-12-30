package com.example.myplayer.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.drop.Cserver.FileUtils;
import com.example.myplayer.drop.Cserver.NativeRuntime;
import com.example.myplayer.util.ToastUtil;


/**
 * Created by Administrator on 2016/3/23.D:\Studio\sdk\platforms\android-22\android.jar
 */
public class MainActivity extends BaseActivity {

    static {
        System.loadLibrary("moduleName");
    }

    private long exitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏  透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        initService();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

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

//                    NativeRuntime.getInstance().stopService();
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
            finish();
            System.exit(0);
        }
    }

}
