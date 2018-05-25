package Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.gahlot.foodtimer.AddedTimer;
import com.app.gahlot.foodtimer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by gahlot on 11/05/18.
 */

public class CustomTimerAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public CustomTimerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(@Nullable Timers object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CustomtimeHolder customtimeHolder;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row,parent,false);
            customtimeHolder = new CustomtimeHolder();
            customtimeHolder.tx_name = (TextView) row.findViewById(R.id.name_entered);
            customtimeHolder.tx_time = (TextView) row.findViewById(R.id.time_entered);
            row.setTag(customtimeHolder);
        }

        else {
            customtimeHolder = (CustomtimeHolder)row.getTag();
        }

        Timers timers = (Timers)getItem(position);
        customtimeHolder.tx_name.setText(timers.getListname().toString());
        customtimeHolder.tx_time.setText(timers.getListtime().toString());
        return row;
    }

    static class CustomtimeHolder
    {
        TextView tx_name,tx_time;
    }
}
