package com.example.myplayer.activity;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.bean.AudioItem;
import com.example.myplayer.bean.Lyric;
import com.example.myplayer.service.AudioPlayService;
import com.example.myplayer.util.StringUtil;
import com.example.myplayer.util.ToastUtil;
import com.example.myplayer.view.LyricView;
import com.example.myplayer.widget.lyric.LyricLoader;
import com.example.myplayer.widget.lyric.LyricParser;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：Administrator on 2016/4/24 18:14
 * <p/>
 * 邮箱：zhangdongsheng2@sina.com
 */
public class AudioPlayActivity extends Activity implements View.OnClickListener {
    public static final String POSITION = "currentPosition";
    public static final String AUDIOLIST = "audioList";
    private final int MSG_UPDATE_PROGRESS = 0;//更新播放进度
    private final int MSG_ROLL_LYRIC = 1;//滚动歌词
    private TextView tv_title, tv_artist, tv_time;
    private ImageView iv_anim, btn_back;
    private ImageView iv_mode, iv_play, iv_pre, iv_next;
    private SeekBar audio_seekbar;
    private LyricView lyric_view;
    private AudioServiceConnection serviceConnection;
    private AudioPlayService.AudioServiceBinder audioServiceBinder;
    private AudioServiceReceiver receiver;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_PROGRESS:
                    updatePlayProgress();
                    break;
                case MSG_ROLL_LYRIC:
                    updateLyric();
                    break;
            }
        }

        ;
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * 更新歌词
     */
    private void updateLyric() {
        lyric_view.roll(audioServiceBinder.getCurrentPosition(), audioServiceBinder.getDuration());
        handler.sendEmptyMessage(MSG_ROLL_LYRIC);
    }

    /**
     * 更新播放进度
     */
    private void updatePlayProgress() {
        audio_seekbar.setProgress((int) audioServiceBinder.getCurrentPosition());
        tv_time.setText(StringUtil.formatVideoDuration(audioServiceBinder.getCurrentPosition())
                + "/" + StringUtil.formatVideoDuration(audioServiceBinder.getDuration()));
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    protected void enterActivity(Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    protected void enterActivity(Bundle bundle, Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void initView() {
        setContentView(R.layout.activity_audio_player);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_artist = (TextView) findViewById(R.id.tv_artist);
        iv_mode = (ImageView) findViewById(R.id.iv_mode);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_pre = (ImageView) findViewById(R.id.iv_pre);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        audio_seekbar = (SeekBar) findViewById(R.id.audio_seekbar);
        lyric_view = (LyricView) findViewById(R.id.lyric_view);

        iv_anim = (ImageView) findViewById(R.id.iv_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_anim.getBackground();
        animationDrawable.start();
    }

    protected void initListener() {
        btn_back.setOnClickListener(this);
        iv_mode.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_pre.setOnClickListener(this);
        iv_next.setOnClickListener(this);
        audio_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    audioServiceBinder.seekTo(progress);
                    tv_time.setText(StringUtil.formatVideoDuration(progress)
                            + "/" + StringUtil.formatVideoDuration(audioServiceBinder.getDuration()));
                }
            }
        });
    }

    protected void initData() {
        registerAudioServiceReceiver();

        serviceConnection = new AudioServiceConnection();

        Intent intent = new Intent(this, AudioPlayService.class);
        Bundle bundle = new Bundle();
        boolean isFromNotification = getIntent().getBooleanExtra("isFromNotification", false);
        if (isFromNotification) {
            //如果是点击通知开启的activity

            intent.putExtra("isFromNotification", isFromNotification);
            intent.putExtra("notification_view", getIntent().getIntExtra("notification_view", -1));
        } else {
            //从音乐列表进入的
            int currentPosition = getIntent().getExtras().getInt("currentPosition");
            ArrayList<AudioItem> audioList = (ArrayList<AudioItem>) getIntent().getExtras().getSerializable("audioList");
            bundle.putInt("currentPosition", currentPosition);
            bundle.putSerializable("audioList", audioList);
            intent.putExtras(bundle);
        }
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        startService(intent);//为了给service传递数据
    }

    /**
     * 注册AudioService中的广播接受者
     */
    private void registerAudioServiceReceiver() {
        receiver = new AudioServiceReceiver();
        IntentFilter filter = new IntentFilter(AudioPlayService.ACTION_NOTIFY_PREPARED);
        filter.addAction(AudioPlayService.ACTION_COMPLATION);
        registerReceiver(receiver, filter);
    }


    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_mode:
                audioServiceBinder.switchPlayMode();
                updatePlayModeBg(true);
                break;
            case R.id.iv_play:
                if (audioServiceBinder.isPlaying()) {
                    audioServiceBinder.pause();
                } else {
                    audioServiceBinder.start();
                }
                updatePlayBtnBg();
                break;
            case R.id.iv_pre:
                if (audioServiceBinder.isPlayingFirst()) {
                    ToastUtil.showToast("当前是第一首");
                } else {
                    audioServiceBinder.playPre();
                }
                break;
            case R.id.iv_next:
                if (audioServiceBinder.isPlayingLast()) {
                    ToastUtil.showToast("当前是最后一首");
                } else {
                    audioServiceBinder.playNext();
                }
                break;
        }
    }

    /**
     * 更新播放模式按钮的背景图片
     *
     * @param isShowToast
     */
    private void updatePlayModeBg(boolean isShowToast) {
        switch (audioServiceBinder.getPlayMode()) {
            case AudioPlayService.MODE_ORDER:
                if (isShowToast) ToastUtil.showToast("顺序播放");
                iv_mode.setBackgroundResource(R.drawable.selector_playmode_order);
                break;
            case AudioPlayService.MODE_SINGLE_REPEAT:
                if (isShowToast) ToastUtil.showToast("单曲循环");
                iv_mode.setBackgroundResource(R.drawable.selector_playmode_single);
                break;
            case AudioPlayService.MODE_ALL_REPEAT:
                if (isShowToast) ToastUtil.showToast("循环播放");
                iv_mode.setBackgroundResource(R.drawable.selector_playmode_allrepeat);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 更新播放按钮的背景图图片
     */
    private void updatePlayBtnBg() {
        iv_play.setBackgroundResource(audioServiceBinder.isPlaying() ?
                R.drawable.selector_btn_audio_pause : R.drawable.selector_btn_audio_play);
    }


    class AudioServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioPlayService.ACTION_NOTIFY_PREPARED.equals(intent.getAction())) {
                AudioItem audioItem = (AudioItem) intent.getSerializableExtra("audioItem");

                audio_seekbar.setMax((int) audioItem.getDuration());
                iv_play.setBackgroundResource(R.drawable.selector_btn_audio_pause);
                tv_title.setText(StringUtil.formatAudioName(audioItem.getTitle()));
                tv_artist.setText(audioItem.getArtist());
                updatePlayModeBg(false);
                updatePlayProgress();


                File file = LyricLoader.loadLyricFileByName(audioItem.getTitle());//加载歌词文件
                ArrayList<Lyric> lyricList = LyricParser.parseLyricFromFile(file);//解析歌词文件
                lyric_view.setLyricList(lyricList);
                updateLyric();//开始滚动歌词
            } else if (AudioPlayService.ACTION_COMPLATION.equals(intent.getAction())) {
                iv_play.setBackgroundResource(R.drawable.selector_btn_audio_play);
                tv_time.setText(StringUtil.formatVideoDuration(audioServiceBinder.getDuration())
                        + "/" + StringUtil.formatVideoDuration(audioServiceBinder.getDuration()));

                handler.removeMessages(MSG_ROLL_LYRIC);
                handler.removeMessages(MSG_UPDATE_PROGRESS);
            }
        }
    }

    class AudioServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            audioServiceBinder = (AudioPlayService.AudioServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


}
