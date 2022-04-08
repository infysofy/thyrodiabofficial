package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoadPatientinfo extends AppCompatActivity {
    ImageView imageView;
    String img = "";
    Button Load;
    String filename = "";
    ProgressDialog pDialog;
    String nm,ph,rvw,tst,Iddd,add;
    TextView nm1,ph1,rvw1,tst1,place1;
    Button back,call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_patientinfo);
        //displayLoader();
        Bundle bundle = getIntent().getExtras();
        Iddd =bundle.getString("Id").trim();
        back=(Button)findViewById(R.id.btn_back);
        nm1=(TextView)findViewById(R.id.tv_name);
        ph1=(TextView)findViewById(R.id.tv_ph);
        rvw1=(TextView)findViewById(R.id.tv_rvwdt);
        tst1=(TextView)findViewById(R.id.tv_tstdt);
        place1=(TextView)findViewById(R.id.tv_place);
        call=(Button)findViewById(R.id.btn_call);
       // Toast.makeText(getApplicationContext(),Iddd, Toast.LENGTH_SHORT).show();
        callvolly(Iddd);
        //pDialog.dismiss();

    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            //Intent in=new Intent(LoadPatientinfo.this,Apptreview.class);
            //startActivity(in);
        }
    });
    call.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91"+ph1.getText()));
            sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(sIntent);
        }
    });
    }



    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {


                String uRL = "http://api.thyrodiab.in/androidapp/prescriptions/" + filename;
                //Toast.makeText(getApplicationContext(),uRL, Toast.LENGTH_SHORT).show();
                URL url = new URL(uRL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;


            } catch (Exception e) {
                pDialog.dismiss();
                //Log.d(TAG,e.getMessage());
             //   pDialog.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imageView = (ImageView) findViewById(R.id.imgpresvw);
            imageView.setImageBitmap(result);
            pDialog.dismiss();
        }
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(LoadPatientinfo.this);
        pDialog.setMessage("Loading.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public void callvolly(final String id) {
     displayLoader();
        RequestQueue myreqque = Volley.newRequestQueue(LoadPatientinfo.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=ticket_listone";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jarr = new JSONArray(response);
                    JSONObject js = null;

                        for (int i = 0; i < jarr.length(); i++) {
                            js = jarr.getJSONObject(i);

                            nm = js.getString("nm");
                            ph = js.getString("ph");
                            rvw = js.getString("rvwdt");
                            tst = js.getString("tstdt");
                            add = js.getString("add");
                            filename = js.getString("filenm");
                            if(filename!="") {
                                SendHttpRequestTask si = new SendHttpRequestTask();
                                si.execute();
                                nm1.setText(nm);
                                ph1.setText(ph);
                                rvw1.setText(rvw);
                                place1.setText(add);
                                tst1.setText(tst);
                            }
                            else
                            {
                                nm1.setText(nm);
                                ph1.setText(ph);
                                rvw1.setText(rvw);
                                tst1.setText(tst);
                                place1.setText(add);
                                pDialog.dismiss();
                            }
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("id", id);
                return myData;
            }
        };
        myreqque.add(mystringrequest);


    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
