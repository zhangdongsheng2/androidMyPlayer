package com.example.myplayer.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.util.StringUtils;
import com.example.myplayer.util.ToastUtil;
import com.example.myplayer.widget.VideoView;

import java.util.ArrayList;

/*
           视频播放页面
           @author ZDS
           create on 2016/4/3 19:18 */
public class VideoPlayerActivity extends Activity implements View.OnClickListener {
    public static final String POSITION = "position";
    public static final String VIDEOLIST = "videolist";
    private final int MSG_UPDATE_SYSTEM_TIME = 0;//更新系统时间
    private final int MSG_UPDATE_PLAY_PROGRESS = 1;//更新播放进度
    private final int MSG_HIDE_CONTROL = 2;//延时隐藏控制面板
    private VideoView video_view;
    //top control
    private TextView tv_name, tv_system_time;
    private ImageView iv_battery;
    private ImageView iv_voice;
    private SeekBar voice_seekbar;
    //bottom control
    private ImageView btn_play, btn_exit, btn_pre, btn_next, btn_screen;
    private TextView tv_current_position, tv_duration;
    private SeekBar video_seekbar;
    private LinearLayout ll_top_control, ll_bottom_control;
    private LinearLayout ll_loading, ll_buffer;
    private int currentPosition;//当前播放视频的位置
    private ArrayList<VideoItem> videoList;//当前的视频列表
    private BatteryChangeReceiver batteryChangeReceiver;
    private AudioManager audioManager;
    private int touchSlop;
    private GestureDetector gestureDetector;
    private int maxVolume;//系统中音乐和视频类型最大音量
    private int currentVolume;//系统音乐和视频类型当前的音量
    private boolean isMute = false;//是否是静音模式
    private int screenWidth, screenHeight;
    private boolean isShowControlLayout = false;//是否是显示控制面板
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SYSTEM_TIME:
                    updateSystemTime();
                    break;
                case MSG_UPDATE_PLAY_PROGRESS:
                    updatePlayProgress();
                    break;
                case MSG_HIDE_CONTROL:
                    hideControlLayout();
                    break;
            }
        }

        ;
    };
    private float downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    @Override
    public void onClick(View v) {
        {
            switch (v.getId()) {
                case R.id.btn_exit:
                    finish();
                    break;
                case R.id.iv_voice:
                    isMute = !isMute;
                    updateSystemVolume();
                    break;
                case R.id.btn_play:
                    if (video_view.isPlaying()) {
                        video_view.pause();
                    } else {
                        video_view.start();
                    }
                    updateBtnPlayBg();
                    break;
                case R.id.btn_pre:
                    if (currentPosition > 0) {
                        currentPosition--;
                        playVideo();
                    }
                    break;
                case R.id.btn_next:
                    if (currentPosition < (videoList.size() - 1)) {
                        currentPosition++;
                        playVideo();
                    }
                    break;
                case R.id.btn_screen:
                    video_view.switchScreen();
                    updateScreenBtnBg();
                    break;
            }
        }
    }

    /**
     * 更新系统时间
     */
    private void updateSystemTime() {
        tv_system_time.setText(StringUtils.formatSystemTime());
        handler.sendEmptyMessageDelayed(MSG_UPDATE_SYSTEM_TIME, 1000);
    }

    /**
     * 更新播放进度
     */
    private void updatePlayProgress() {
        tv_current_position.setText(StringUtils.formatVideoDuration(video_view.getCurrentPosition()));
        video_seekbar.setProgress(video_view.getCurrentPosition());
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PLAY_PROGRESS, 500);
    }

    protected void initView() {
        setContentView(R.layout.fragment_video_play);
        video_view = (VideoView) findViewById(R.id.video_view);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_system_time = (TextView) findViewById(R.id.tv_system_time);
        iv_battery = (ImageView) findViewById(R.id.iv_battery);
        iv_voice = (ImageView) findViewById(R.id.iv_voice);
        voice_seekbar = (SeekBar) findViewById(R.id.voice_seekbar);

        btn_play = (ImageView) findViewById(R.id.btn_play);
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_pre = (ImageView) findViewById(R.id.btn_pre);
        btn_next = (ImageView) findViewById(R.id.btn_next);
        btn_screen = (ImageView) findViewById(R.id.btn_screen);
        tv_current_position = (TextView) findViewById(R.id.tv_current_position);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        video_seekbar = (SeekBar) findViewById(R.id.video_seekbar);
        ll_top_control = (LinearLayout) findViewById(R.id.ll_top_control);
        ll_bottom_control = (LinearLayout) findViewById(R.id.ll_bottom_control);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        ll_buffer = (LinearLayout) findViewById(R.id.ll_buffer);
    }

    protected void initListener() {
        ll_top_control.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_top_control.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ll_top_control.setTranslationY(-ll_top_control.getHeight());
            }
        });
        ll_bottom_control.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_bottom_control.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ll_bottom_control.setTranslationY(ll_bottom_control.getHeight());
            }
        });

        iv_voice.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_screen.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        voice_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL, 5000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(MSG_HIDE_CONTROL);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {//表示是用户手动拖动
                    isMute = false;
                    currentVolume = progress;
                    updateSystemVolume();
                }
            }
        });
        video_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL, 5000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(MSG_HIDE_CONTROL);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    video_view.seekTo(progress);
                    tv_current_position.setText(StringUtils.formatVideoDuration(progress));
                }
            }
        });
        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btn_play.setImageResource(R.drawable.selector_btn_play);
            }
        });
        video_view.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                //percent:0-100
                long bufferProgress = (long) ((video_view.getDuration() / 100f) * percent);
                video_seekbar.setSecondaryProgress((int) bufferProgress);
            }
        });
        video_view.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//					Toast.makeText(VideoPlayerActivity.this, "开始卡顿", 0).show();
                        ll_buffer.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        ll_buffer.setVisibility(View.GONE);
