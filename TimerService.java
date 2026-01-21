package com.example.timerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class TimerService extends Service {

    private static final String CHANNEL_ID = "TIMER_SERVICE_CHANNEL";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int timerPackIndex = intent.getIntExtra("timer_pack_index", -1);
        
        if (timerPackIndex != -1) {
            TimerPack timerPack = TimerStorage.loadTimerPacks(this).get(timerPackIndex);
            
            // 创建通知
            Notification notification = createNotification(timerPack);
            
            // 启动前台服务
            startForeground(NOTIFICATION_ID, notification);
        }
        
        // 如果服务被杀死，系统会尝试重新创建服务
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "计时器服务",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("保持计时器在后台运行");
            
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification(TimerPack timerPack) {
        Intent notificationIntent = new Intent(this, TimerDetailActivity.class);
        notificationIntent.putExtra("timer_pack_index", getTimerPackIndex(timerPack.getId()));
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        
        SubTimer currentSubTimer = null;
        if (!timerPack.getSubTimers().isEmpty() && timerPack.getCurrentSubTimerIndex() < timerPack.getSubTimers().size()) {
            currentSubTimer = timerPack.getSubTimers().get(timerPack.getCurrentSubTimerIndex());
        }
        
        String contentText = currentSubTimer != null ? 
                currentSubTimer.getName() + " - " + formatTime(currentSubTimer.getRemainingTime()) : 
                "计时器运行中";
        
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(timerPack.getName())
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_timer)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }

    private int getTimerPackIndex(String id) {
        for (int i = 0; i < TimerStorage.loadTimerPacks(this).size(); i++) {
            if (TimerStorage.loadTimerPacks(this).get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}