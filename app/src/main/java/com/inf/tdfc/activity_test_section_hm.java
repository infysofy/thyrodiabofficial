package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class activity_test_section_hm extends AppCompatActivity {
    private final String JSON_URL = "http://api.thyrodiab.in/androidapp/services.php?action=searchtestscat";
            //searchtests"
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    Button tq, rl, tb, pd;
    MydbAdapter_tl db;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_section_hm);
        tq = (Button) findViewById(R.id.btn_tq);
        rl = (Button) findViewById(R.id.btn_rl);
        tb = (Button) findViewById(R.id.btn_tb);
        pd = (Button) findViewById(R.id.btn_pd);
        db=new MydbAdapter_tl(this);
        try {
           jsonrequest();
           // db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
            //db.insertTestDetails("1", "CODE", "DESCRIPTION", "0", "0", "LAB");
        }
        catch (SQLiteException ex)
        {

        }
        tq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent in = new Intent(activity_test_section_hm.this, testquote.class);
                //startActivity(in);
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(activity_test_section_hm.this, Testreview.class);
                startActivity(in);
            }
        });

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity_test_section_hm.this, TestEntry_b2b.class);
                startActivity(in);
            }
        });
        pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity_test_section_hm.this, PatientData.class);
                startActivity(in);
            }
        });
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }
    private void jsonrequest() {
        displayLoader();

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;
                for (int i = 0 ; i < response.length(); i++ ) {
                    try {
                        jsonObject = response.getJSONObject(i) ;
                        String id=jsonObject.getString("id");
                        String tc=jsonObject.getString("TestCode");
                        String des=jsonObject.getString("Description");
                        String mrp=jsonObject.getString("MRP");
                        String rt=jsonObject.getString("Rate");
                        String lb=jsonObject.getString("Lab");


                        db.insertTestDetails(id,tc,des,mrp,rt,lb);
                        //mylist.add(Name+"-"+"#"+Id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error1",Toast.LENGTH_LONG).show();
                    }
                }

                //lv.setAdapter(Ad);
                pDialog.dismiss();
                // setuprecyclerview(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("lab","TTC");
                return myData;
            } }

        );
        requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request) ;


    }
    private void displayLoader (){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Data Syncing..... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

}
