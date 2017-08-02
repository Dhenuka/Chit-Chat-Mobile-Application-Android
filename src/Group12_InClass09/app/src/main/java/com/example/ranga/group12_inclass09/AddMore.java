package com.example.ranga.group12_inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class AddMore extends AppCompatActivity {
    ListView lv;
    String at;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);
        String y=getIntent().getExtras().getString("aut");

        ArrayList<Channel> x = (ArrayList<Channel>) getIntent().getExtras().getSerializable("arraylist");

        AddAdapter adapter = new AddAdapter(AddMore.this,R.layout.channels1,x,y);
        adapter.setNotifyOnChange(true);

        lv=(ListView) findViewById(R.id.list_add);
        lv.setAdapter(adapter);

        final OkHttpClient client = new OkHttpClient();

        b=(Button) findViewById(R.id.done);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddMore.this,ChatScreens.class);
                startActivity(i);
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
