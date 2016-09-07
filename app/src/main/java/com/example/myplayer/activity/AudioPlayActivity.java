package com.example.myplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.service.PlayService;
import com.example.myplayer.util.LogUtils;
import com.example.myplayer.util.StringUtil;
import com.example.myplayer.util.ToastUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * 作者：Administrator on 2016/4/24 18:14
 * <p/>
 * 邮箱：zhangdongsheng2@sina.com
 */
public class AudioPlayActivity extends Activity implements View.OnClickListener {
    public static final String POSITION = "position";
    public static final String AUDIOLIST = "audiolist";
    private final int MSG_UPDATE_SYSTEM_TIME = 0;//更新系统时间
    private final int MSG_UPDATE_PLAY_PROGRESS = 1;//更新播放进度
    private final int MSG_HIDE_CONTROL = 2;//延时隐藏控制面板
    private long currentPositionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

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

    //------------------------------------------------------------------------
    private int currentPosition;//当前播放视频的位置
    private ArrayList<VideoItem> videoList;//当前的视频列表
    private BatteryChangeReceiver batteryChangeReceiver;
    private AudioManager audioManager;
    private int touchSlop;
    private GestureDetector gestureDetector;
    //--------------------------------------------------------------------
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


    /**
     * 更新系统时间  每隔一秒
     */
    private void updateSystemTime() {
        tv_system_time.setText(StringUtil.formatSystemTime());
        handler.sendEmptyMessageDelayed(MSG_UPDATE_SYSTEM_TIME, 1000);
    }

    /**
     * 更新播放进度  每隔0.5秒
     */
    private void updatePlayProgress() {
        tv_current_position.setText(StringUtil.formatVideoDuration(video_view.getCurrentPosition()));
        video_seekbar.setProgress((int) video_view.getCurrentPosition());
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PLAY_PROGRESS, 500);
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        //检查vitamio类库是否正确加载
        LibsChecker.checkVitamioLibs(this);

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
        //把top和bottom布局移动隐藏
        ll_top_control.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_top_control.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewPropertyAnimator.animate(ll_top_control).translationY(-ll_top_control.getHeight()).setDuration(0);
            }
        });
        ll_bottom_control.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_bottom_control.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewPropertyAnimator.animate(ll_bottom_control).translationY(ll_bottom_control.getHeight()).setDuration(0);
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
                    tv_current_position.setText(StringUtil.formatVideoDuration(progress));
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

    @Override
    public void onClick(View v) {
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
//                    video_view.switchScreen();
                updateScreenBtnBg();
                break;
        }
    }

    protected void initData() {
        //监控页面的手势行为，单击长按双击等。
        gestureDetector = new GestureDetector(this, new MyOnGestureListner());
        //判断用户是否是在做手势滑动行为
        touchSlop = ViewConfiguration.getTouchSlop();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        updateSystemTime();
        registerBatteryReceiver();
        initVolume();

        try {
            currentPositionTime = getIntent().getExtras().getLong("currentPosition", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //外部跳转进来的
        Uri videoUri = getIntent().getData();
        if (videoUri != null) {
            //从文件发起的请求
            LogUtils.e("uri: " + videoUri.getPath());
            video_view.setVideoURI(videoUri);
            btn_pre.setEnabled(false);
            btn_next.setEnabled(false);
            tv_name.setText(videoUri.getPath());
        } else {
            //正常从视频列表中进入的
            currentPosition = getIntent().getExtras().getInt(POSITION);
            videoList = (ArrayList<VideoItem>) getIntent().getExtras().getSerializable(AUDIOLIST);

            playVideo();
        }


        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ViewPropertyAnimator.animate(ll_loading).alpha(0).setDuration(1000).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        ll_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator arg0) {
                    }
                });

                video_view.start();

                updatePlayProgress();
                video_seekbar.setMax((int) video_view.getDuration());
                tv_current_position.setText("00:00");
                tv_current_position.setText(StringUtil.formatVideoDuration(currentPositionTime));
                video_view.seekTo(currentPositionTime);
                currentPositionTime = 0;
                tv_duration.setText(StringUtil.formatVideoDuration(video_view.getDuration()));

                btn_play.setImageResource(R.drawable.selector_btn_pause);
            }
        });

//		video_view.setMediaController(new MediaController(this));//启用系统自带控制器，播放暂停等控制
    }

    private float downY;

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
                LogUtils.e("movePercent: " + movePercent);
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
//        btn_screen.setImageResource(video_view.isFullScreen() ?
//                R.drawable.selector_btn_defaultscreen : R.drawable.selector_btn_fullscreen);
        final long currentPositionTime = video_view.getCurrentPosition();

        finish();
        new Thread() {
            @Override
            public void run() {
                Intent intentHome = new Intent(Intent.ACTION_MAIN);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intentHome.addCategory(Intent.CATEGORY_HOME);
                startActivity(intentHome);
                Intent playIntent = new Intent(AudioPlayActivity.this, PlayService.class);
                Bundle extras = new Bundle();
                extras.putInt(VideoPlayerActivity.POSITION, currentPosition);
                extras.putSerializable(VideoPlayerActivity.VIDEOLIST, videoList);
                extras.putLong("currentPosition", currentPositionTime);
                playIntent.putExtras(extras);
                playIntent.setData(getIntent().getData());
                playIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(playIntent);
            }
        }.start();
    }

//    // 点击HOME键时程序进入后台运行
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        // 按下HOME键
//        if (keyCode == KeyEvent.KEYCODE_HOME) {
//            // 显示Notification
//
//
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(batteryChangeReceiver);
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

    private void showControlLayout() {
        ViewPropertyAnimator.animate(ll_top_control).translationY(0).setDuration(200);
        ViewPropertyAnimator.animate(ll_bottom_control).translationY(0).setDuration(200);
        isShowControlLayout = true;
        handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL, 5000);
    }

    private void hideControlLayout() {
        ViewPropertyAnimator.animate(ll_top_control).translationY(-ll_top_control.getHeight()).setDuration(200);
        ViewPropertyAnimator.animate(ll_bottom_control).translationY(ll_bottom_control.getHeight()).setDuration(200);
        isShowControlLayout = false;
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

    private void updateBatteryBg(int level) {
        if (level == 0) {
            iv_battery.setImageResource(R.drawable.ic_battery_0);
        } else if (level > 0 && level <= 20) {
            iv_battery.setImageResource(R.drawable.ic_battery_1);
        } else if (level > 20 && level <= 50) {
            iv_battery.setImageResource(R.drawable.ic_battery_2);
        } else if (level > 50 && level <= 70) {
            iv_battery.setImageResource(R.drawable.ic_battery_4);
        } else if (level > 70 && level <= 90) {
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

}
