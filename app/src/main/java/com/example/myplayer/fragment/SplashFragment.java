package com.example.myplayer.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.ExceptionUtil;
import com.example.myplayer.util.ViewUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 启动页动画 2016/3/24.
 */
public class SplashFragment extends BaseFragment {

    private android.widget.ImageView ivsplashbg;
    private android.widget.ImageView ivsplashlogo;
    private android.widget.ImageView ivsplashleft;
    private android.widget.ImageView ivsplashright;
    private android.widget.LinearLayout lvanimContainer;
    private RelativeLayout rlsplash;

    public SplashFragment() {

    }

    @Override
    protected View initView() {
        View inflate = ViewUtils.inflateView(R.layout.fragment_splash);
        this.rlsplash = (RelativeLayout) inflate.findViewById(R.id.rl_splash);
        this.lvanimContainer = (LinearLayout) inflate.findViewById(R.id.lv_animContainer);
        this.ivsplashright = (ImageView) inflate.findViewById(R.id.iv_splash_right);
        this.ivsplashleft = (ImageView) inflate.findViewById(R.id.iv_splash_left);
        this.ivsplashlogo = (ImageView) inflate.findViewById(R.id.iv_splash_logo);
        this.ivsplashbg = (ImageView) inflate.findViewById(R.id.iv_splash_bg);
        return inflate;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivsplashlogo, "rotation", 0, 360);
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

    @Override
    protected void processClick(View view) {

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
                try {
                    FragmentManager manager = BaseActivity.getActivity().getSupportFragmentManager();
                    final FragmentTransaction beginTransaction = manager.beginTransaction();
                    beginTransaction.setCustomAnimations(R.anim.my_scale_action,
                            R.anim.my_alpha_action, R.anim.my_scale_action,
                            R.anim.my_translation_out);
                    Fragment fragment = MainTabHostFragment.class.newInstance();
                    beginTransaction.add(R.id.content, fragment);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            beginTransaction.remove(SplashFragment.this);
                        }
                    }, 1000);
                    beginTransaction.commitAllowingStateLoss();
                } catch (Throwable e) {
                    e.printStackTrace();
                    ExceptionUtil.printThrowable(e);
                }

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
