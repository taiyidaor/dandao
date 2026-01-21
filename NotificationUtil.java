package com.example.timerapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class NotificationUtil {

    public static void playNotificationSound(Context context) {
        try {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.notification_sound);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(500); // 振动500毫秒
        }
    }
}