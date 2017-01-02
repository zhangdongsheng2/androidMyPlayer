package com.example.myplayer.util;

import android.view.Gravity;
import android.widget.Toast;

import com.example.myplayer.MyApplication;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ToastUtil {
    public static Toast mToast;

    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
