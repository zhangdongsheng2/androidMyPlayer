package com.example.administrator.myplayer.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.base.BaseFragment;
import com.example.administrator.myplayer.util.ViewUtils;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MainFragment extends BaseFragment {

    /**
     * fragment对象被创建后附加在activity上
     */
    @Override
    public void onAttach(Activity activity) {
        System.out.println("======01======onAttach========================");
        super.onAttach(activity);
    }

    /**
     * 初始化fragment对象(不包含加载界面)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("====01====onCreate====================");
        super.onCreate(savedInstanceState);
    }
    /**
     * 用来加载fragment的布局文件
     * inflater 布局填充器,可以把 布局文件加载到界面上
     * container 父级容器,表示fragment填充在哪个父级控件上
     * savedInstanceState fragment状态的集合,界面可见,界面获得焦点,界面失去焦点,界面不可见,实例对象被销毁
     */

    /**
     * fragment界面可见后调用这个方法
     */
    @Override
    public void onStart() {
        System.out.println("======01======onStart=====================");
        super.onStart();
    }

    /**
     * fragment界面获得焦点后调用这个方法
     */
    @Override
    public void onResume() {
        System.out.println("=======01=====onResume=====================");
        super.onResume();
    }

    /**
     * fragment界面失去焦点后调用这个方法
     */
    @Override
    public void onPause() {
        System.out.println("========01====onPause=====================");
        super.onPause();
    }

    /**
     * fragment界面不可见后调用这个方法
     */
    @Override
    public void onStop() {
        System.out.println("=====01=======onStop=====================");
        super.onStop();
    }

    /**
     * fragment的view被销毁之前调用这个方法
     */
    @Override
    public void onDestroyView() {
        System.out.println("========01====onDestroyView=====================");
        super.onDestroyView();
    }

    /**
     * fragment不再使用时调用这个方法
     */
    @Override
    public void onDestroy() {
        System.out.println("====01========onDestroy=====================");
        super.onDestroy();
    }

    /**
     * 把fragment从activity中去除调用这个方法
     */
    @Override
    public void onDetach() {
        System.out.println("========01====onDetach=====================");
        super.onDetach();
    }


    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, null);
        System.out.println("========01=====onCreateView=======================");
//        initLogoAnim();
        view.setPadding(0, ViewUtils.getStatusBarHeight(getActivity()), 0, 0);
        return view;
    }

    private void initLogoAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        // ObjectAnimator animator = ObjectAnimator.ofFloat(iv_logo,
        // "rotationY", 0,90,270,360);
        // 设置动画时间

        animator.setDuration(1000);
        // 设置无限旋转
//        animator.setRepeatCount(1);
        LinearInterpolator value = new LinearInterpolator();
        animator.setInterpolator(value);
        animator.start();
    }
}
