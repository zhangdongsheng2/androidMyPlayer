package com.example.myplayer.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/27.
 */
public class ViewUtils {
    /**
     * 获状态栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 用id加载一个布局
     *
     * @param id
     * @return
     */
    public static View inflateView(int id) {
        return View.inflate(BaseActivity.getActivity(), id, null);
    }

    /**
     * fragment跳转
     *
     * @param
     * @param clazz 要跳转到的fragment
     * @param stack 是否要加入到回退栈
     * @param save  是否要保存自己
     */
    public static void launch(boolean save, int id, Class clazz, boolean stack, Fragment thisFragment) {
        try {
            FragmentManager manager = BaseActivity.getActivity().getFragmentManager();
            FragmentTransaction beginTransaction = manager.beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.fragment_my_in,
                    R.anim.fragment_my_out, R.anim.fragment_my_in,
                    R.anim.fragment_my_out);

            Fragment fragment = null;
            fragment = (Fragment) clazz.newInstance();
            beginTransaction.add(id, fragment);
            if (null != thisFragment) {
                String tag = thisFragment.getClass().getName();
                if (save) {
                    beginTransaction.hide(thisFragment);
                } else {
                    beginTransaction.remove(thisFragment);
                }
                if (stack) {
                    beginTransaction.addToBackStack(tag);
                }
            }
            beginTransaction.commitAllowingStateLoss();

        } catch (Throwable e) {
            ExceptionUtil.printThrowable(e);
        }
    }
}
