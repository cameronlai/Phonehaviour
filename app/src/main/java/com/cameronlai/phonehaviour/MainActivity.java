package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    ListView mListView;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (Spinner) findViewById(R.id.time_choice_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_choice_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String mString = (String) parentView.getItemAtPosition(position);
                if (mString == getString(R.string.day)) {
                    updateUsageStatistics(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis());
                } else if (mString == getString(R.string.week)) {
                    updateUsageStatistics(UsageStatsManager.INTERVAL_WEEKLY, 0, System.currentTimeMillis());
                } else if (mString == getString(R.string.month)) {
                    updateUsageStatistics(UsageStatsManager.INTERVAL_MONTHLY, 0, System.currentTimeMillis());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        // Confirm usage access
        // String tmpSettingStr = Settings.System.getString( getContentResolver(), Settings.ACTION_USAGE_ACCESS_SETTINGS  );
        // If not, prompt the user to set the usage access settings
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_about)
        {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateUsageStatistics(int timeSpan, long startTime, long endTime)
    {
        // Get usage statistics from Android OS
        final UsageStatsManager mUsageStatManager = (UsageStatsManager) getApplicationContext().getSystemService("usagestats");
        final List<UsageStats> stats = mUsageStatManager.queryUsageStats(timeSpan, startTime,  endTime);

        mListView = (ListView) findViewById(R.id.main_activity_all_package_name);
        ListEntryArrayAdapter adapter = new ListEntryArrayAdapter(this, stats, findViewById(R.id.main_activity_total_usage_textview));
        mListView.setAdapter(adapter);
    }
}
