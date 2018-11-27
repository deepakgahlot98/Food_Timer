package com.app.gahlot.foodtimer;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import static com.app.gahlot.foodtimer.FoodTimer.CHANNEL_1_ID;
import static com.app.gahlot.foodtimer.FoodTimer.CHANNEL_2_ID;

public class MainActivity extends Activity {

    SeekBar seekbar;
    TextView timertext;
    ImageView animation;
    boolean counterIsActive = false;
    Button controllerButton;
    Button addCustomtimer;
    Button showCustomTimer;
    CountDownTimer countDownTimer;
    private AdView mAdView;
    public static String timeremaining;
    private NotificationManagerCompat notificationManagerCompat;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft - minutes * 60;

        timeremaining = String.format("%02d:%02d",minutes,seconds);
        timertext.setText(timeremaining);
    }

    public void controlTimer(final View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            seekbar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(seekbar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    int timeleft = (int) l / 1000;
                    updateTimer(timeleft);
                    seekbar.setProgress(timeleft);
                    animation = (ImageView) findViewById(R.id.imageView2);
                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(animation);
                    Glide.with(getApplicationContext()).load(R.raw.giphy).into(imageViewTarget);
                    sendChannel1(view);
                }

                @Override
                public void onFinish() {
                    seekbar.setProgress(0);
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.smokealarm);
                    mplayer.start();
                    seekbar.setEnabled(true);
                    controllerButton.setText("Go");
                    Glide.with(getApplicationContext()).onStop();
                    sendChannel2(view);

                }
            }.start();
        } else {
            controllerButton.setText("Go");
            timertext.setText("00:30");
            seekbar.setProgress(30);
            countDownTimer.cancel();
            Glide.with(getApplicationContext()).onStop();
            seekbar.setEnabled(true);
            counterIsActive = false;
        }
    }

    public void addTimer(View view) {
        startActivity(new Intent(this,saveTimer.class));
    }

    public void showTimer(View view) {

        Intent intent = new Intent(this,displayTimer.class);
        startActivityForResult(intent,SECOND_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = (SeekBar) findViewById(R.id.timerSeekBar);
        timertext = (TextView) findViewById(R.id.timeTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        showCustomTimer = (Button) findViewById(R.id.Saved_timer);
        addCustomtimer = (Button) findViewById(R.id.add_timer);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        seekbar.setMax(1200);
        seekbar.setProgress(30);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateTimer(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        MobileAds.initialize(this,"ca-app-pub-9932656312973519~2969150830");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                int time = Integer.parseInt(data.getExtras().getString("TIME_DATA"));

                seekbar.setProgress(time * 60);
                updateTimer(time * 60);
            }
            else{
                System.out.println("Not Ok");
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void sendChannel1(View v) {
        Intent activityIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,activityIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.timer_icon_notification)
                .setContentTitle(timeremaining)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setContentIntent(pendingIntent)
                .build();

        notificationManagerCompat.notify(1,notification);
    }

    public void sendChannel2(View v) {
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.timer_icon_notification)
                .setContentTitle(timeremaining)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .build();

        notificationManagerCompat.notify(1,notification);
    }

}
