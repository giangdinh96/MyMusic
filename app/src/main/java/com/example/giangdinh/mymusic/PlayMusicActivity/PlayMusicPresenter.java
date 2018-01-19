package com.example.giangdinh.mymusic.PlayMusicActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.giangdinh.mymusic.Model.MusicModel;
import com.example.giangdinh.mymusic.Utils.LoadMusicList;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class PlayMusicPresenter implements IPlayMusicPresenter {
    private Context context;
    private IPlayMusicView iPlayMusicView;

    public PlayMusicPresenter() {

    }

    public PlayMusicPresenter(Context context, IPlayMusicView iPlayMusicView) {
        this.context = context;
        this.iPlayMusicView = iPlayMusicView;
    }

    @Override
    public void handleStartMusic() {
        iPlayMusicView.setVisibilityStart(false);
        iPlayMusicView.setVisibilityStop(true);
        iPlayMusicView.setAnimationPictureCircle();
        Intent intent = new Intent();
        intent.setAction(PlayMusicService.PLAY_MUSIC_SERVICE_ACTION_START_CLICK);
        context.sendBroadcast(intent);
    }

    @Override
    public void handleStopMusic() {
        iPlayMusicView.setVisibilityStop(false);
        iPlayMusicView.setVisibilityStart(true);
        iPlayMusicView.clearAnimationPictureCircle();
        Intent intent = new Intent();
        intent.setAction(PlayMusicService.PLAY_MUSIC_SERVICE_ACTION_STOP_CLICK);
        context.sendBroadcast(intent);

//        Intent intentStopService = new Intent(context, PlayMusicService.class);
//        context.stopService(intentStopService);
    }

    @Override
    public void handleSeekBarChange(int currentTime) {
        Intent intent = new Intent();
        intent.putExtra(PlayMusicService.PLAY_MUSIC_SERVICE_EXTRA_CURRENT_TIME, currentTime);
        intent.setAction(PlayMusicService.PLAY_MUSIC_SERVICE_ACTION_SEEK_BAR_CHANGE);
        context.sendBroadcast(intent);
    }

    @Override
    public void handleLoadMusicList(Uri uri) {
        LoadMusicList loadMusicList = new LoadMusicList(context, iPlayMusicView);
        loadMusicList.execute(uri);
    }

    @Override
    public void handleMusicItemClick(MusicModel musicModel) {
        iPlayMusicView.setVisibilityStart(false);
        iPlayMusicView.setVisibilityStop(true);
        iPlayMusicView.loadPictureCircle(musicModel);
        iPlayMusicView.setTimeRun("00:00");
        iPlayMusicView.setTimeEnd(musicModel.getTimeDuration());
        iPlayMusicView.setSeekBar(0);
        iPlayMusicView.setSeekBarMax(musicModel.getDuration());
        Intent intent = new Intent();
        intent.putExtra(PlayMusicService.PLAY_MUSIC_SERVICE_EXTRA_MUSIC_MODEL, musicModel);
        intent.setAction(PlayMusicService.PLAY_MUSIC_SERVICE_ACTION_MUSIC_ITEM_CLICK);
        context.sendBroadcast(intent);
    }
}