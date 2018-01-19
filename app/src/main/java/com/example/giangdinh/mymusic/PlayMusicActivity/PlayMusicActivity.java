package com.example.giangdinh.mymusic.PlayMusicActivity;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.giangdinh.mymusic.Adapter.MusicAdapter;
import com.example.giangdinh.mymusic.Model.MusicModel;
import com.example.giangdinh.mymusic.R;
import com.example.giangdinh.mymusic.Utils.TimeUltils;
import com.example.giangdinh.mymusic.Utils.UriUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity implements IPlayMusicView {

    private ImageView ivPictureCircle;
    private ImageView ivStart;
    private ImageView ivStop;
    private ImageView ivPlay;

    private TextView tvTimeRun;
    private TextView tvTimeEnd;
    private SeekBar sbTime;

    private IPlayMusicPresenter iPlayMusicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        setViews();
        setEvents();
    }

    private void setViews() {
        ivPictureCircle = findViewById(R.id.ivPictureCircle);
        ivStart = findViewById(R.id.ivStart);
        ivStop = findViewById(R.id.ivStop);
        ivPlay = findViewById(R.id.ivPlay);

        tvTimeRun = findViewById(R.id.tvTimeRun);
        tvTimeEnd = findViewById(R.id.tvTimeEnd);
        sbTime = findViewById(R.id.sbTime);

        loadPictureCircleDefault();
        loadPictureHeaderDefault();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

        iPlayMusicPresenter = new PlayMusicPresenter(this, this);
        iPlayMusicPresenter.handleLoadMusicList(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(this, PlayMusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setSeekBar(playMusicService.getCurrentTime());
                setTimeRun(TimeUltils.getTimeDuration(playMusicService.getCurrentTime()));
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private PlayMusicService playMusicService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            playMusicService = ((PlayMusicService.BinderLocal) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playMusicService = null;
        }
    };

    private void setEvents() {
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPlayMusicPresenter.handleStartMusic();
            }
        });

        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPlayMusicPresenter.handleStopMusic();
            }
        });

        sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iPlayMusicPresenter.handleSeekBarChange(seekBar.getProgress());
            }
        });
    }

    @Override
    public void setVisibilityStart(boolean visibilityStart) {
        if (visibilityStart) {
            ivStart.setVisibility(View.VISIBLE);
        } else {
            ivStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void setVisibilityStop(boolean visibilityStop) {
        if (visibilityStop) {
            ivStop.setVisibility(View.VISIBLE);
        } else {
            ivStop.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAnimationPictureCircle() {
        Animation animation = AnimationUtils.loadAnimation(PlayMusicActivity.this, R.anim.anim_music_circle);
        ivPictureCircle.startAnimation(animation);
    }

    @Override
    public void clearAnimationPictureCircle() {
        ivPictureCircle.clearAnimation();
    }

    @Override
    public void loadMusicList(ArrayList<MusicModel> musicList) {
        RecyclerView recyclerView = findViewById(R.id.rvMusicList);
        MusicAdapter musicAdapter = new MusicAdapter(PlayMusicActivity.this, musicList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PlayMusicActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(musicAdapter);
    }

    @Override
    public void loadPictureCircleDefault() {
        Glide.with(this).load(R.drawable.ic_picture_circle).placeholder(R.drawable.ic_picture_circle).fitCenter().into(ivPictureCircle);
    }

    @Override
    public void loadPictureCircle(MusicModel musicModel) {
        Uri uriImage = UriUtils.getUriPictureFromMusicModel(musicModel);
        Drawable drawable = null;
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uriImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            Toast.makeText(this, "InputStream is null !", Toast.LENGTH_SHORT).show();
            return;
        }
        drawable = Drawable.createFromStream(inputStream, "null");
        Glide.with(this).load(uriImage).priority(Priority.IMMEDIATE).placeholder(drawable).into(ivPictureCircle);
        setAnimationPictureCircle();
    }

    @Override
    public void loadPictureHeaderDefault() {
        GlideDrawableImageViewTarget glideDrawableImageViewTarget = new GlideDrawableImageViewTarget(ivPlay);
        Glide.with(this).load(R.drawable.ic_picture_header).into(glideDrawableImageViewTarget);
    }

    @Override
    public void setTimeRun(String timeRun) {
        tvTimeRun.setText(timeRun);
    }

    @Override
    public void setTimeEnd(String timeEnd) {
        tvTimeEnd.setText(timeEnd);
    }

    @Override
    public void setSeekBar(int time) {
        sbTime.setProgress(time);
    }

    @Override
    public void setSeekBarMax(int timeMax) {
        sbTime.setMax(timeMax);
    }

    @Override
    public void setOnMusicItemClickListener(MusicModel musicModel) {
        iPlayMusicPresenter.handleMusicItemClick(musicModel);
    }
}