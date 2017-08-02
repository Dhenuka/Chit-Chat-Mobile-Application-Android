package com.example.ranga.group12_inclass09;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ranga on 3/27/2017.
 */

public class AddAdapter extends ArrayAdapter<Channel> {

    String a;
    Context mcontext;
    int mResouce;
    ArrayList<Channel> ds=new ArrayList<Channel>();

    public AddAdapter(Context context, int resource, ArrayList<Channel> objects,String aut) {
        super(context, resource, objects);
        this.mcontext=context;
        this.mResouce=resource;
        this.ds=objects;
        this.a=aut;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResouce,parent,false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.channel_sub_1);
        Channel ch = ds.get(position);
        tv.setText(ch.getName());
        final OkHttpClient client = new OkHttpClient();

        Button b=(Button) convertView.findViewById(R.id.view_join);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Request request = new Request.Builder()
                        .header("Authorization","BEARER "+a)
                        .header("Content-Type","application/x-www-form-urlencoded")
                        .url("http://localhost:8080/controller/api/subscribe/channel")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                        // Log.d("demo","response is "+response.body().string());
                        String json=null;
                        // Log.d("demo",response.body().string());
                        json=response.body().string();
                        JSONObject root = null;
                        try {
                            root = new JSONObject(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*JSONArray data=null;
                        ch=new ArrayList<String>();
                        try {
                            data = root.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                ch.add(data.getString(i));
                            }
                            Log.d("demo",ch.size()+"");
                            Intent i = new Intent(MainActivity.this, ChatScreens.class);
                            i.putExtra("array",ch);
                            i.putExtra("auth",preferences.getString(s5,""));
                            //i.putExtra(key, g);
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(data+"");
*/



                    }
                });


            }
        });

        return convertView;
    }
}
