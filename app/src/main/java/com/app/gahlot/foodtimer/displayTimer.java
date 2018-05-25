package com.app.gahlot.foodtimer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import Utils.BackGroundTask;

/**
 * Created by gahlot on 12/05/18.
 */

public class displayTimer extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_view);
        listView = (ListView) findViewById(R.id.customtimer_listview);
        BackGroundTask backGroundTask = new BackGroundTask(this);
        backGroundTask.execute("Get_info");
    }
}
