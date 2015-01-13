package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;

/**
 * Created by cameron on 14/01/15.
 */
public class MyUsageStats implements Comparable<MyUsageStats> {
    UsageStats stat;

    public MyUsageStats(UsageStats stat) {
        this.stat = stat;
    }

    public int compareTo(MyUsageStats s) {
        // For descending order sort
        // After sort, element 0 will be the largest element
        return (int) (s.stat.getTotalTimeInForeground() - this.stat.getTotalTimeInForeground());
    }
}