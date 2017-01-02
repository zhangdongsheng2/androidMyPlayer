package com.example.myplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myplayer.R;
import com.example.myplayer.util.ViewUtils;

import java.lang.reflect.Field;

/**
 * Fragment 的基类 2016/3/29.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        initListener();
        initData();
        return mRootView;
    }

    protected abstract View initView();

    protected void initListener() {
    }

    protected void initData() {
    }


//========================跳转相关========================================

    /**
     * fragment跳转
     * <p/>
     * 不保存自己 加入到回退栈 容器id固定为R.id.container
     *
     * @param clazz 要跳转到的fragment
     */
    public void launch(Class clazz, Bundle bundle) {
        launch(false, R.id.content, clazz, true, bundle);
    }

    /**
     * fragment跳转 容器id固定为R.id.container 并加入到回退栈
     *
     * @param clazz 要跳转到的fragment
     * @param save  是否要保存自己
     */
    public void launch(boolean save, Class clazz, Bundle bundle) {
        launch(save, R.id.content, clazz, true, bundle);
    }

    /**
     * fragment跳转 不保存自己。 容器id固定为R.id.container
     *
     * @param clazz 要跳转到的fragment
     * @param stack 是否加入到回退栈
     */
    public void launch(Class clazz, boolean stack, Bundle bundle) {
        launch(false, R.id.content, clazz, stack, bundle);
    }

    /**
     * fragment跳转
     *
     * @param
     * @param clazz 要跳转到的fragment
     * @param stack 是否要加入到回退栈
     * @param save  是否要保存自己
     */
    public void launch(boolean save, int id, Class clazz, boolean stack, Bundle bundle) {
        ViewUtils.launch(getActivity(), save, id, clazz, stack, this, bundle);
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
        FragmentManager manager = getActivity().getSupportFragmentManager();

        try {
            Field mStateSaved = manager.getClass().getDeclaredField("mStateSaved");
            mStateSaved.setAccessible(true);
            mStateSaved.set(manager, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(tag)) {
            try {
                manager.popBackStackImmediate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Activity 跳转
     *
     * @param bundle
     * @param targetActivity
     */
    protected void enterActivity(Bundle bundle, Class<?> targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }
}
