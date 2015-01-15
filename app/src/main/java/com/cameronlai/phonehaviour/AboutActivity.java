package com.cameronlai.phonehaviour;

import android.app.ListActivity;
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
import android.widget.SimpleAdapter;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AboutActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Context mContext = getApplicationContext();
        ListView mListView = (ListView) findViewById(R.id.about_list);

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair item;

        item = new BasicNameValuePair("Version",  mContext.getString(R.string.version));
        list.add(item);

        item = new BasicNameValuePair("Author",  "Cameron Lai");
        list.add(item);

        item = new BasicNameValuePair("Website",  "http://www.cameronnlai.com");
        list.add(item);

        TwoLineListArrayAdapter sa = new TwoLineListArrayAdapter(this, list);

        mListView.setAdapter(sa);
    }
}
