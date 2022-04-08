package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Presclose extends AppCompatActivity {
    String oid, odt,orem;
    Spinner revday, tstday;
    EditText et_revday, et_tstday,et_remark;
    Button previ, nexti,upload;
    RadioGroup daygroup1, daygroup2;
    RadioButton radioButton1, radioButton2;
    String revDay,tstDay,Remar;
    int selectedId1 = -1;
    int selectedId2 = -1;
    String KEY_EMPTY = "";
    ProgressDialog pDialog;
    private final int requestCode = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presclose);
        et_revday = (EditText) findViewById(R.id.editText_revday);
        et_tstday = (EditText) findViewById(R.id.editText_tstday);
        et_remark = (EditText) findViewById(R.id.editText_rem);
        daygroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        daygroup2 = (RadioGroup) findViewById(R.id.radiogroup2);
        previ = (Button) findViewById(R.id.btn_prvi);
        nexti = (Button) findViewById(R.id.btn_nxti);
        upload=(Button) findViewById(R.id.button_upld);
        Bundle bundle = getIntent().getExtras();
        oid = bundle.getString("ids");
        odt = getDtlngtosrt(bundle.getString("dat"));
        orem=bundle.getString("rem");
        previ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in= new Intent(Presclose.this,Viewpresdetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id",oid);
                in.putExtras(bundle);
                startActivity(in);
                //   Toast.makeText(getApplicationContext(),odt, Toast.LENGTH_SHORT).show();
            }
        });
        nexti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedId1 = daygroup1.getCheckedRadioButtonId();
                selectedId2 = daygroup2.getCheckedRadioButtonId();
                revDay = et_revday.getText().toString().trim();
                tstDay = et_tstday.getText().toString().trim();
                if (validateInputs()) {
                    displayLoader();
                    Remar=orem+"-->"+et_remark.getText().toString();
                    radioButton1 = (RadioButton) findViewById(selectedId1);
                    radioButton2 = (RadioButton) findViewById(selectedId2);
                    String Text = radioButton1.getText().toString().trim();
                    String Text1 = radioButton2.getText().toString().trim();
                    String dtsrd = gtdt(odt, getdays(Text),revDay);
                    String dtbtd=gtdt(odt, getdays(Text1),tstDay);
                    callvolly(dtsrd,dtbtd,Remar,oid);
                    Toast.makeText(getApplicationContext(), "Next Review Date:"+dtsrd+"\n"+"Blood Test Date:"+dtbtd, Toast.LENGTH_LONG).show();

                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
    @Override
         public void onClick(View v) {
        Intent in= new Intent(Presclose.this,Uploadpresimg.class);
        Bundle bundle = new Bundle();
        bundle.putString("Id",oid);
        in.putExtras(bundle);
        startActivity(in);
         }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            displayLoader();

            //Old Code
            /*
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
            byte[] byteArray = stream.toByteArray();
            //imageHolder.setImageBitmap(bitmap);
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            callvolly2(encodedImage,oid.trim());
            */
        }
    }
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    public void callvolly(final String rvwdt, final String tstdt, final String remark,final String id) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=insdt";

        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray ja= new JSONArray(response);
                    JSONObject jd = new JSONObject(response);

                        Toast.makeText(getApplicationContext(), jd.getString("msg"), Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(Presclose.this,Presdetails.class);
                        startActivity(in);
                        //finish();

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
                myData.put("rvwdt", rvwdt);
                myData.put("tstdt", tstdt);
                myData.put("remark", remark);
                myData.put("stat","CLOSE");

                return myData;
            }
        };


        myreqque.add(mystringrequest);

    }
    public String getDtlngtosrt(String dtss){
        String mStringDate = dtss;
        String oldFormat= "dd-MM-yyyy h:m:s";
        String newFormat= "dd-MM-yyyy";

        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mStringDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);

        return formatedDate;
    }
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(revDay)) {
            et_revday.setError("Please fill revisit days");
            et_revday.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(tstDay)) {
            et_tstday.setError("Please fill test days");
            et_tstday.requestFocus();
            return false;
        }
        if(selectedId1==-1)
        {
            Toast.makeText(getApplicationContext(),"Please choose Day/Month/Year", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedId2==-1)
        {
            Toast.makeText(getApplicationContext(),"Please choose Day/Month/Year", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public int getdays(String item){
        int i=0;
        if(item.equals("Day"))
        {
            i=1;
        }
        else if(item.equals("Month"))
        {
            i=30;
        }
        else if(item.equals("Year"))
        {
            i=365;
        }
        else if(item.equals("Week"))
        {
            i=7;
        }
        return i;
    }
    public String gtdt(String dateInString,int days,String dksi){
        int dki=Integer.parseInt(dksi);//=0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(odt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int daysss=days*dki;
        c.add(Calendar.DATE, daysss);
        String dt = sdf.format(c.getTime());
        return  dt;
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Presclose.this);
        pDialog.setMessage("Registering Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
