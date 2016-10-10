package com.example.myplayer.activity.leonids;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myplayer.R;
import com.plattysoft.leonids.ParticleSystem;

/**
 * Created by zhangdongsheng on 2016/10/10.
 */

public class HeartActivity extends Activity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        WindowManager.LayoutParams layoutParams1 = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams1);

        button = new Button(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 300;
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        button.setText("点击");
        button.setLayoutParams(layoutParams);
        relativeLayout.addView(button);

        setContentView(relativeLayout);

        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        praticleEffect1();
        praticleEffect2();
    }

    private void praticleEffect1() {
        new ParticleSystem(this, 80, R.drawable.heart, 5000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 225, 315)
                .setRotationSpeed(180)
                .setAcceleration(0.00005f, 270)
                .setScaleRange(0.1f, 0.5f)
                .setFadeOut(2000)
                .emit(button, 3);
    }

    private void praticleEffect2() {
        new ParticleSystem(this, 80, R.drawable.heart1, 5000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 225, 315)
                .setRotationSpeed(180)
                .setAcceleration(0.00005f, 270)
                .setScaleRange(0.1f, 0.5f)
                .setFadeOut(2000)
                .emit(button, 3);
    }

    private void praticleEffect0() {
        new ParticleSystem(this, 100, R.drawable.heart1, 1000)
                .setSpeedModuleAndAngleRange(0.1f, 0.2f, 225, 315)
                .setScaleRange(0, 0.5f)
                .setFadeOut(500)
                .oneShot(button, 100);
    }

}
