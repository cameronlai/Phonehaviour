package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
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
                updateUsageStatistics(mString);
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

        String mString = (String) mSpinner.getSelectedItem();
        updateUsageStatistics(mString);
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

    private void updateUsageStatistics(String timeSpanString)
    {
        final UsageStatsManager mUsageStatManager =
                (UsageStatsManager) getApplicationContext().getSystemService("usagestats");
        long startTime = 0;
        long endTime = System.currentTimeMillis();

        // Get string
        final List<UsageStats> stats;
        if (timeSpanString == getString(R.string.day)) {
            stats = mUsageStatManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY, startTime,  endTime);
        } else if (timeSpanString == getString(R.string.week)) {
             stats = mUsageStatManager.queryUsageStats(
                     UsageStatsManager.INTERVAL_WEEKLY, startTime,  endTime);
        } else if (timeSpanString == getString(R.string.month)) {
            stats = mUsageStatManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_MONTHLY, startTime,  endTime);
        }
        else
        {
            return;
        }

        // Update ListView
        mListView = (ListView) findViewById(R.id.main_activity_all_package_name);
        ListEntryArrayAdapter adapter = new ListEntryArrayAdapter(this, stats, findViewById(R.id.main_activity_total_usage_textview));
        mListView.setAdapter(adapter);
    }
}
