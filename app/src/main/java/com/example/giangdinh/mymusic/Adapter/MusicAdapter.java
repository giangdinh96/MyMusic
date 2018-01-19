package com.example.giangdinh.mymusic.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.giangdinh.mymusic.Model.MusicModel;
import com.example.giangdinh.mymusic.PlayMusicActivity.IPlayMusicItemListener;
import com.example.giangdinh.mymusic.R;
import com.example.giangdinh.mymusic.Utils.ColorUtils;
import com.example.giangdinh.mymusic.Utils.UriUtils;

import java.util.ArrayList;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MusicModel> musicList;
    private LayoutInflater layoutInflater;
    private RequestManager requestManagerGlide;
    private IPlayMusicItemListener iPlayMusicItemListener;

    public MusicAdapter(Context context, ArrayList<MusicModel> musicList) {
        this.musicList = musicList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.requestManagerGlide = Glide.with(context);
        this.iPlayMusicItemListener = (IPlayMusicItemListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_music, parent, false);
        MusicHolder musicHolder = new MusicHolder(view);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicHolder musicHolder = (MusicHolder) holder;
        MusicModel musicModel = musicList.get(position);

        requestManagerGlide.load(UriUtils.getUriPictureFromMusicModel(musicModel)).into(musicHolder.ivPicture);
        musicHolder.tvTitle.setText(musicModel.getTitle());
        musicHolder.tvArtist.setText(musicModel.getArtist());
        musicHolder.tvDuration.setText(musicModel.getTimeDuration());

        // Set background for tvDuration
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(ColorUtils.getColorRandom());
        shape.setCornerRadius(100);
        musicHolder.tvDuration.setBackground(shape);


        musicHolder.setItemAnimation(context);
        musicHolder.setOnMenuItemClickListener(musicModel);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        private ImageView ivPicture;
        private TextView tvTitle;
        private TextView tvArtist;
        private TextView tvDuration;

        public MusicHolder(View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }

        public void setItemAnimation(Context context) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_music_item);
            itemView.startAnimation(animation);
        }

        public void setDurationItemAnimation(Context context) {

        }

        public void setOnMenuItemClickListener(final MusicModel musicModel) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iPlayMusicItemListener.setOnMusicItemClickListener(musicModel);
                }
            });
        }
    }
}
