package com.example.myplayer.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.myplayer.util.ToastUtil;

/**
 * Activity基类 2016/3/23.
 */
public abstract class BaseActivity extends FragmentActivity {
    private static BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;

    }
    public static BaseActivity getActivity() {
        return mActivity;
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
