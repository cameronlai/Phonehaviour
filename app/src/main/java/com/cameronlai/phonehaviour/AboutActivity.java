package com.cameronlai.phonehaviour;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Context mContext = getApplicationContext();
        ListView mListView = (ListView) findViewById(R.id.about_list);

        final List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        BasicNameValuePair item;

        item = new BasicNameValuePair(mContext.getString(R.string.version_key),
                mContext.getString(R.string.version_value));
        list.add(item);

        item = new BasicNameValuePair(mContext.getString(R.string.project_website_key),
                mContext.getString(R.string.project_website_value));
        list.add(item);

        item = new BasicNameValuePair(mContext.getString(R.string.author_key),
                mContext.getString(R.string.author_value));
        list.add(item);

        item = new BasicNameValuePair(mContext.getString(R.string.author_website_key),
                mContext.getString(R.string.author_website_value));
        list.add(item);

        TwoLineListArrayAdapter sa = new TwoLineListArrayAdapter(this, list);

        mListView.setAdapter(sa);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BasicNameValuePair item = list.get(position);
                if (item.getValue().indexOf("http") != -1){
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(item.getValue()));
                    startActivity(webIntent);
                }

            }
        });
    }
}
