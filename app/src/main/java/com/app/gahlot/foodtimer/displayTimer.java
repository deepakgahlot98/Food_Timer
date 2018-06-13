package com.app.gahlot.foodtimer;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import Utils.BackGroundTask;
import Utils.CustomTimerAdapter;
import Utils.Timers;

/**
 * Created by gahlot on 12/05/18.
 */

public class displayTimer extends AppCompatActivity {

    ListView listView;
    CustomTimerAdapter customTimerAdapter;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_view);
        listView = (ListView) findViewById(R.id.customtimer_listview);
        customTimerAdapter = new CustomTimerAdapter(this, R.layout.row);
        BackGroundTask backGroundTask = new BackGroundTask(this);
        backGroundTask.execute("Get_info");
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.time_entered);
                String timeRetrevied = textView.getText().toString();
                Intent intentExtras = new Intent();
                intentExtras.putExtra("TIME_DATA",timeRetrevied);
                setResult(RESULT_OK, intentExtras);
                finish();

            }
        });

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menucontext, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.delete_id:
                String itemTobeDeleted = Integer.toString(info.position);
                BackGroundTask backGroundTask = new BackGroundTask(this);
                backGroundTask.execute("Delete_info", itemTobeDeleted);
                listView.setAdapter(customTimerAdapter);
        }
        return super.onContextItemSelected(item);
    }


}
