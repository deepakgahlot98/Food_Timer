package com.app.gahlot.foodtimer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class FoodTimer extends Application {

    public static final String CHANNEL_1_ID = "Timer_running";
    public static final String CHANNEL_2_ID = "Timer_Stopped";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,"Timer is Running"
                    , NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("Timer is running");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,"Timer is Stopped"
                    , NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("Timer is Stopped");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
