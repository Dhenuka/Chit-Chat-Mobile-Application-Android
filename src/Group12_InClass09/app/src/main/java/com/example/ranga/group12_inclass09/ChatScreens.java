package com.example.ranga.group12_inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

public class ChatScreens extends AppCompatActivity {
    ListView listView2;
    Button ad;
    String at;
    ArrayList<Channel> ch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screens2);
        listView2 = (ListView) findViewById(R.id.list);
        ch=new ArrayList<Channel>();

      ArrayList<String> x =  getIntent().getExtras().getStringArrayList("array");
        /*ArrayList<String> x=new ArrayList<String>();
        x.add("kdnd");
        x.add("kjcbad");*/
        chatAdapter adapter = new chatAdapter(ChatScreens.this,R.layout.channels,x);
        adapter.setNotifyOnChange(true);

        listView2.setAdapter(adapter);

        final OkHttpClient client = new OkHttpClient();

        ad=(Button) findViewById(R.id.done);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                at=getIntent().getExtras().getString("auth");
                Request request = new Request.Builder()
                        .header("Authorization","BEARER "+at)
                        .url("http://52.90.79.130:8080/Groups/api/get/channels")
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
                        JSONArray data=null;
                        try {
                            data = root.getJSONArray("data");

                            for(int i=0;i<data.length();i++){
                                JSONObject ob= (JSONObject) data.get(i);
                                Channel c=new Channel();
                                c.setId(ob.getString("channel_id"));
                                c.setName(ob.getString("channel_name"));
                                ch.add(c);
                            }
                            Log.d("demo",ch.size()+"");
                            Intent i = new Intent(ChatScreens.this, AddMore.class);
                            i.putExtra("arraylist",ch);
                            i.putExtra("aut",at);
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(data+"");


                    }
                });
            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutC:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
