package com.example.giangdinh.mymusic.Utils;

import android.content.ContentUris;
import android.net.Uri;

import com.example.giangdinh.mymusic.Model.MusicModel;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class UriUtils {
    public static Uri getUriPictureFromMusicModel(MusicModel musicModel) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(sArtworkUri, musicModel.getAlbumId());
        return uri;
    }
}
