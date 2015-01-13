package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
    private PackageManager mPackageManager;

    public ListEntryArrayAdapter(Context context, List<UsageStats> values) {
        super(context, R.layout.list_entry, values);
        this.context = context;
        this.values = values;
        this.mPackageManager = this.context.getPackageManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Find all UI elements
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_entry, parent, false);
        TextView textViewPackageName = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textViewPackageTime = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.packageIcon);

        // Declare all information handlers
        UsageStats mUsageStats = values.get(position);
        String mPackageName = mUsageStats.getPackageName();
        ApplicationInfo mApplicationInfo;
        Drawable mPackageIcon;

        // Set application icon
        try {
            mPackageIcon = this.mPackageManager.getApplicationIcon(mPackageName);
            imageView.setImageDrawable(mPackageIcon);
        }catch (PackageManager.NameNotFoundException e){
            // Catch block
        }

        // Set application name
        try {
            mApplicationInfo = this.mPackageManager.getApplicationInfo(mPackageName, PackageManager.GET_META_DATA);
            textViewPackageName.setText(this.mPackageManager.getApplicationLabel(mApplicationInfo));
        }catch (PackageManager.NameNotFoundException e){
            // Catch block
        }

        // Set total run time
        textViewPackageTime.setText(Long.toString(mUsageStats.getTotalTimeInForeground()));

        return rowView;
    }
}

