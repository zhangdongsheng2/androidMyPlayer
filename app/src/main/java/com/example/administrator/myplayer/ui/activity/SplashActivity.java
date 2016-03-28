package com.example.administrator.myplayer.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.ui.fragment.SplashFragment;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2016/3/23.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        .setStatusBarTintEnabled(false);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 7000);
        launch(false, R.id.content2, SplashFragment.class, false);
    }

    /**
     * fragment跳转
     *
     * @param
     * @param clazz 要跳转到的fragment
     * @param stack 是否要加入到回退栈
     * @param save  是否要保存自己
     */
    public void launch(boolean save, int id, Class clazz, boolean stack) {
        try {
            FragmentManager manager = this.getFragmentManager();
            FragmentTransaction beginTransaction = manager.beginTransaction();
//            beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            beginTransaction.setCustomAnimations(R.anim.fragment_my_in,
                    R.anim.fragment_my_out, R.anim.fragment_my_in,
                    R.anim.fragment_my_out);

            Fragment fragment = null;
            fragment = (Fragment) clazz.newInstance();
            String tag = this.getClass().getName();
            beginTransaction.add(id, fragment);

            if (save) {
//                beginTransaction.hide(this);
            } else {
//                beginTransaction.remove(this);
            }
            if (stack) {
                beginTransaction.addToBackStack(tag);
            }
            beginTransaction.commitAllowingStateLoss();

        } catch (Throwable e) {

        }
    }

    /**
     * 退出Fragment回退栈
     */
    public void popStack() {
        popStack(null);
    }

    /**
     * 退出Fragment回退栈
     */
    public void popStack(String tag) {
        try {
            FragmentManager supportFragmentManager = this.getFragmentManager();
            Field mStateSaved = supportFragmentManager.getClass().getDeclaredField("mStateSaved");
            mStateSaved.setAccessible(true);
            mStateSaved.set(supportFragmentManager, Boolean.valueOf(false));
            if (TextUtils.isEmpty(tag)) {
                supportFragmentManager.popBackStackImmediate();
            } else {
                boolean popBackStackImmediate = supportFragmentManager.popBackStackImmediate(tag,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        } catch (Throwable e) {
            //异常了
        }
    }
}
