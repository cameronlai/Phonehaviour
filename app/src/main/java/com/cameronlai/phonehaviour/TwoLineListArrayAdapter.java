package com.cameronlai.phonehaviour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameron on 13/01/15.
 */
public class TwoLineListArrayAdapter extends ArrayAdapter<BasicNameValuePair> {
    private final Context context;
    private List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

    public TwoLineListArrayAdapter(Context context, List<BasicNameValuePair> inputList) {
        super(context, android.R.layout.simple_list_item_2, inputList);
        this.context = context;
        this.list = inputList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Find all UI elements
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRowView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        TextView mTextViewText1 = (TextView) mRowView.findViewById(android.R.id.text1);
        TextView mTextViewText2 = (TextView) mRowView.findViewById(android.R.id.text2);

        BasicNameValuePair mBasicNameValuePair = list.get(position);
        mTextViewText1.setText(mBasicNameValuePair.getName());
        mTextViewText2.setText(mBasicNameValuePair.getValue());

        return mRowView;
    }
}

