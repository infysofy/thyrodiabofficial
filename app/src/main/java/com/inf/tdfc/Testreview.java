package com.inf.tdfc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testreview extends AppCompatActivity {
    String[] language ={"C-1-1","C++-1-1","Java-1-1",".NET-1-1","iPhone-1-1","Android-1-1","ASP.NET-1-1","PHP-1-1"};
    private final String JSON_URL = "http://api.thyrodiab.in/androidapp/services.php?action=searchtestscat" ;
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    Button back;
    ArrayList<String> mylist;
    ListView lv;
    ArrayAdapter<String> Ad;
    MydbAdapter_tl db;
    AutoCompleteTextView actv_tst;
    ArrayList<HashMap<String, String>> userList;
    Button del;
    ProgressDialog pDialog;
    List<String> tests;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testreview);
        db=new MydbAdapter_tl(Testreview.this);
        del=(Button)findViewById(R.id.btn_clrtest);
        lv = (ListView) findViewById(R.id.lv_test);
        actv_tst=(AutoCompleteTextView)findViewById(R.id.actv_testdet);
        userList=new  ArrayList<HashMap<String, String>> ();
        mylist= new ArrayList<String>();
        Ad=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,mylist);
        callvolly("TTC","0","10");//jsonrequest();
        actv_tst.setThreshold(0);
        actv_tst.setAdapter(Ad);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //
        //userList = new ArrayList<>();
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv_tst.setText("");
            }
        });
        actv_tst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                //TODO Do something with the selected text
                String[] arr = selection.split("-");
                //Double rate=Double.parseDouble(arr[2]);
               // Double Disc=50.00;
                //Double finalrate=rate-(rate*(Disc/100));
               // String result = String.format("%.0f", finalrate);
            if(checkVal(arr[1])==false) {
                HashMap<String, String> user = new HashMap<>();
                user.put("code", arr[0]);
                user.put("des", arr[1]);
                user.put("mrp", arr[2]);
                user.put("rate", "0");
                userList.add(user);
                loadselTest();
                actv_tst.setText("");
            }

            }
        });
        //loadgrid();
    }
    public boolean checkVal(String val)    {
        boolean result=false;

        for (int i=0;i<userList.size();i++)
        {
                HashMap<String, String> hashmap= userList.get(i);
                String string= hashmap.get("code");

                if(string.equals(val))
                {

                    result=true;
                    actv_tst.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Code:"+string+"  Arr:"+val, Toast.LENGTH_LONG).show();

                }
        }

        return  result;
    }
    public void loadselTest(){
    SimpleAdapter   adapter = new SimpleAdapter(Testreview.this, userList, R.layout.list_row, new String[]{"des", "code", "mrp","rate"}, new int[]{R.id.testname, R.id.code2, R.id.mrp2,R.id.rate2})
    {
        @Override
        public View getView (final int position, View convertView, ViewGroup parent)
        {

             View v = super.getView(position, convertView, parent);
            Button d=(Button)v.findViewById(R.id.btn_lv_del_test);
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
                    userList.remove(position);
                    loadselTest();
                }
            });

            return v;
        }
    };
    lv.setAdapter(adapter);
}
    public void loadgrid(){
        // userList2 = db.GetTestlistttc(srch,lab);
        // adapter = new SimpleAdapter(Testreview.this, userList2, R.layout.list_row, new String[]{"gid", "name", "mrp", "b2b"}, new int[]{R.id.code2, R.id.testname, R.id.mrp2, R.id.rate2});
        //lv.setAdapter(adapter);
        //db=new MydbAdapter_tl(Testreview.this);

        List<String> tktPat = db.getTestDet();
        callvolly("TTC","0","10");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line, mylist);
        actv_tst.setThreshold(0);
        actv_tst.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

    }
    private void displayLoader (){
        pDialog = new ProgressDialog(Testreview.this);
        pDialog.setMessage("Loading.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public void callvolly(final String Lab,final String ind1,final String ind2) {
        displayLoader();
        RequestQueue myreqque = Volley.newRequestQueue(Testreview.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=searchtestscat";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jarr = new JSONArray(response);
                    JSONObject js = null;

                    for (int i = 0; i < jarr.length(); i++) {
                        js = jarr.getJSONObject(i);

                        // nm = js.getString("nm");
                        String tc = js.getString("TestCode");
                        String des = js.getString("Description");
                        String mrp = js.getString("MRP");
                        mylist.add(tc+"-"+des+"-"+mrp);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();

                }

                pDialog.dismiss();
               // actv_tst.setAdapter(adapter);
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
                myData.put("lab",Lab);
                myData.put("ind1",ind1);
                myData.put("ind2",ind2);
                return myData;
            }
        };
        myreqque = Volley.newRequestQueue(this);
        mystringrequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        myreqque.add(mystringrequest) ;
       // myreqque.add(mystringrequest);


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
                        String tc = jsonObject.getString("TestCode");
                        String des = jsonObject.getString("Description");
                        String mrp = jsonObject.getString("MRP");
                       // language.add
                        mylist.add(tc+"-"+des+"-"+mrp);

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
        })
        {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("lab", "TTC");
                return myData;
            } };



        requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request) ;


    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
