package com.inf.tdfc;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button id;
    int SPLASH_TIME=1000;
    String Resule=null;
    MydbAdapter_tkt db=new MydbAdapter_tkt(MainActivity.this);
    //ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAuth();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(MainActivity.this,UserLogin.class);
                startActivity(in);
                finish();
            }
        }, SPLASH_TIME);*/
        //  callvolly();
    }
    public  void checkAuth()
    {
        boolean r=db.checkUser();
        if(r==false)
        {
            Intent in = new Intent(MainActivity.this, UserLogin.class);
            startActivity(in);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(in);
                    finish();
                }
            }, SPLASH_TIME);
        }
    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
    public String callvolly(){

        RequestQueue myreqque = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=checkpdatetdofc";
        //String result="NO";
        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jarr = new JSONArray(response);
                    JSONObject js=null;
                    for (int i = 0; i < jarr.length(); i++) {
                        js = jarr.getJSONObject(i);
                       int oid=js.getInt("apupdate");

                       if(oid==0)
                       {
                           Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                                   Uri.parse("http://www.thyrodiab.in/tdofficeap/app-release.apk"));
                           startActivity(updateIntent);
                           finish();
                       }
                       else
                       {
                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   Intent in = new Intent(MainActivity.this, Dashboard.class);
                                   startActivity(in);
                                   finish();
                               }
                           }, SPLASH_TIME);
                       }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(in);
                            finish();
                        }
                    }, SPLASH_TIME);
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent in = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(in);
                        finish();
                    }
                }, SPLASH_TIME);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("id", "1");
                return myData;
            }
        };
        myreqque.add(mystringrequest);
            return  Resule;
    }
}
