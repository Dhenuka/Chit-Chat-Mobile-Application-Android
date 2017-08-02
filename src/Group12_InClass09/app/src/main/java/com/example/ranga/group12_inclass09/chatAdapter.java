package com.example.ranga.group12_inclass09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranga on 3/27/2017.
 */

public class chatAdapter extends ArrayAdapter<String> {

    Context mcontext;
    int mResouce;
    ArrayList<String> ds=new ArrayList<String>();

    public chatAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.mResouce=resource;
        this.ds=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResouce,parent,false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.channel_sub);
        tv.setText(ds.get(position));

        return convertView;
    }
}
