package com.example.myplayer.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;

import com.example.myplayer.R;
import com.example.myplayer.activity.AudioPlayActivity;
import com.example.myplayer.bean.AudioItem;
import com.example.myplayer.widget.lyric.StringUtil;

import java.io.IOException;
import java.util.ArrayList;

public class AudioPlayService extends Service {
    public static final int MODE_ORDER = 0;//顺序播放
    public static final int MODE_SINGLE_REPEAT = 1;//单曲循环
    public static final int MODE_ALL_REPEAT = 2;//循环播放
    public static String ACTION_NOTIFY_PREPARED = "ACTION_NOTIFY_PREPARED";//准备完成
    public static String ACTION_COMPLATION = "ACTION_COMPLATION";//播放完成
    private final int VIEW_PRE = 1;//上一首
    private final int VIEW_NEXT = 2;//下一首
    private final int VIEW_CONTAINER = 3;//通知整体的布局
    private int playMode = MODE_ORDER;//默认是顺序播放

    private AudioServiceBinder audioServiceBinder;
    private int currentPosition;
    private ArrayList<AudioItem> audioList;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sp;
    private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            notifyPrepared();
            sendNotification();
        }
    };
    private OnCompletionListener mOnCompletionListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            notifyCompletion();
            autoPlayByMode();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return audioServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        audioServiceBinder = new AudioServiceBinder();
        sp = getSharedPreferences("playmode.cfg", MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isFromNotification = intent.getBooleanExtra("isFromNotification", false);
        if (isFromNotification) {
            int notification_view = intent.getIntExtra("notification_view", -1);
            switch (notification_view) {
                case VIEW_PRE:
                    audioServiceBinder.playPre();
                    break;
                case VIEW_NEXT:
                    audioServiceBinder.playNext();
                    break;
                case VIEW_CONTAINER:
                    notifyPrepared();
                    break;
            }
        } else {
            currentPosition = intent.getExtras().getInt("currentPosition");
            audioList = (ArrayList<AudioItem>) intent.getExtras().getSerializable("audioList");
            audioServiceBinder.playAudio();
        }

        playMode = getPlayModeFromSp();
        return START_STICKY;//如果服务被杀死，会自动重启
    }

    /**
     * 发送通知
     */
    private void sendNotification() {
        AudioItem audioItem = audioList.get(currentPosition);

        Builder builder = new Builder(this);
        builder.setSmallIcon(R.drawable.notification_music_playing)
                .setTicker("正在播放：" + StringUtil.formatAudioName(audioItem.getTitle()))
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);

        if (VERSION.SDK_INT > 10) {
            //3.0以上的系统
            builder.setContent(getRemoteViews(audioItem));
        } else {
            //3.0以下的系统
            builder.setContentIntent(getContainerPendingIntent());
        }

        startForeground(1, builder.build());
    }

    private RemoteViews getRemoteViews(AudioItem audioItem) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.notification_title, StringUtil.formatAudioName(audioItem.getTitle()));
        remoteViews.setTextViewText(R.id.notification_artist, audioItem.getArtist());

        remoteViews.setOnClickPendingIntent(R.id.notification_pre, getPrePendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.notification_next, getNextPendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.notification_layout, getContainerPendingIntent());
        return remoteViews;
    }

    private PendingIntent getPrePendingIntent() {
        Intent intent = new Intent(this, AudioPlayService.class);
        intent.putExtra("notification_view", VIEW_PRE);
        intent.putExtra("isFromNotification", true);
        PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    private PendingIntent getNextPendingIntent() {
        Intent intent = new Intent(this, AudioPlayService.class);
        intent.putExtra("notification_view", VIEW_NEXT);
        intent.putExtra("isFromNotification", true);
        PendingIntent contentIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    private PendingIntent getContainerPendingIntent() {
        Intent intent = new Intent(this, AudioPlayActivity.class);
        intent.putExtra("isFromNotification", true);
        intent.putExtra("notification_view", VIEW_CONTAINER);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    /**
     * 通知准备完成
     */
    private void notifyPrepared() {
        Intent intent = new Intent(ACTION_NOTIFY_PREPARED);
        intent.putExtra("audioItem", audioList.get(currentPosition));
        sendBroadcast(intent);
    }

    /**
     * 根据播放模式自动播放
     */
    private void autoPlayByMode() {
        switch (playMode) {
            case MODE_ORDER://从第一首播到最后一首，最后播完就停止
                audioServiceBinder.playNext();
                break;
            case MODE_SINGLE_REPEAT:
                audioServiceBinder.playAudio();
                break;
            case MODE_ALL_REPEAT://从第一到最后，最后播完再播第一首
                if (currentPosition == (audioList.size() - 1)) {
                    currentPosition = 0;
                    audioServiceBinder.playAudio();
                } else {
                    audioServiceBinder.playNext();
                }
                break;
        }
    }

    /**
     * 通知播放完成
     */
    private void notifyCompletion() {
        Intent intent = new Intent(ACTION_COMPLATION);
        intent.putExtra("audioItem", audioList.get(currentPosition));
        sendBroadcast(intent);
    }

    /**
     * 将playMOde保存到SharePerence
     */
    private void savePlayModeToSp() {
        sp.edit().putInt("playMode", playMode).commit();
    }

    /**
     * 从SharePerences中获取播放模式
     *
     * @return
     */
    private int getPlayModeFromSp() {
        return sp.getInt("playMode", MODE_ORDER);
    }

    public class AudioServiceBinder extends Binder {
        /**
         * 播放音乐
         */
        public void playAudio() {
            if (audioList == null || audioList.size() == 0) return;
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            AudioItem audioItem = audioList.get(currentPosition);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mediaPlayer.setOnCompletionListener(mOnCompletionListener);
            try {
                mediaPlayer.setDataSource(audioItem.getPath());
                mediaPlayer.prepareAsync();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 是否在播放第一首
         *
         * @return
         */
        public boolean isPlayingFirst() {
            return currentPosition == 0;
        }

        /**
         * 是否在播放最后一首
         *
         * @return
         */
        public boolean isPlayingLast() {
            return currentPosition == (audioList.size() - 1);
        }

        public void playPre() {
            if (currentPosition > 0) {
                currentPosition--;
                playAudio();
            }
        }

        public void playNext() {
            if (currentPosition < (audioList.size() - 1)) {
                currentPosition++;
                playAudio();
            }
        }

        public void pause() {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
            stopForeground(true);
        }

        public boolean isPlaying() {
            return mediaPlayer != null ? mediaPlayer.isPlaying() : false;
        }

        public void start() {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            sendNotification();
        }

        /**
         * 获取当前音乐播放的位置
         *
         * @return
         */
        public long getCurrentPosition() {
            return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
        }

        public long getDuration() {
            return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
        }

        public void seekTo(long progress) {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo((int) progress);
            }
        }

        /**
         * 切换播放模式
         */
        public void switchPlayMode() {
            switch (playMode) {
                case MODE_ORDER:
                    playMode = MODE_SINGLE_REPEAT;
                    break;
                case MODE_SINGLE_REPEAT:
                    playMode = MODE_ALL_REPEAT;
                    break;
                case MODE_ALL_REPEAT:
                    playMode = MODE_ORDER;
                    break;
            }
            savePlayModeToSp();
        }

        /**
         * 获取当前播放模式
         *
         * @return
         */
        public int getPlayMode() {
            return playMode;
        }
    }

}
