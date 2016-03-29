package com.example.administrator.myplayer.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.base.BaseFragment;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Administrator on 2016/3/24.
 */
public class SplashFragment extends BaseFragment {
    private android.widget.ImageView ivsplashbg;
    private android.widget.ImageView ivsplashlogo;
    private android.widget.ImageView ivsplashleft;
    private android.widget.ImageView ivsplashright;
    private android.widget.LinearLayout lvanimContainer;
    private RelativeLayout rlsplash;
    private View inflate;















        /**
         * fragment对象被创建后附加在activity上
         */
        @Override
        public void onAttach(Activity activity) {
            System.out.println("======02======onAttach========================");
            super.onAttach(activity);
        }
        /**
         * 初始化fragment对象(不包含加载界面)
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            System.out.println("====02====onCreate====================");
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
            System.out.println("======02======onStart=====================");
            super.onStart();
        }

        /**
         * fragment界面获得焦点后调用这个方法
         */
        @Override
        public void onResume() {
            System.out.println("=======02=====onResume=====================");
            super.onResume();
        }

        /**
         * fragment界面失去焦点后调用这个方法
         */
        @Override
        public void onPause() {
            System.out.println("========02====onPause=====================");
            super.onPause();
        }

        /**
         * fragment界面不可见后调用这个方法
         */
        @Override
        public void onStop() {
            System.out.println("=====02=======onStop=====================");
            super.onStop();
        }

        /**
         * fragment的view被销毁之前调用这个方法
         */
        @Override
        public void onDestroyView() {
            System.out.println("========02====onDestroyView=====================");
            super.onDestroyView();
        }

        /**
         * fragment不再使用时调用这个方法
         */
        @Override
        public void onDestroy() {
            System.out.println("====02========onDestroy=====================");
            super.onDestroy();
        }

        /**
         * 把fragment从activity中去除调用这个方法
         */
        @Override
        public void onDetach() {
            System.out.println("========02====onDetach=====================");
            super.onDetach();
        }

















    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_splash, null);
        System.out.println("========02=====onCreateView=======================");
        initView();
        return inflate;
    }


    protected void initView() {
        this.rlsplash = (RelativeLayout) inflate.findViewById(R.id.rl_splash);
        this.lvanimContainer = (LinearLayout) inflate.findViewById(R.id.lv_animContainer);
        this.ivsplashright = (ImageView) inflate.findViewById(R.id.iv_splash_right);
        this.ivsplashleft = (ImageView) inflate.findViewById(R.id.iv_splash_left);
        this.ivsplashlogo = (ImageView) inflate.findViewById(R.id.iv_splash_logo);
        this.ivsplashbg = (ImageView) inflate.findViewById(R.id.iv_splash_bg);
        initLogoAnim();
    }

    private void initLogoAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivsplashlogo, "rotation", 0, 360);
        // ObjectAnimator animator = ObjectAnimator.ofFloat(iv_logo,
        // "rotationY", 0,90,270,360);
        // 设置动画时间
        animator.setDuration(1000);
        // 设置无限旋转
        animator.setRepeatCount(1);
        LinearInterpolator value = new LinearInterpolator();
        animator.setInterpolator(value);
        // 开始动画
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // 结束时，做打开的动画
                // 1.获得 进度容器的图片
                rlsplash.setDrawingCacheEnabled(true);
                rlsplash
                        .setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = rlsplash.getDrawingCache();// null

                // 给动画imageView设置图片,做动画
                ivsplashleft.setImageBitmap(getLeftBitmap(bitmap));
                ivsplashright.setImageBitmap(getRightBitmap(bitmap));

                // 显示结果，隐藏进度
                ivsplashlogo.setVisibility(View.GONE);
                ivsplashbg.setVisibility(View.GONE);
                lvanimContainer.setVisibility(View.VISIBLE);
                lvanimContainer.bringToFront();

                // 显示动画
                showOpenAnimtor();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.start();
    }

    private Bitmap getLeftBitmap(Bitmap srcBitmap) {
        // 1. 准备画纸
        int width = (int) (srcBitmap.getWidth() / 2f + 0.5f);
        int height = srcBitmap.getHeight();
        Bitmap destBitmap = Bitmap.createBitmap(width, height,
                srcBitmap.getConfig());
        // 2. 准备画板，把画纸放上去
        Canvas canvas = new Canvas(destBitmap);
        // 3. 准备笔
        Paint paint = new Paint();
        // 4. 准备规则
        Matrix matrix = new Matrix();

        // 5. 绘制
        canvas.drawBitmap(srcBitmap, matrix, paint);

        return destBitmap;
    }

    private Bitmap getRightBitmap(Bitmap srcBitmap) {
        // 1. 准备画纸
        int width = (int) (srcBitmap.getWidth() / 2f + 0.5f);
        int height = srcBitmap.getHeight();
        Bitmap destBitmap = Bitmap.createBitmap(width, height,
                srcBitmap.getConfig());
        // 2. 准备画板，把画纸放上去
        Canvas canvas = new Canvas(destBitmap);
        // 3. 准备笔
        Paint paint = new Paint();
        // 4. 准备规则
        Matrix matrix = new Matrix();
        matrix.setTranslate(-width, 0);

        // 5. 绘制
        canvas.drawBitmap(srcBitmap, matrix, paint);

        return destBitmap;
    }

    private void showOpenAnimtor() {
        AnimatorSet set = new AnimatorSet();

        lvanimContainer.measure(0, 0);
        set.playTogether(
                ObjectAnimator.ofFloat(ivsplashleft, "translationX", 0,
                        -ivsplashleft.getMeasuredWidth()),
                ObjectAnimator.ofFloat(ivsplashright, "translationX", 0,
                        ivsplashright.getMeasuredWidth()),
                ObjectAnimator.ofFloat(ivsplashleft, "alpha", 1, 0),
                ObjectAnimator.ofFloat(ivsplashright, "alpha", 1, 0));
        set.setDuration(1000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
               SplashFragment.this.launch(false, MainFragment.class, false);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }
}
