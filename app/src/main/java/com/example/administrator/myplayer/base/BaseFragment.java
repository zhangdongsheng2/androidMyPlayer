package com.example.administrator.myplayer.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.text.TextUtils;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.util.ExceptionUtil;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/29.
 */
public class BaseFragment extends Fragment {

    public void launch(boolean save, Class clazz, boolean stack) {
        launch(save, R.id.content,clazz,stack);
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
            FragmentManager manager = BaseActivity.getActivity().getFragmentManager();
            FragmentTransaction beginTransaction = manager.beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.fragment_my_in,
                    R.anim.fragment_my_out, R.anim.fragment_my_in,
                    R.anim.fragment_my_out);

            Fragment fragment = null;
            fragment = (Fragment) clazz.newInstance();
            String tag = this.getClass().getName();
            beginTransaction.add(id, fragment);

            if (save) {
                beginTransaction.hide(this);
            } else {
                beginTransaction.remove(this);
            }
            if (stack) {
                beginTransaction.addToBackStack(tag);
            }
            beginTransaction.commitAllowingStateLoss();

        } catch (Throwable e) {
            ExceptionUtil.printThrowable(e);
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
            ExceptionUtil.printThrowable(e);
        }
    }



}
