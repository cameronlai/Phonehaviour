package com.cameronlai.phonehaviour;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private XYPlot plot;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.time_choice_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_choice_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        // Confirm usage access
        // String tmpSettingStr = Settings.System.getString( getContentResolver(), Settings.ACTION_USAGE_ACCESS_SETTINGS  );
        // If not, prompt the user to set the usage access settings
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateUsageStatistics();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    public void updateUsageStatistics()
    {
        // Get usage statistics from Android OS
        final UsageStatsManager mUsageStatManager = (UsageStatsManager) getApplicationContext().getSystemService("usagestats");
        final List<UsageStats> stats = mUsageStatManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0,  System.currentTimeMillis());

        List<Number> series1Numbers = new ArrayList<Number>();
        for (int i=0; i<stats.size(); i++) {
            series1Numbers.add(stats.get(i).getTotalTimeInForeground());
        }

        mListView = (ListView) findViewById(R.id.main_activity_all_package_name);
        ListEntryArrayAdapter adapter = new ListEntryArrayAdapter(this, stats);
        mListView.setAdapter(adapter);

        // More information about Android Plot
        // e.g. Dynamic data
        // http://androidplot.com/docs/quickstart/

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Create a couple arrays of y-values to plot:


        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                series1Numbers,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series


        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.setTicksPerDomainLabel(1);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
    }
}
