package com.example.myplayer.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myplayer.R;
import com.example.myplayer.util.ExceptionUtil;
import com.example.myplayer.util.ViewUtils;

import java.lang.reflect.Field;

/**
 * Fragment 的基类 2016/3/29.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

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

    protected abstract void initListener();

    protected abstract void initData();

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
        ViewUtils.launch(save, id, clazz, stack, this, bundle);
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


    /**
     * 处理共同按钮点击事件
     *
     * @param view
     */
    protected abstract void processClick(View view);

    @Override
    public void onClick(View v) {
        processClick(v);
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
