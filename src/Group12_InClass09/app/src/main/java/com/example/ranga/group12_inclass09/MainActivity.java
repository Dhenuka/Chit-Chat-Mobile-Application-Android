package com.example.ranga.group12_inclass09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    EditText f1,f2;
    String s1,s2,s3,s4,s5,s6;
    ArrayList<String> ch;
    Button b,b1;
    String name=null;

    public static final String My_shared_pref = "My_Shared_Pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.firstVal);
        e2 = (EditText) findViewById(R.id.last_val);
        e3 = (EditText) findViewById(R.id.email_sign_val);
        e4 = (EditText) findViewById(R.id.pas_val_sign);
        f1=(EditText) findViewById(R.id.email_val);
        f2=(EditText) findViewById(R.id.password_val);
        b = (Button) findViewById(R.id.login);
        b1=(Button) findViewById(R.id.signup);



        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        final OkHttpClient client = new OkHttpClient();


            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    s5=f1.getText().toString();
                    s6=f2.getText().toString();
                    name=preferences.getString(s5,"");
                    Log.d("demo",preferences.getAll()+"");



                    Request request = new Request.Builder()
                            .header("Authorization","BEARER "+preferences.getString(s5,""))
                            .url("http://52.90.79.130:8080/Groups/api/get/subscriptions")
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


                        }
                    });



                   /* if(!name.equalsIgnoreCase(""))
                    {
                        Intent i = new Intent(MainActivity.this, ChatScreens.class);
                        //i.putExtra(key, g);
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this,"No user with this email",Toast.LENGTH_LONG).show();
                    }*/
                }
            });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=e1.getText().toString();
                //Log.d("demo",s1+"");
                s2=e2.getText().toString();
                //Log.d("demo",s2+"");
                s3=e3.getText().toString();
                s4=e4.getText().toString();


                RequestBody formBody = new FormBody.Builder()
                        .add("email", "" + s1)
                        .add("password", "" + s2)
                        .add("fname", ""+s3)
                        .add("lname",""+s4)
                /*.add("email", "drangam4@uncc")
                .add("password", "123456")
                .add("fname", "Bob")
                .add("lname","New")*/
                        .build();

                Request request = new Request.Builder()
                        .post(formBody)
                        .url("http://52.90.79.130:8080/Groups/api/signUp")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

//                Headers responseHeaders = response.headers();
//                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
                        String json=null;
                       // Log.d("demo",response.body().string());
                        json=response.body().string();
                JSONObject root = null;
                try {
                    root = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String data=null;
                try {
                    data = root.getString("data");
                    if(data.equals(" ")){
                        Toast.makeText(MainActivity.this,"Error while signing in",Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println(data+"");
                        editor.putString(s3,data);
                        editor.commit();
                        //Toast.makeText(MainActivity.this, "Sign-in successfull", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    }
                });
            }
        });



        }
    }
