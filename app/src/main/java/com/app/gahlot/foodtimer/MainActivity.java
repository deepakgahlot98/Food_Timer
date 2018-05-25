package com.app.gahlot.foodtimer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

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

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft - minutes * 60;

        String timeremaining = String.format("%02d:%02d",minutes,seconds);

        timertext.setText(timeremaining);
    }

    public void controlTimer(View view) {
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

                }

                @Override
                public void onFinish() {
                    seekbar.setProgress(0);
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.smokealarm);
                    mplayer.start();
                    seekbar.setEnabled(true);
                    controllerButton.setText("Go");
                    Glide.with(getApplicationContext()).onStop();

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
       startActivity(new Intent(this,displayTimer.class));
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


        seekbar.setMax(600);
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

        MobileAds.initialize(this,"ca-app-pub-6234849788191173~9884819105");
        //

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