//					Toast.makeText(VideoPlayerActivity.this, "卡顿结束。。。。。。。。", 0).show();
                        break;
                }
                return true;
            }
        });
        video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        //未知格式，视频文件错误
                        ToastUtil.showToast("视频格式不支持");
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 更新系统音量
     */
    private void updateSystemVolume() {
        if (isMute) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            voice_seekbar.setProgress(0);
        } else {
            voice_seekbar.setProgress(currentVolume);
            //第三个参数如果是1，会显示音量改变的浮动面板
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        }
    }

    protected void initData() {
        gestureDetector = new GestureDetector(this, (GestureDetector.OnGestureListener) new MyOnGestureListner());
        touchSlop = ViewConfiguration.getTouchSlop();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        updateSystemTime();
        registerBatteryReceiver();
        initVolume();

        Uri videoUri = getIntent().getData();
        if (videoUri != null) {
            //从文件发起的请求
            video_view.setVideoURI(videoUri);
            btn_pre.setEnabled(false);
            btn_next.setEnabled(false);
            tv_name.setText(videoUri.getPath());
        } else {
            //正常从视频列表中进入的
            currentPosition = getIntent().getExtras().getInt(POSITION);
            videoList = (ArrayList<VideoItem>) getIntent().getExtras().getSerializable(VIDEOLIST);

            playVideo();
        }


        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setFillAfter(true);
                ll_loading.setAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ll_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                alphaAnimation.start();

                video_view.start();

                updatePlayProgress();
                video_seekbar.setMax(video_view.getDuration());
                tv_current_position.setText("00:00");
                tv_duration.setText(StringUtils.formatVideoDuration(video_view.getDuration()));

                btn_play.setImageResource(R.drawable.selector_btn_pause);
            }
        });

