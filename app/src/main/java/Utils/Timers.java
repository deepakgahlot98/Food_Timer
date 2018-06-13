package Utils;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gahlot on 08/05/18.
 */

public class Timers {

    private String Listname, listtime;

    public Timers(String name, String time)
    {
        this.setListname(name);
        this.setListtime(time);
    }

    public Timers() {

    }

    public String getListname() {
        return Listname;
    }

    public void setListname(String listname) {
        Listname = listname;
    }

    public String getListtime() {
        return listtime;
    }

    public void setListtime(String listtime) {
        this.listtime = listtime;
    }
}
