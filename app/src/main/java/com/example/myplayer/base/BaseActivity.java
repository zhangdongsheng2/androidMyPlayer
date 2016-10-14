package com.example.myplayer.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.myplayer.app.AppManager;
import com.example.myplayer.util.DialogHelp;

import butterknife.ButterKnife;

/**
 * Activity基类 2016/3/23.
 */
public abstract class BaseActivity extends FragmentActivity {

    private boolean _isVisible;
    private ProgressDialog _waitDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        AppManager.getAppManager().addActivity(this);
        // 通过注解绑定控件
        ButterKnife.bind(this);
        initView();
        initData();
        _isVisible = true;
    }

    protected View initView() {
        return null;
    }

    protected void initData() {
    }

    protected int getLayoutId() {
        return 0;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public ProgressDialog showWaitDialog() {
        return showWaitDialog("加载中…");
    }


    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }


    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }


    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
