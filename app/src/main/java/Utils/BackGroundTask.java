package Utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.gahlot.foodtimer.AddedTimer;
import com.app.gahlot.foodtimer.R;
import com.bumptech.glide.request.target.SquaringDrawable;

import java.util.List;

/**
 * Created by gahlot on 11/05/18.
 */

public class BackGroundTask extends AsyncTask<String,Timers,String> {

    Context ctx;
    CustomTimerAdapter customTimerAdapter;
    Activity activity;
    ListView listView;

    public BackGroundTask(Context ctx)
    {
        this.ctx = ctx;
        activity = (Activity)ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        DbOperations dbOperations = new DbOperations(ctx);

        if (method.equals("Add_info"))
        {
            String Name = params[1];
            String Time = params[2];
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addinformations(db,Name,Time);
            return "One Row inserted";
        }

        else if (method.equals("Get_info"))
        {
            listView = (ListView) activity.findViewById(R.id.customtimer_listview);
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInformations(db);
            customTimerAdapter = new CustomTimerAdapter(ctx, R.layout.row);
            String Rname,Rtime;

            while (cursor.moveToNext())
            {
                Rname = cursor.getString(cursor.getColumnIndex(AddedTimer.TimerEntry.DName));
                Rtime = cursor.getString(cursor.getColumnIndex(AddedTimer.TimerEntry.DTime));

                Timers timers = new Timers(Rname,Rtime);
                publishProgress(timers);
            }
            return "Get_info";
        }
        else if (method.equals("Delete_info"))
        {
            String id = params[1];
            int correctid = Integer.parseInt(id);
            correctid=correctid+1;
            String databaseID = Integer.toString(correctid);
            //System.out.println(correctid);
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInformation(db,databaseID);
            customTimerAdapter = new CustomTimerAdapter(ctx, R.layout.row);
            return "Delete_info";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Timers... values) {
        customTimerAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {

        if (result.equals("Get_info"))
        {
            listView.setAdapter(customTimerAdapter);
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String name = adapterView.getItemAtPosition(position).toString();
                        System.out.println(name.toString());

                }
            }); */

        } else if (result.equals("Delete_info")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
    }
}
