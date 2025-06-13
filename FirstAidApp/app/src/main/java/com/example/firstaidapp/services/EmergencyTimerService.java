package com.example.firstaidapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.firstaidapp.R;
import com.example.firstaidapp.activities.MainActivity;

public class EmergencyTimerService extends Service {

    private static final String CHANNEL_ID = "EmergencyTimerChannel";
    private static final int NOTIFICATION_ID = 1;

    private CountDownTimer timer;
    private NotificationManager notificationManager;
    private String timerTitle;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int duration = intent.getIntExtra("TIMER_DURATION", 300); // Default 5 minutes
            timerTitle = intent.getStringExtra("TIMER_TITLE");
            if (timerTitle == null) {
                timerTitle = getString(R.string.emergency_timer);
            }

            startForeground(NOTIFICATION_ID, createNotification(formatTime(duration)));
            startTimer(duration);
        }

        return START_STICKY;
    }

    private void startTimer(int durationInSeconds) {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(durationInSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                updateNotification(formatTime(secondsRemaining));
            }

            @Override
            public void onFinish() {
                showTimerFinishedNotification();
                stopSelf();
            }
        };

        timer.start();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.emergency_timer_channel),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(getString(R.string.emergency_timer_channel_description));
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification(String timeRemaining) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(timerTitle)
                .setContentText(getString(R.string.time_remaining) + " " + timeRemaining)
                .setSmallIcon(R.drawable.ic_timer)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }

    private void updateNotification(String timeRemaining) {
        Notification notification = createNotification(timeRemaining);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void showTimerFinishedNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.timer_finished_title))
                .setContentText(getString(R.string.timer_finished_text))
                .setSmallIcon(R.drawable.ic_timer)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID + 1, notification);
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}