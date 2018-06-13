package com.app.gahlot.foodtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import Utils.BackGroundTask;

/**
 * Created by gahlot on 06/05/18.
 */

public class saveTimer extends AppCompatActivity {

    EditText e_name, e_time;
    String name, time;
    ImageButton savebutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomtimer);
        e_name = (EditText) findViewById(R.id.time_name);
        e_time = (EditText) findViewById(R.id.time);
    }

    public void saveData(View view)
    {
        name = e_name.getText().toString();
        time = e_time.getText().toString();
        final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.4F);
        savebutton = (ImageButton) findViewById(R.id.save_timer);

        BackGroundTask backGroundTask = new BackGroundTask(this);
        backGroundTask.execute("Add_info",name,time);
        finish();
    }

}

