package com.cameronlai.phonehaviour;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class AboutActivity extends ActionBarActivity {
    private  ListView listView ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        context = getApplicationContext();
        List<String> values = new ArrayList<String>();

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.about_list);

        // Get Data
        String mSoftwareVersion = "Software Version: ";
        try {
            mSoftwareVersion = mSoftwareVersion.concat(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
            values.add(mSoftwareVersion);
        }
        catch (PackageManager.NameNotFoundException e) {
            // Do Nothing
        }
        values.add("Author: Cameron Lai");
        values.add("Website: http://www.cameronlai.com/");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
