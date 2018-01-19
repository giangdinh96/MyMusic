package com.example.giangdinh.mymusic.PlayMusicActivity;

import android.net.Uri;

import com.example.giangdinh.mymusic.Model.MusicModel;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public interface IPlayMusicPresenter {
    public void handleStartMusic();

    public void handleStopMusic();

    public void handleSeekBarChange(int currentTime);

    public void handleLoadMusicList(Uri uri);

    public void handleMusicItemClick(MusicModel musicModel);
}
