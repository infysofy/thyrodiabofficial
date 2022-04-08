package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestList extends AppCompatActivity {
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> Ad;
    String lab;
    ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, String>> userList2;
    MydbAdapter_tkt db;
    Button view,uploadSync,prev;
    ProgressDialog pDialog;
    TextView ph,nm,clncldet,id,visits;
    String Visitss="";
    String clncdetss="";
    String msg="";
    String id1="";
    String idd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        db = new MydbAdapter_tkt(this);
        lv = (ListView) findViewById(R.id.ListView);
        view = (Button) findViewById(R.id.btn_vew);
        prev=(Button)findViewById(R.id.btn_prev);
        userList = db.GetTicketsDirect();
        userList2 = db.GetTicket();

        LoadGrid();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String uid=db.GetSpCode();
                view.setVisibility(view.GONE);
                int l = userList.size();
                if (l != 0) {
                    displayLoaderSync();
                    for (int j = 0; j < userList.size(); j++) {
                        HashMap<String, String> hashmap = userList.get(j);
                        if (hashmap.get("tempid").equals("0")) {
                            /*
                           // callvolly(hashmap.get("filenm"),hashmap.get("nm"),hashmap.get("ad"),hashmap.get("ph"),hashmap.get("eml"), hashmap.get("clcdet"),hashmap.get("remrk"),hashmap.get("ccrg"),hashmap.get("rvwdt"),hashmap.get("tstdt"));
                           String ofilenm = hashmap.get("filenm");
                            String onm = hashmap.get("nm");
                            String oad = hashmap.get("ad");
                            String oph = hashmap.get("ph");
                            String oeml = hashmap.get("eml");
                            String ocdet = hashmap.get("clcdet");
                            String oremark = hashmap.get("remrk");
                            String occrg = hashmap.get("ccrg");
                            String orvwdt = hashmap.get("rvwdt");
                            String otstdt = hashmap.get("tstdt");
                         *///hashmap.get("id")
                          //  String uiid=uid;
                            callvolly(hashmap.get("img"), hashmap.get("nm"), hashmap.get("ad"), hashmap.get("ph"), hashmap.get("eml"), hashmap.get("clcdet"), hashmap.get("remrk"), hashmap.get("ccrg"), hashmap.get("rvwdt"), hashmap.get("tstdt"),hashmap.get("id"),hashmap.get("area"),hashmap.get("doc"),hashmap.get("spid"));
                          // Toast.makeText(getApplicationContext(), hashmap.get("spid"), Toast.LENGTH_SHORT).show();
                            //db.DeleteUser(hashmap.get("id"));
                            // Toast.makeText(getApplicationContext(), hashmap.get("nm")+hashmap.get("ad")+hashmap.get("ph")+hashmap.get("eml")+hashmap.get("clcdet")+hashmap.get("remrk")+hashmap.get("ccrg")+hashmap.get("rvwdt")+hashmap.get("tstdt")+hashmap.get("id"), Toast.LENGTH_LONG).show();
                            view.setVisibility(view.VISIBLE);
                        } else {
                            //callvollyUpldImg(hashmap.get("img"), hashmap.get("tempid"));
                            //callvolly2(hashmap.get("rvwdt"), hashmap.get("tstdt"), hashmap.get("remrk"), hashmap.get("tempid"));
                            // db.DeleteUser(hashmap.get("id"));
                            pDialog.dismiss();
                            view.setVisibility(view.VISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! No data Available to Sync. ", Toast.LENGTH_SHORT).show();
                    view.setVisibility(view.VISIBLE);
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(TestList.this,MakePresNw.class);
                startActivity(in);
            }
        });
    }

    public void LoadGrid()    {
        userList2 = db.GetTicket();
       // ListAdapter adapter = new SimpleAdapter(TestList.this, userList2, R.layout.list_tktdetails, new String[]{"pid", "pnm", "pph", "oid"}, new int[]{R.id.tktid, R.id.tkname, R.id.tktph, R.id.tmpid});
        //lv.setAdapter(adapter);
        SimpleAdapter k=new SimpleAdapter(this,userList2,R.layout.list_tktdetails,new String[]{"pid", "pnm"}, new int[]{R.id.tktid, R.id.tkname})
        {
            @Override
            public View getView (int position, View convertView, ViewGroup parent)
            {
                //final
                final   View v = super.getView(position, convertView, parent);
                Button view=(Button)v.findViewById(R.id.btn_lv_view);
                Button del=(Button)v.findViewById(R.id.btn_lv_del);
               // Button s=(Button)v.findViewById(R.id.btn_lv_sms);
               // Button p=(Button)v.findViewById(R.id.btn_lv_print);
                //final TextView  ph=(TextView)v.findViewById(R.id.tktph);
                //final TextView nm=(TextView)v.findViewById(R.id.tkname);
                //final TextView clncldet=(TextView)v.findViewById(R.id.tmpid);
                final TextView id=(TextView)v.findViewById(R.id.tktid);
                //final TextView visits=(TextView)v.findViewById(R.id.visitid);

               // id1=id.getText().toString();
                //ph1=ph.getText().toString();
                //nm1=nm.getText().toString();
                //clncldet1=clncldet.getText().toString();
                //visits1=visits.getText().toString();
               // String item = (String) lv.getItemAtPosition(position);
               // Toast.makeText(TestList.this,"You selected : " + item,Toast.LENGTH_SHORT).show();

             del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        idd=id.getText().toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(TestList.this);
                        builder.setTitle("Thyrodiab Official");
                        builder.setIcon(R.mipmap.icon_round);
                        builder.setMessage("Do you want to remove this patient ?"+"Id:"+id.getText().toString())
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.DeleteUser(idd);
                                        LoadGrid();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        id1=id.getText().toString();
                        Toast.makeText(getApplicationContext(), "Selected Id: "+id1, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id",id1);
                        Intent intent = new Intent(TestList.this, Viewpresdetails.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //Intent intent = new Intent(TestList.this, dialog_viewDetails.class);
                        //intent.putExtras(bundle);
                       // startActivity(intent);
                      //  public String  ids=id.getText().toString();
                       // id1=id.getText().toString();
                     /*   AlertDialog.Builder builder = new AlertDialog.Builder(TestList.this);
                        builder.setTitle("Thyrodiab Official");
                        builder.setIcon(R.mipmap.icon_round);
                        builder.setMessage("Do you want to remove this patient ?"+"Id:"+id1)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.DeleteUser(id1);
                                        LoadGrid();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();*/

                    }
                });

               /* s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   id1=id.getText().toString();
                   //     ph1=ph.getText().toString();
                        String a=toBase64(visits1+"00"+"|"+id1+ph1+"|"+ph1+"|"+"no-reply@thyrodiab.in"+"|"+nm1);
                        // String b=;//toBase64(id.getText().toString()+ph.getText().toString());
                        String link="https://rzp.io/l/S4g9A2T";//"( http://www.thyrodiab.in/pay/"+a+")";
                        Uri uri = Uri.parse("smsto:+91"+ph1);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body","Hi,"+nm1+"-->\n"+"\n"+clncldet1+"\nPay online at:\n"+link);
                        startActivity(intent);
                        //shareApp(TestList.this,"Hi,"+nm.getText().toString()+link+"\n-->"+clncldet.getText().toString()+"Pay online at:\n");
                    }
                });
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                     //   ph1=ph.getText().toString();

                        // TODO Auto-generated method stub
                        Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91"+ph1));
                        sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(sIntent);
                    }
                });

                p.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                     //   id1=id.getText().toString();
                      //  ph1=ph.getText().toString();
                      //  nm1=nm.getText().toString();
                      //  clncldet1=clncldet.getText().toString();
                       // visits1=visits.getText().toString();
                        Intent in= new Intent(TestList.this,PrintPage.class);
                        Bundle bundle = new Bundle();
                        String msg = "            Tax Invoice"+
                                "\n   MBODYMENT THYRODIAB CLINIC"+
                                "\n        www.thyrodiab.in"+
                                "\n Phno:+91-9038003788/9830282334"+
                                "\n--------------------------------"+

                                "\n   155A AJC BOSE ROAD KOLKATA"+
                                "\n   18/B DR SNM STREET HOOGHLY"+
                                "\n   10A M B ROAD, BALLY HOWRAH"+
                                "\n   97, N P ROAD,GHUSURI,HOWRAH"+
                                "\n--------------------------------"+
                                "\nPatient Details: "+
                                "\n"+nm1+
                                "\nPh no:"+ph1+
                                "\n--------------------------------"+
                                "\nConsultation fee "+
                                "\nDR ABHISEKH BHATTACHARYYA Rs."+ visits1+
                                "\n--------------------------------"+
                                "\n"+
                                "\n--------------------------------"+
                                "\n      Clinical Interpretation"+
                                "\n"+
                                clncldet1+
                                "\n--------------------------------"+
                                "\n******Thank You Visit Again*****"+
                                "\n        www.infysofy.com"+
                                "\n"+"\n"+"\n";
                        bundle.putString("msg",msg);

                        // bundle.putString("oId",tid2.getText().toString().trim());
                        in.putExtras(bundle);
                        startActivity(in);
                    }

                });*/
                return v;
            }


        };
        lv.setAdapter(k);

    }
    public static String toBase64(String message) {
        byte[] data;
        try {
            data = message.getBytes("UTF-8");
            String base64Sms = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Sms;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void shareApp(Context context,String msg){
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
    private void displayLoaderSync() {
        view.setEnabled(false);
        pDialog = new ProgressDialog(TestList.this);
        pDialog.setMessage("Syncing.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public void callvolly(final String filename,final String nm, final String add, final String ph, final String eml, final String clncldet, final String Remarks, final String ccrg,final String rvwdt, final String tstdt,final String oid,final String area,final String doc,final String spid) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=makepressDirect4";
        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray ja= new JSONArray(response);
                    JSONObject jd = new JSONObject(response);
                    int i=jd.getInt("log_stat");
                    if (i==1) {

                        db.DeleteUser(oid);
                        LoadGrid();
                       // pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                    } else {
                       Toast.makeText(getApplicationContext(),"Error1"+jd.getString("log_stat"), Toast.LENGTH_SHORT).show();
                    }
                    view.setEnabled(true);
                    pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error2", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setEnabled(true);
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error3 :-"+ error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();

                myData.put("nm", nm);
                myData.put("add", add);
                myData.put("ph", ph);
                myData.put("eml", eml);
                myData.put("clncldet", clncldet);
                myData.put("rvwdt", rvwdt);
                myData.put("tstdt", tstdt);
                myData.put("Remarks", Remarks);
                myData.put("ccrg", ccrg);
                myData.put("filename", filename);
                myData.put("sp", spid);
                myData.put("area", area);
                myData.put("doc", doc);
                return myData;
            }
        };
       mystringrequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        myreqque.add(mystringrequest);

    }

    public void onBackPressed() {}

}
