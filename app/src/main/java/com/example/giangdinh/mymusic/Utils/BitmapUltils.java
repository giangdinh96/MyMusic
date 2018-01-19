package com.example.giangdinh.mymusic.Utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.giangdinh.mymusic.Model.MusicModel;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class BitmapUltils {
    public static Bitmap getBitmapFromMp3(Context context, MusicModel musicModel) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(sArtworkUri, musicModel.getAlbumId());
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
