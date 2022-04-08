package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;


public class UserLogin extends AppCompatActivity {
    private final String JSON_URL = "http://api.thyrodiab.in/androidapp/services.php?action=tdofc_userlogin" ;
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    EditText tdofc_uid, tdofc_pwd;
    Button tdofc_login;
    ProgressDialog pDialog;
    String id,nms,img,area,ls,ls1,doc;
    MydbAdapter_tkt db=new MydbAdapter_tkt(UserLogin.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        tdofc_uid=(EditText)findViewById(R.id.tdofc_uid);
        tdofc_pwd=(EditText)findViewById(R.id.tdofc_pwd);
        tdofc_login=(Button)findViewById(R.id.btn_tdofc_login);
        tdofc_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=tdofc_uid.getText().toString();
                String pwd=tdofc_pwd.getText().toString();
                callvolly(uid,pwd);
              //  jsonrequest(uid,pwd);
            }
        });
    }
  /*  public void callvolly(final String uid,final String pwd) {
        displayLoader();
        RequestQueue myreqque = Volley.newRequestQueue(UserLogin.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=tdofc_userlogin";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jarr = new JSONArray(response);
                    JSONObject js = null;

                    for (int i = 0; i < jarr.length(); i++) {
                        js = jarr.getJSONObject(i);
                        id = js.getString("id");
                        nms = js.getString("name");
                        img = js.getString("img");
                        area = js.getString("area");
                        ls1 = js.getString("logstat");
                       int lss = js.getInt("log_stat");
                        if(lss!=1)
                        {
                            Toast.makeText(getApplicationContext(),"Check username or password", Toast.LENGTH_LONG).show();
                            pDialog.dismiss();

                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),id+nms+img+area+ls1, Toast.LENGTH_LONG).show();
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
                myData.put("uid", uid);
                myData.put("pwd", pwd);
                return myData;
            }
        };
        myreqque.add(mystringrequest);


    }
    public void callvolly(final String uids,final String pwds) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=tdofc_userlogin";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONArray jsonArr = null;
                try {
                    jsonArr = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonArr.length()!=0) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = jsonArr.getJSONObject(i);
                            String Id = jsonObj.getString("id");
                            String Name = jsonObj.getString("name");
                           String img = jsonObj.getString("img");
                            String Area = jsonObj.getString("area");
                            String ls = jsonObj.getString("logstat");
                            int ls1 = jsonObj.getInt("log_stat");
                            if(ls1!=1)
                            {
                                Toast.makeText(getApplicationContext(),"("+Name+")"+Area+"-"+"#"+Id, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Check Username Password", Toast.LENGTH_SHORT).show();
                            }
                          //  mylist.add("(" + rvwdt + ")  " + Name + "-" + "#" + Id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "ERROR1", Toast.LENGTH_SHORT).show();
                        }

                       // lv.setAdapter(Ad);
                        pDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Sorry!no data found.", Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR2", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("uid", uids);
                myData.put("pwd", pwds);

                return myData;
            }
        };
        myreqque.add(mystringrequest);

    }*/
    public void callvolly(final String uid,final String pwd){
    displayLoader();
        RequestQueue myreqque = Volley.newRequestQueue(UserLogin.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=tdofc_userlogin";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jarr = new JSONArray("["+response+"]");
                    JSONObject js=null;
                    for (int i = 0; i < jarr.length(); i++) {
                        js = jarr.getJSONObject(i);
                        id=js.getString("id");
                        nms=js.getString("nm");
                        img=js.getString("img");
                        area=js.getString("area");
                        doc=js.getString("doc");
                        ls1=js.getString("log_stat");


                   if(ls1.equals("1"))
                   {
                       db.insertuserDetails(id,nms,img,area,ls1,doc);
                       Toast.makeText(getApplicationContext(),"Login Succesful"+nms, Toast.LENGTH_LONG).show();
                       Intent in = new Intent(UserLogin.this, Dashboard.class);
                       startActivity(in);

                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"Please Check Username Password", Toast.LENGTH_LONG).show();
                   }
                    }

                    pDialog.dismiss();
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
                myData.put("uid", uid);
                myData.put("pwd", pwd);
                return myData;
            }
        };
        myreqque.add(mystringrequest);

    }

    private void jsonrequest(final String uid,final String pwd) {
        displayLoader();

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;
                for (int i = 0 ; i < response.length(); i++ ) {
                    try {
                        jsonObject = response.getJSONObject(i) ;
                        id=jsonObject.getString("id");
                        nms=jsonObject.getString("nm");
                        img=jsonObject.getString("img");
                        area=jsonObject.getString("area");
                        ls=jsonObject.getString("ls");
                        ls1=jsonObject.getString("ls1");
                        //String Names=jsonObject.getString("nm");
                        //String Ids=jsonObject.getString("id");

                        Toast.makeText(getApplicationContext(),nms+id, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error1",Toast.LENGTH_LONG).show();
                    }

                }


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
                myData.put("uid", uid);
                myData.put("pwd", pwd);
                return myData;
            }
        };



        requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request) ;


    }
    private void displayLoader() {
        pDialog = new ProgressDialog(UserLogin.this);
        pDialog.setMessage("Log in.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
}