//		video_view.setMediaController(new MediaController(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                handler.removeMessages(MSG_HIDE_CONTROL);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float moveDistance = moveY - downY;

                //对滑动距离进行一个值的限制
                if (Math.abs(moveDistance) < touchSlop) break;

                isMute = false;

                float totalDistance = Math.min(screenHeight, screenWidth);
                float movePercent = Math.abs(moveDistance) / totalDistance;
                int moveVolume = (int) (movePercent * maxVolume);//这个值一定是0

                if (moveDistance > 0) {
                    //减小音量
                    currentVolume -= 1;
                } else {
                    //增大音量
                    currentVolume += 1;
                }
                updateSystemVolume();

                downY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL, 5000);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化系统音量
     */
    private void initVolume() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //maxVolume:0-15
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        voice_seekbar.setMax(maxVolume);
        voice_seekbar.setProgress(currentVolume);
    }

    /**
     * 注册系统电量变化的广播接受者
     */
    private void registerBatteryReceiver() {
        batteryChangeReceiver = new BatteryChangeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryChangeReceiver, filter);

    }

    /**
     * 播放当前位置的视频
     */
    private void playVideo() {
        btn_pre.setEnabled(currentPosition != 0);
        btn_next.setEnabled(currentPosition != (videoList.size() - 1));

        VideoItem videoItem = videoList.get(currentPosition);
        tv_name.setText(videoItem.getTitle());
        video_view.setVideoURI(Uri.parse(videoItem.getPath()));
    }

    /**
     * 根据是否是全屏设置屏幕按钮的背景图片
     */
    private void updateScreenBtnBg() {
        btn_screen.setImageResource(video_view.isFullScreen() ?
                R.drawable.selector_btn_defaultscreen : R.drawable.selector_btn_fullscreen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(batteryChangeReceiver);
    }

    private void showControlLayout() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ll_top_control, "translationY", -ll_top_control.getHeight(), 0);
        animator.setDuration(500);
        animator.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ll_bottom_control, "translationY", ll_bottom_control.getHeight(), 0);
        animator2.setDuration(500);
        animator2.start();

        isShowControlLayout = true;
        handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL, 5000);
    }

    private void hideControlLayout() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ll_top_control, "translationY", 0, -ll_top_control.getHeight());
        animator.setDuration(500);
        animator.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(ll_bottom_control, "translationY", 0, ll_bottom_control.getHeight());
        animator2.setDuration(500);
        animator2.start();
        isShowControlLayout = false;
    }

    private void updateBatteryBg(int level) {
        if (level == 0) {
            iv_battery.setImageResource(R.drawable.ic_battery_0);
        } else if (level > 0 && level <= 10) {
            iv_battery.setImageResource(R.drawable.ic_battery_1);
        } else if (level > 10 && level <= 20) {
            iv_battery.setImageResource(R.drawable.ic_battery_2);
        } else if (level > 20 && level <= 50) {
            iv_battery.setImageResource(R.drawable.ic_battery_4);
        } else if (level > 50 && level <= 80) {
            iv_battery.setImageResource(R.drawable.ic_battery_5);
        } else {
            iv_battery.setImageResource(R.drawable.ic_battery_6);
        }
    }

    /**
     * 根据是否正在播放更改播放按钮的背景图片
     */
    private void updateBtnPlayBg() {
        btn_play.setImageResource(video_view.isPlaying() ? R.drawable.selector_btn_pause : R.drawable.selector_btn_play);
    }

    private class MyOnGestureListner extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            onClick(btn_play);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onClick(btn_screen);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isShowControlLayout) {
                //隐藏操作
                hideControlLayout();
            } else {
                //显示操作
                showControlLayout();
            }
            return super.onSingleTapConfirmed(e);
        }

    }

    /**
     * 电池电量变化的广播接受者
     *
     * @author Administrator
     */
    private class BatteryChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //level:0-100
            int level = intent.getIntExtra("level", 0);
            updateBatteryBg(level);
        }
    }


}
