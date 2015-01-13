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

import java.util.List;

/**
 * Created by cameron on 13/01/15.
 */
public class ListEntryArrayAdapter extends ArrayAdapter<UsageStats> {
    private final Context context;
    private final List<UsageStats> values;
    private PackageManager mPackageManager;
    private long mTotalUsageTime;

    public ListEntryArrayAdapter(Context context, List<UsageStats> values) {
        super(context, R.layout.list_entry, values);
        this.context = context;
        this.values = values;
        this.mPackageManager = this.context.getPackageManager();
        mTotalUsageTime = 0;
        for(UsageStats s : values){
            this.mTotalUsageTime += s.getTotalTimeInForeground();
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
        mTextViewPackageUsage.setText(Long.toString(mPackageUsageTime));

        // Set progress bar percentage
        mProgressBar.setProgress(mPercentageUsage);

        return mRowView;
    }
}

