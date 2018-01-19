package com.example.giangdinh.mymusic.Model;

import com.example.giangdinh.mymusic.Utils.TimeUltils;

import java.io.Serializable;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class MusicModel implements Serializable {
    private String title;
    private String artist;
    private String path;
    private int albumId;
    private int duration;

    public MusicModel() {

    }

    public MusicModel(String title, String artist, String path, int albumId, int duration) {
        this.title = title;
        this.artist = artist;
        this.path = path;
        this.albumId = albumId;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMinuteFromDuration() {
        return TimeUltils.getMinuteFromDuration(duration);
    }

    public String getResidualSecondFromDuration() {
        return TimeUltils.getResidualSecondFromDuration(duration);
    }

    public String getTimeDuration() {
        return TimeUltils.getTimeDuration(duration);
    }
}
