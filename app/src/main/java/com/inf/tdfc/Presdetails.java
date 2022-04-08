package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;

public class Presdetails extends AppCompatActivity {
    private final String JSON_URL = "http://api.thyrodiab.in/androidapp/services.php?action=ticket_list" ;
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    Button back;
    ArrayList<String> mylist;
    ListView lv;
    ArrayAdapter<String> Ad;
    ProgressDialog pDialog;
    int j=0;
    EditText ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presdetails);
        lv=(ListView)findViewById(R.id.ListView);
        ts=(EditText)findViewById(R.id.searchfilter);
        back=(Button)findViewById(R.id.btn_prev);
        mylist= new ArrayList<String>();
        jsonrequest();
        Ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,mylist);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(Presdetails.this,Dashboard.class);
                startActivity(in);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //displayLoader();
                String selectedmovie=mylist.get(position);
                String[] arr = selectedmovie.split("-");
                Intent in= new Intent(Presdetails.this,Viewpresdetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id",arr[1].replace('#',' ').trim());
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        ts.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            (Presdetails.this).Ad.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

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
                        String Name=jsonObject.getString("nm");
                        String Id=jsonObject.getString("id");
                        mylist.add(Name+"-"+"#"+Id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error1",Toast.LENGTH_LONG).show();
                    }
                }

                lv.setAdapter(Ad);
                pDialog.dismiss();
                // setuprecyclerview(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });



        requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request) ;


    }
    private void displayLoader (){
        pDialog = new ProgressDialog(Presdetails.this);
        pDialog.setMessage("Loading.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
