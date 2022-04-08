package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Viewpresdetails extends AppCompatActivity {
    MydbAdapter_tkt db=new MydbAdapter_tkt(Viewpresdetails.this);
    ProgressDialog pDialog;
    String oidds,oname,oaddr,ophone,oemail,oclncdet,oremark,occharg,odts;
    TextView idds,name,phone,clncdet,ccharg;
    String Idd;
    Button prv,share,del,call,sms,print;
    String Doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpresdetails);
        //Textview Area
        idds=(TextView)findViewById(R.id.us_id);
        name=(TextView)findViewById(R.id.nm);
        phone=(TextView)findViewById(R.id.phno);
        clncdet=(TextView)findViewById(R.id.clncldet);
        ccharg=(TextView)findViewById(R.id.visit);
        //Button Area
        prv=(Button)findViewById(R.id.btn_prv);
        share=(Button)findViewById(R.id.btn_share);
       // del=(Button)findViewById(R.id.btn_del);
        call=(Button)findViewById(R.id.btn_call);
        sms=(Button)findViewById(R.id.btn_sms);
        print=(Button)findViewById(R.id.btn_print);
      //  displayLoader();
        //display loader
        //bundle intent section
            Bundle bundle = getIntent().getExtras();
            Idd =bundle.getString("Id");
            LoadTicketDetails(Idd);
        //volley section
         //callvolly(Idd.trim());//Idd.trim());
        //pDialog.dismiss();
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Viewpresdetails.this,TestList.class);
                startActivity(in);
            }
        });
         /*    nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Viewpresdetails.this);
                builder.setTitle("Thyrodiab Official");
                builder.setIcon(R.mipmap.icon_round);
                builder.setMessage("Do you want to remove this patient ?"+"Id:"+Idd)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                db.DeleteUser(Idd);
                                //LoadGrid();
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
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Viewpresdetails.this);
                builder.setTitle("Thyrodiab Official");
                builder.setIcon(R.mipmap.icon_round);
                builder.setMessage("Do you want to remove this patient ?"+"Id:"+Idd)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                db.DeleteUser(Idd);
                                //LoadGrid();
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
        });*/
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link="https://rzp.io/l/S4g9A2T";//"( http://www.thyrodiab.in/pay/"+a+")";
                Uri uri = Uri.parse("smsto:+91"+phone.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body","Hi,"+name.getText().toString()+"-->\n"+"\n"+clncdet.getText().toString()+"\nPay online at:\n"+link);
                startActivity(intent);
                //shareApp(TestList.this,"Hi,"+nm.getText().toString()+link+"\n-->"+clncldet.getText().toString()+"Pay online at:\n");
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91"+phone.getText().toString()));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);

            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in= new Intent(Viewpresdetails.this,PrintPage.class);
                Bundle bundle = new Bundle();
                String msg = "            Tax Invoice"+
                        "\n   MBODYMENT THYRODIAB CLINIC"+
                        "\n        www.thyrodiab.in"+
                        "\n Phno:+91-9830988233/9038003788"+
                        "\n--------------------------------"+

                        "\n   180 AJC BOSE ROAD KOLKATA"+
                        "\n   18/B DR SNM STREET HOOGHLY"+
                        "\n   10A M B ROAD BALLY HOWRAH"+
                        "\n   97  N P ROAD GHUSURI HOWRAH"+
                        "\n--------------------------------"+
                        "\nPatient Details: "+
                        "\n"+name.getText().toString()+
                        "\nPh no:"+phone.getText().toString()+
                        "\n--------------------------------"+
                        "\nConsultation fee "+
                        "\n"+Doc+ " Rs."+ ccharg.getText().toString()+
                        "\n--------------------------------"+
                        "\n"+
                        "\n--------------------------------"+
                        "\n      Clinical Interpretation"+
                        "\n"+
                        clncdet.getText().toString()+
                        "\n--------------------------------"+
                        "\n******Thank You Visit Again*****"+
                        "\nmaintained by www.infysofy.com"+
                        "\n"+"\n"+"\n";
                bundle.putString("msg",msg);

                // bundle.putString("oId",tid2.getText().toString().trim());
                in.putExtras(bundle);
                startActivity(in);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent in= new Intent(Viewpresdetails.this,PrintPage.class);
                //Bundle bundle = new Bundle();
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
                        "\n"+name.getText().toString()+
                        "\nPh no:"+phone.getText().toString()+
                        "\n--------------------------------"+
                        "\nConsultation fee "+
                        "\n"+Doc+ " Rs."+ccharg.getText().toString()+
                        "\n--------------------------------"+
                        "\n"+
                        "\n--------------------------------"+
                        "\n      Clinical Interpretation"+
                        "\n"+
                        clncdet.getText().toString()+
                        "\n--------------------------------"+
                        "\n******Thank You Visit Again*****"+
                        "\n        www.infysofy.com"+
                        "\n"+"\n"+"\n";
               shareApp(Viewpresdetails.this,msg);
                }

               //bundle.putString("msg",msg);

                // bundle.putString("oId",tid2.getText().toString().trim());
                //in.putExtras(bundle);
                //startActivity(in);


        });
    }
    public static void shareApp(Context context,String msg) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
    public  void LoadTicketDetails(String id)    {
        ArrayList<HashMap<String, String>> details =db.GetTicketId1(id);
        HashMap<String, String> user = details.get(0); // If it contains only one key value
      //  idds,name,phone,clncdet,ccharg;
        idds.setText(id);
        name.setText(user.get("nm"));
        phone.setText(user.get("ph"));
        clncdet.setText(user.get("cdet"));
        ccharg.setText(user.get("visit"));
        Doc=user.get("doc");
    }
    public void callvolly(final String id){

        RequestQueue myreqque = Volley.newRequestQueue(Viewpresdetails.this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=ticket_listone";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jarr = new JSONArray(response);
                    JSONObject js=null;
                    for (int i = 0; i < jarr.length(); i++) {
                        js = jarr.getJSONObject(i);
                        oidds=js.getString("id");
                        oname=js.getString("nm");
                        oaddr=js.getString("add");
                        ophone=js.getString("ph");
                        oemail=js.getString("eml");
                        oclncdet=js.getString("clncldet");
                        oremark=js.getString("Remarks");
                        occharg=js.getString("ccrg");
                        odts=js.getString("presdt");
                        idds.setText("Id:"+oidds+"\n\nPatient Name:\n\n"+oname+"\n\nPatient Details\n\n"+oaddr+"\n"+ophone+"\n"+oemail+"\n"+oclncdet+"\n\nRemak:\n\n"+oremark+"\n\nVisit:\n\nRs."+occharg+"\n\nDate:\n\n"+odts);
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
                myData.put("id", id);
                return myData;
            }
        };
        myreqque.add(mystringrequest);

    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Viewpresdetails.this);
        pDialog.setMessage("Loading.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public void deleteId(final String id){
        displayLoader();
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=deleteId";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray ja= new JSONArray(response);
                    JSONObject jd = new JSONObject(response);
                    if (jd.getInt("log_stat") == 1) {
                        Toast.makeText(getApplicationContext(), jd.getString("msg"), Toast.LENGTH_SHORT).show();
                        Intent in= new Intent(Viewpresdetails.this,Presdetails.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(getApplicationContext(), jd.getString("msg"), Toast.LENGTH_SHORT).show();
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
