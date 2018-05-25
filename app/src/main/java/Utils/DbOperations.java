package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.gahlot.foodtimer.AddedTimer;

/**
 * Created by gahlot on 11/05/18.
 */

public class DbOperations extends SQLiteOpenHelper {

    private static final int DB_VERSION =1;
    private static final String DB_NAME = "customtimer_info.db";
    private static final String CREATE_QUERY = "create table " + AddedTimer.TimerEntry.DtableName
                                            + "(" + AddedTimer.TimerEntry.DName
                                            + " text," + AddedTimer.TimerEntry.DTime + " text);";

    DbOperations(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
        Log.d("Database Operations","Database Created");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations","Table Created");
    }

    public void addinformations(SQLiteDatabase DB, String n, String t)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AddedTimer.TimerEntry.DName,n);
        contentValues.put(AddedTimer.TimerEntry.DTime,t);
        DB.insert(AddedTimer.TimerEntry.DtableName,null,contentValues);
        Log.d("Database Operations","One row inserted");
    }

    public Cursor getInformations(SQLiteDatabase db)
    {
        String[] projections = {AddedTimer.TimerEntry.DName, AddedTimer.TimerEntry.DTime};
        Cursor cursor = db.query(AddedTimer.TimerEntry.DtableName,projections,null,null,
                null,null,null);


        return cursor;
    }

    public void deleteInformation(SQLiteDatabase DB, String n)
    {
        String selection = AddedTimer.TimerEntry.DName + " = " + n;
        DB.delete(AddedTimer.TimerEntry.DtableName,selection,null);
    }

    public Cursor getItemID(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + AddedTimer.TimerEntry.DName + " FROM " + AddedTimer.TimerEntry.DtableName + " WHERE " + AddedTimer.TimerEntry.DName
                + " = " + name;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
