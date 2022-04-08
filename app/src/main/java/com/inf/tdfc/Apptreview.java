package com.inf.tdfc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Apptreview extends AppCompatActivity {
    DatePickerDialog picker;
    DatePickerDialog picker1;
    private final String JSON_URL = "http://api.thyrodiab.in/androidapp/services.php?action=srch_appt" ;
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    Button back;
    ArrayList<String> mylist;
    ListView lv;
    ArrayAdapter<String> Ad;
    ProgressDialog pDialog;
    int j=0;
    EditText ts;
    Spinner mnthh,yerr;
   TextView tv,tv1,tv3,tv4,subject;
   Button srchmnth,srchday,home,tstsrchmnth,tstsrchday;
    MydbAdapter_tkt dxb=new MydbAdapter_tkt(Apptreview.this);

    @Override
    protected void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apptreview);
        lv=(ListView)findViewById(R.id.listview_app);
        mylist= new ArrayList<String>();
        Ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,mylist);
        srchmnth=(Button)findViewById(R.id.btn_mnthsrch);
        srchday=(Button)findViewById(R.id.btn_datesrch);
        tstsrchmnth=(Button)findViewById(R.id.btn_tstmnthsrch);
        tstsrchday=(Button)findViewById(R.id.btn_tstdatesrch);
        home=(Button)findViewById(R.id.btn_home);
        tv=(TextView)findViewById(R.id.tv_date);
        tv1=(TextView)findViewById(R.id.tv_month);
        tv3=(TextView)findViewById(R.id.tv_tstdate);
        tv4=(TextView)findViewById(R.id.tv_tstmonth);
        subject=(TextView)findViewById(R.id.lvdet);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        String dtaa=getDtlngtosrt(dateString);
        tv.setText(dtaa);
        tv3.setText(dtaa);

        long date1 = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String dateString1 = sdf.format(date1);
        String dtaa1=getDtlngtosrt1(dateString1);
        tv1.setText(dtaa1);
        tv4.setText(dtaa1);

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Apptreview.this,Dashboard.class);
                startActivity(in);

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker1 = new DatePickerDialog(Apptreview.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv.setText( getDtlngtosrt(dayOfMonth + "/" + (monthOfYear+1) + "/" + year));
                            }
                        }, year, month, day);
                picker1.show();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker1 = new DatePickerDialog(Apptreview.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv3.setText( getDtlngtosrt(dayOfMonth + "/" + (monthOfYear+1) + "/" + year));
                            }
                        }, year, month, day);
                picker1.show();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Apptreview.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv1.setText(getDtlngtosrt1(dayOfMonth + "/"+(monthOfYear+1)+ "/" + year));
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Apptreview.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv4.setText(getDtlngtosrt1(dayOfMonth + "/"+(monthOfYear+1)+ "/" + year));
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        srchday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dt=tv.getText().toString();
                displayLoader();
                mylist.clear();
                subject.setText("Doctor Appointment List");
                String uid=dxb.GetSpCode();
                Toast.makeText(getApplicationContext(),uid, Toast.LENGTH_LONG).show();
               callvolly(dt,uid);
            }
        });
        tstsrchday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dt=tv3.getText().toString();
                displayLoader();
                mylist.clear();
                subject.setText("Test Appointment List");
                String uid=dxb.GetSpCode();
                Toast.makeText(getApplicationContext(),uid, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),dt, Toast.LENGTH_LONG).show();
                callvolly2(dt,uid);
            }
        });
        srchmnth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dst="%-"+tv1.getText().toString();
                displayLoader();
                mylist.clear();
                subject.setText("Doctor Appointment List");
                String uid=dxb.GetSpCode();
                Toast.makeText(getApplicationContext(),uid, Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),dt, Toast.LENGTH_LONG).show();
              callvolly(dst,uid);
            }
        });

        tstsrchmnth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dst="%-"+tv4.getText().toString();
                displayLoader();
                mylist.clear();
                subject.setText("Test Appointment List");
                String uid=dxb.GetSpCode();
                Toast.makeText(getApplicationContext(),uid, Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),dt, Toast.LENGTH_LONG).show();
                callvolly(dst,uid);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //displayLoader();
                String selectedmovie=mylist.get(position);
                String[] arr = selectedmovie.split("-");
                Intent in= new Intent(Apptreview.this,LoadPatientinfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id",arr[3].replace('#',' ').trim());
                in.putExtras(bundle);
                startActivity(in);

                //Toast.makeText(getApplicationContext(), "Selected Item : "+arr[3].replace('#',' ').trim(),   Toast.LENGTH_LONG).show();
                //pDialog.dismiss();
            }
        });


    }
    public void callvolly(final String dt,final String uid) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=srch_appt1";

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
                            String Name = jsonObj.getString("nm");
                            String Id = jsonObj.getString("id");
                            String rvwdt = jsonObj.getString("rvwdt");
                            String doc=jsonObj.getString("doc");
                            if(doc.equals("DR ARGHYA CHATTERJEE"))
                            {
                                //Toast.makeText(getApplicationContext(),"("+rvwdt+")"+Name+"-"+"#"+Id, Toast.LENGTH_SHORT).show();
                                mylist.add("(" + rvwdt + ")  " + Name + "-" + "#" + Id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "ERROR1", Toast.LENGTH_SHORT).show();
                        }

                        lv.setAdapter(Ad);
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
                myData.put("id", dt);
                myData.put("uid",uid);
                return myData;
            }
        };
        myreqque.add(mystringrequest);

    }
    public void callvolly2(final String dt,final String uid) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=srch_appttst1";

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
                            String Name = jsonObj.getString("nm");
                            String Id = jsonObj.getString("id");
                            String rvwdt = jsonObj.getString("tstdt");
                            //Toast.makeText(getApplicationContext(),"("+rvwdt+")"+Name+"-"+"#"+Id, Toast.LENGTH_SHORT).show();
                            mylist.add("(" + rvwdt + ")  " + Name + "-" + "#" + Id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "ERROR1", Toast.LENGTH_SHORT).show();
                        }

                        lv.setAdapter(Ad);
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
                myData.put("id", dt);
                myData.put("uid", uid);
                return myData;
            }
        };
        myreqque.add(mystringrequest);

    }
    private void displayLoader (){
        pDialog = new ProgressDialog(Apptreview.this);
        pDialog.setMessage("Loading.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public String getDtlngtosrt(String dtss){
        String mStringDate = dtss;
        String oldFormat= "dd/MM/yyyy";
        String newFormat= "dd-MM-yyyy";

        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        java.util.Date myDate = null;
        try {
            myDate = dateFormat.parse(mStringDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);

        return formatedDate;
    }
    public String getDtlngtosrt1(String dtss){
        String mStringDate = dtss;
        String oldFormat= "dd/M/yyyy";
        String newFormat= "MM-yyyy";

        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        java.util.Date myDate = null;
        try {
            myDate = dateFormat.parse(mStringDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);

        return formatedDate;
    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
