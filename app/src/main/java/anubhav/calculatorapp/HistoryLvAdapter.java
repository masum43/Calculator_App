package anubhav.calculatorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryLvAdapter extends ArrayAdapter<String> {

    private int resourceLayout;
    private Context mContext;

    public HistoryLvAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
            TextView tv = v.findViewById(R.id.textView2);

            tv.setText(getItem(position));



        return v;
    }

}
