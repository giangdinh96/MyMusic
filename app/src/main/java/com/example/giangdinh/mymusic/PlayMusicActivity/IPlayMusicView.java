package com.example.giangdinh.mymusic.PlayMusicActivity;

import com.example.giangdinh.mymusic.Model.MusicModel;

import java.util.ArrayList;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public interface IPlayMusicView extends IPlayMusicItemListener {
    public void setVisibilityStart(boolean visibilityStart);

    public void setVisibilityStop(boolean visibilityStop);

    public void setAnimationPictureCircle();

    public void clearAnimationPictureCircle();

    public void loadMusicList(ArrayList<MusicModel> musicList);

    public void loadPictureCircleDefault();

    public void loadPictureCircle(MusicModel musicModel);

    public void loadPictureHeaderDefault();

    public void setTimeRun(String timeRun);

    public void setTimeEnd(String timeEnd);

    public void setSeekBar(int time);

    public void setSeekBarMax(int timeMax);
}
