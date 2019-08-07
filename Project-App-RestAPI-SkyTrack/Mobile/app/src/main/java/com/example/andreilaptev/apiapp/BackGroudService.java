package com.example.andreilaptev.apiapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.io.InputStream;
/*Developed by Manoel B. Burgos Filho*/

public class BackGroudService extends Service {
    MediaPlayer myPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myPlayer = MediaPlayer.create(this, R.raw.jingle);
        myPlayer.setLooping(true);
        myPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myPlayer.stop();
    }
}
