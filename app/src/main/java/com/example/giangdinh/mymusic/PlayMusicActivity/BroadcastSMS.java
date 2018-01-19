package com.example.giangdinh.mymusic.PlayMusicActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by GiangDinh on 17/01/2018.
 */

public class BroadcastSMS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent playMusicActivityIntent = new Intent(context, PlayMusicActivity.class);
        playMusicActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(playMusicActivityIntent);
    }
}
