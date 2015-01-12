package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cameron on 13/01/15.
 */
public class ListEntryArrayAdapter extends ArrayAdapter<UsageStats> {
    private final Context context;
    private final List<UsageStats> values;

    public ListEntryArrayAdapter(Context context, List<UsageStats> values) {
        super(context, R.layout.list_entry, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_entry, parent, false);

        TextView textViewPackageName = (TextView) rowView.findViewById(R.id.firstLine);
        textViewPackageName.setText(values.get(position).getPackageName());

        TextView textViewPackageTime = (TextView) rowView.findViewById(R.id.secondLine);
        textViewPackageTime.setText(Long.toString(values.get(position).getTotalTimeInForeground()));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        // change the icon for Windows and iPhone
        String s = values.get(position).getPackageName();
        if (s.startsWith("iPhone")) {
            //imageView.setImageResource(R.drawable.no);
        } else {
            //imageView.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
}

