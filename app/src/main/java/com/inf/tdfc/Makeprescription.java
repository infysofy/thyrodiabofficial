package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Makeprescription extends AppCompatActivity {
    String KEY_EMPTY = "";
    MydbAdapter_tkt db=new MydbAdapter_tkt(this);
    EditText name,age,phno,address,sbp,dbp,pulse,rbs,wt,ht,Email,remark,visit;
    String name1,age1,sex1,phno1,address1,sbp1,dbp1,pulse1,rbs1,wt1,ht1,Email1,bmi1,bdystrc1,remark1,visit1;
    Button btnnext,btnprev;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    ProgressDialog pDialog;
    int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeprescription);
        name = (EditText) findViewById(R.id.t_name);
        age = (EditText) findViewById(R.id.t_age);
        phno = (EditText) findViewById(R.id.t_phno);
        Email = (EditText) findViewById(R.id.t_email);
        address = (EditText) findViewById(R.id.t_address);
        sbp = (EditText) findViewById(R.id.t_sbp);
        dbp = (EditText) findViewById(R.id.t_dbp);
        pulse = (EditText) findViewById(R.id.t_pulse);
        rbs = (EditText) findViewById(R.id.t_rbs);
        wt = (EditText) findViewById(R.id.t_wt);
        ht = (EditText) findViewById(R.id.t_ht);
        remark = (EditText) findViewById(R.id.t_remark);
        visit = (EditText) findViewById(R.id.t_cchrg);
        btnnext = (Button) findViewById(R.id.btn_nxt);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnprev = (Button) findViewById(R.id.btn_prev);
        btnprev.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent in=new Intent(Makeprescription.this,Dashboard.class);
        startActivity(in);
        }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedId = radioSexGroup.getCheckedRadioButtonId();
                //sex1=radioSexButton.getText().toString();
                name1 = name.getText().toString().trim();
                age1 = age.getText().toString().trim();
                phno1 = phno.getText().toString().trim();
                Email1 = Email.getText().toString().trim();
                address1 = address.getText().toString().trim();
                sbp1 = sbp.getText().toString();
                dbp1 = dbp.getText().toString();
                pulse1 = pulse.getText().toString();
                rbs1 = rbs.getText().toString();
                wt1 = wt.getText().toString();
                ht1 = ht.getText().toString();
                remark1 = remark.getText().toString();
                visit1 = visit.getText().toString();

                if (validateInputs())
                {
                    displayLoader();
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    sex1 = radioSexButton.getText().toString();

                    if (!KEY_EMPTY.equals(wt1) && !KEY_EMPTY.equals(ht1)) {
                        Double Wtss = (Double.parseDouble(wt1) * 2.204) * 703;
                        Double Htss = ((Double.parseDouble(ht1) * 0.39f) * (Double.parseDouble(ht1) * 0.39f));
                        Double bmis = Wtss / Htss;
                        bmi1 =String.format("%.1f", bmis);
                        bdystrc1 = GetObese(bmis);
                    }
                   // insDatalocal(name1.toUpperCase() + "(" + age1 + "Y/" + sex1.toUpperCase() + ")", address1.toUpperCase(), phno1, Email1, "\nBasic Interpretation:\n BP:-->" + sbp1 + "/" + dbp1 + "\nPulse:-->" + pulse1 + "\nRandom Blood Sugar:-->" + rbs1 + "mg/dL"+"\nWeight(Kg.):-->"+wt1+"\nHeight(cm):-->"+ht1+"\nBMI:-->" + bmi1 + "(" + bdystrc1 + ")", remark1,Double.parseDouble(visit1));

                   //  callvolly(name1.toUpperCase() + "(" + age1 + "Y/" + sex1.toUpperCase() + ")", address1.toUpperCase(), phno1, Email1, "\nBasic Interpretation:\n BP:-->" + sbp1 + "/" + dbp1 + "\nPulse:-->" + pulse1 + "\nRandom Blood Sugar:-->" + rbs1 + "mg/dL"+"\nWeight(Kg.):-->"+wt1+"\nHeight(cm):-->"+ht1+"\nBMI:-->" + bmi1 + "(" + bdystrc1 + ")", remark1, visit1);

                }

            }
        });



    }
    public void insDatalocal(final String nm, final String add, final String ph, final String eml, final String clncldet, final String Remarks, final Double ccrg,final  String tempid){

        //db.insertTestDetails(nm,ph,add,eml,clncldet,ccrg,"NIL","NIL","NIL",Remarks,tempid);
        pDialog.dismiss();
        ClearText();


        //finish();
    }

    public void callvolly(final String nm, final String add, final String ph, final String eml, final String clncldet, final String Remarks, final String ccrg) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=makepress";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray ja= new JSONArray(response);
                    JSONObject jd = new JSONObject(response);
                    if (jd.getInt("log_stat") == 1) {

                        ClearText();
                        Uri uri = Uri.parse("smsto:" + ph);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body","Hi,"+nm+"-->"+clncldet );
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), jd.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), jd.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                    pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("nm", nm);
                myData.put("add", add);
                myData.put("ph", ph);
                myData.put("eml", eml);
                myData.put("clncldet", clncldet);
                myData.put("Remarks", Remarks);
                myData.put("ccrg", ccrg);
                return myData;
            }
        };
        mystringrequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        myreqque.add(mystringrequest);

    }

    public void ClearText(){
    name.setText("");
    age.setText("");
    phno.setText("");
    address.setText("");
    sbp.setText("");
    dbp.setText("");
    pulse.setText("");
    rbs.setText("");
    wt.setText("");
    ht.setText("");
    Email.setText("");
    remark.setText("");
    visit.setText("");
}

    public String GetObese(Double Bmi) {
        String res = null;
        if (Bmi < 18.6f) {
            res = "THIN";
        } else if (Bmi < 25.0f) {
            res = "HEALTHY";
        } else if (Bmi < 30.00f) {
            res = "OVER WEIGHT";
        } else if (Bmi > 30f) {
            res = "FATTY";
        }

        return res;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(Makeprescription.this);
        pDialog.setMessage("Registering.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(name1)) {
            name.setError("Name cannot be empty");
            name.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(age1)) {
            age.setError("Age cannot be empty");
            age.requestFocus();
            return false;
        }
        if (selectedId == -1) {
            Toast.makeText(getApplicationContext(), "Please select a Gender( Male / Female)", Toast.LENGTH_LONG).show();
            //radioSexButton.setError("Please select a sex");
            //radioSexButton.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(address1)) {
            address.setError("Address cannot be empty");
            address.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(phno1)) {
            phno.setError("Phone number cannot be empty");
            phno.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(sbp1)) {
            sbp.setError("SBP cannot be empty");
            sbp.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(dbp1)) {
            dbp.setError("DBP Name cannot be empty");
            dbp.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(pulse1)) {
            pulse.setError("PULSE cannot be empty");
            pulse.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(rbs1)) {
            rbs.setError("RBS cannot be empty");
            rbs.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(wt1)) {
            wt.setError("Weight cannot be empty");
            wt.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(visit1)) {
            visit.setError("Please fill Visit Charge");
            visit.requestFocus();
            return false;

        }
       // pDialog.dismiss();
        return true;

    }
    @Override
    public void onBackPressed() {}
}