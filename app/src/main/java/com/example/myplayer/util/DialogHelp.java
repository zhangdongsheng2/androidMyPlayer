package com.example.myplayer.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.myplayer.R;

/**
 * 对话框辅助类
 * Created by  on 15/6/19.
 */
public class DialogHelp {


    /**
     * 调用函数
     * 没有title栏   提示
     *
     * @param message         提示文本内容
     * @param rightButtonText 右边的文案
     * @param leftButtonText  左边的文案
     * @param listener        回调监听
     */
    public static void showThree(Activity activity, String message, String rightButtonText, String leftButtonText, final RepickClickButtonListener listener) {
        show(activity, R.layout.dialog_cancel_confirm_three_btn, message, leftButtonText, rightButtonText, listener);
    }


    /**
     * 调用函数
     * 可自定义Dialog的样式
     *
     * @param layoutResid     dialog的布局
     * @param message         提示文本内容
     * @param leftButtonText  左边按钮文案
     * @param rightButtonText 右边的文案
     * @param listener        回调监听
     */
    private static void show(Activity activity, int layoutResid, String message, String leftButtonText, String rightButtonText, final RepickClickButtonListener listener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = ViewUtils.inflateView(layoutResid);
        dialog.setContentView(view);
        TextView textView = (TextView) view.findViewById(R.id.dialog_desc);
        textView.setText(message);
        TextView cancel = (TextView) view.findViewById(R.id.dialog_leftbutton);
        cancel.setText(leftButtonText);
        TextView confrim = (TextView) view.findViewById(R.id.dialog_rightbutton);
        confrim.setText(rightButtonText);
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (listener != null) {
                    listener.clickRightButton();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                listener.clickLeftButton();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    /**
     * 不修改上面这个RepickDialogListener是因为用到的地方比较多，改起来麻烦。
     * 新写一个接口
     */
    public interface RepickClickButtonListener {
        void clickLeftButton();

        void clickRightButton();
    }
}
