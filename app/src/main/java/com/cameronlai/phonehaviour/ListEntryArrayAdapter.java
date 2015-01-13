package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by cameron on 13/01/15.
 */
public class ListEntryArrayAdapter extends ArrayAdapter<UsageStats> {
    private final Context context;
    private final List<UsageStats> values = new ArrayList<UsageStats>();
    private PackageManager mPackageManager;
    private long mTotalUsageTime;


    public ListEntryArrayAdapter(Context context, List<UsageStats> values) {
        super(context, R.layout.list_entry, values);
        this.context = context;
        this.mPackageManager = this.context.getPackageManager();
        mTotalUsageTime = 0;

        List<MyUsageStats> mMyUsageStats = new ArrayList<MyUsageStats>();
        for(UsageStats s : values){
            this.mTotalUsageTime += s.getTotalTimeInForeground();
            mMyUsageStats.add(new MyUsageStats(s));
        }

        // Sort and add back to array
        Collections.sort(mMyUsageStats);
        for(MyUsageStats s : mMyUsageStats){
            this.values.add(s.stat);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Find all UI elements
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRowView = inflater.inflate(R.layout.list_entry, parent, false);
        TextView mTextViewPackageName = (TextView) mRowView.findViewById(R.id.packageTitle);
        TextView mTextViewPackageUsage = (TextView) mRowView.findViewById(R.id.packageUsage);
        ImageView mImageView = (ImageView) mRowView.findViewById(R.id.packageIcon);
        ProgressBar mProgressBar = (ProgressBar) mRowView.findViewById(R.id.packageProgressBar);

        // Declare all information handlers
        UsageStats mUsageStats = values.get(position);
        String mPackageName = mUsageStats.getPackageName();
        long mPackageUsageTime = mUsageStats.getTotalTimeInForeground();
        int mPercentageUsage = (int) ( (double)mPackageUsageTime / this.mTotalUsageTime * 100);
        String mPackageUsageTimeString = String.format(
                "%02dh%02dm%02ds",
                TimeUnit.MILLISECONDS.toHours(mPackageUsageTime),
                TimeUnit.MILLISECONDS.toMinutes(mPackageUsageTime) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mPackageUsageTime) % TimeUnit.MINUTES.toSeconds(1)
        );
        ApplicationInfo mApplicationInfo;
        Drawable mPackageIcon;

        // Set application icon
        try {
            mPackageIcon = this.mPackageManager.getApplicationIcon(mPackageName);
            mImageView.setImageDrawable(mPackageIcon);
        }catch (PackageManager.NameNotFoundException e){
            // Catch block
        }

        // Set application name
        try {
            mApplicationInfo = this.mPackageManager.getApplicationInfo(mPackageName, PackageManager.GET_META_DATA);
            mTextViewPackageName.setText(this.mPackageManager.getApplicationLabel(mApplicationInfo));
        }catch (PackageManager.NameNotFoundException e){
            // Catch block
        }

        // Set total run time
        mTextViewPackageUsage.setText(mPackageUsageTimeString);

        // Set progress bar percentage
        mProgressBar.setProgress(mPercentageUsage);

        return mRowView;
    }
}

