package com.example.giangdinh.mymusic.PlayMusicActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.giangdinh.mymusic.Model.MusicModel;
import com.example.giangdinh.mymusic.R;

public class PlayMusicService extends Service {

    public static final String PLAY_MUSIC_SERVICE_ACTION_START_CLICK = "PlayMusicService.Action.StartClick";
    public static final String PLAY_MUSIC_SERVICE_ACTION_STOP_CLICK = "PlayMusicService.Action.StopClick";
    public static final String PLAY_MUSIC_SERVICE_ACTION_SEEK_BAR_CHANGE = "PlayMusicService.Action.SeekBarChange";
    public static final String PLAY_MUSIC_SERVICE_ACTION_NEXT_CLICK = "PlayMusicService.Action.NextClick";
    public static final String PLAY_MUSIC_SERVICE_ACTION_PREVIOUS_CLICK = "PlayMusicService.Action.PreviousClick";
    public static final String PLAY_MUSIC_SERVICE_ACTION_MUSIC_ITEM_CLICK = "PlayMusicService.Action.MusicItemClick";
    public static final String PLAY_MUSIC_SERVICE_EXTRA_MUSIC_MODEL = "PlayMusicService.Extra.MusicModel";
    public static final String PLAY_MUSIC_SERVICE_EXTRA_CURRENT_TIME = "PlayMusicService.Extra.CurrentTime";

    private static MediaPlayer mediaPlayer;
    private IPlayMusicView iPlayMusicView;

    public class BinderLocal extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Test", "onBind");
        return new BinderLocal();
    }

    @Override
    public void onCreate() {
        Log.d("Test", "onCreate");
        super.onCreate();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.rapture_panorama_panama_town);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Test", "onStartCommand");
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("Test", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("Test", "onDestroy");
        super.onDestroy();
    }

    public int getCurrentTime() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        return 0;
    }

    public void handleStartClick() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void handleStopClick() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void handleSeekBarChange(int currentTime) {
        mediaPlayer.seekTo(currentTime);
    }

    public void handleMusicItemClick(MusicModel musicModel) {
        if (mediaPlayer != null || mediaPlayer.isPlaying()) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(musicModel.getPath()));
        mediaPlayer.start();
    }

    public static class PlayMusicReceiver extends BroadcastReceiver {
        private Context context;

        @Override
        public void onReceive(Context context, Intent intent) {
            this.context = context;
            switch (intent.getAction()) {
                case PLAY_MUSIC_SERVICE_ACTION_START_CLICK:
                    handleStartClick();
                    break;
                case PLAY_MUSIC_SERVICE_ACTION_STOP_CLICK:
                    handleStopClick();
                    break;
                case PLAY_MUSIC_SERVICE_ACTION_SEEK_BAR_CHANGE:
                    int currentTime = intent.getIntExtra(PLAY_MUSIC_SERVICE_EXTRA_CURRENT_TIME, 0);
                    handleSeekBarChange(currentTime);
                    break;
                case PLAY_MUSIC_SERVICE_ACTION_NEXT_CLICK:
                    break;
                case PLAY_MUSIC_SERVICE_ACTION_PREVIOUS_CLICK:
                    break;
                case PLAY_MUSIC_SERVICE_ACTION_MUSIC_ITEM_CLICK:
                    MusicModel musicModel = (MusicModel) intent.getSerializableExtra(PlayMusicService.PLAY_MUSIC_SERVICE_EXTRA_MUSIC_MODEL);
                    handleMusicItemClick(musicModel);
                    break;
                default:
                    Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private void handleStartClick() {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }

        private void handleStopClick() {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }

        private void handleSeekBarChange(int currentTime) {
            mediaPlayer.seekTo(currentTime);
        }

        private void handleMusicItemClick(MusicModel musicModel) {
            if (mediaPlayer != null || mediaPlayer.isPlaying()) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(musicModel.getPath()));
            mediaPlayer.start();
        }
    }
}