package com.example.administrator.myplayer.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.administrator.myplayer.util.ToastUtil;

/**
 * Created by Administrator on 2016/3/23.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private static BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        initView();
        initListener();
        initData();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    public static BaseActivity getActivity() {
        return mActivity;
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

    @Override
    public void onBackPressed() {
        if (isLastFragment()) {
            doubleExit();
        } else {
            super.onBackPressed();
        }
    }

    private long exitTime = 0L;

    /**
     * 判断栈中只有最后一个Fragment
     *
     * @return
     */
    private boolean isLastFragment() {
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
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
