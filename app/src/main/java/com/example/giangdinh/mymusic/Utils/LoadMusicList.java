package com.example.giangdinh.mymusic.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.example.giangdinh.mymusic.Model.MusicModel;
import com.example.giangdinh.mymusic.PlayMusicActivity.IPlayMusicView;

import java.util.ArrayList;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class LoadMusicList extends AsyncTask<Uri, Integer, ArrayList<MusicModel>> {
    private Context context;
    private IPlayMusicView iPlayMusicView;

    public LoadMusicList(Context context, IPlayMusicView iPlayMusicView) {
        this.context = context;
        this.iPlayMusicView = iPlayMusicView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<MusicModel> doInBackground(Uri... songUri) {
        ArrayList<MusicModel> musicList = new ArrayList<>();
        Cursor songCursor = context.getContentResolver().query(songUri[0], null,
                MediaStore.Audio.Media.DATA + " LIKE ? ",
                new String[]{"%.mp3%"}, MediaStore.Audio.Media.DURATION + "," + MediaStore.Audio.Media.TITLE + " ASC");

        if (songCursor != null && songCursor.moveToFirst()) {
            int indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int indexAlbumId = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int indexDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            MusicModel musicModel;
            do {
                musicModel = new MusicModel();
                musicModel.setTitle(songCursor.getString(indexTitle));
                musicModel.setArtist(songCursor.getString(indexArtist));
                musicModel.setPath(songCursor.getString(indexPath));
                musicModel.setAlbumId(songCursor.getInt(indexAlbumId));
                musicModel.setDuration(songCursor.getInt(indexDuration));
                musicList.add(musicModel);
            } while (songCursor.moveToNext());
        }
        return musicList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<MusicModel> musicList) {
        super.onPostExecute(musicList);
        iPlayMusicView.loadMusicList(musicList);
    }
}
