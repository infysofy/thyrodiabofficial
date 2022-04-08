package com.inf.tdfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MakePresNw extends AppCompatActivity {
    MydbAdapter_tkt dxxb=new MydbAdapter_tkt(MakePresNw.this);
    String KEY_EMPTY = "";
    private Bitmap mImageBitmap;
    boolean result;
    ImageView viewImage;
    public static Button b;
    public static TextView ulimag;
    Spinner area,doc;
    TextView rvw,tst,tid,tid2;
    EditText name,age,phno,address,sbp,dbp,pulse,rbs,wt,ht,Email,remark,visit,clcdet;
    String name1,age1,sex1,phno1,address1,sbp1,dbp1,pulse1,rbs1,wt1,ht1,Email1,bmi1,bdystrc1,remark1,visit1,odt;
    Button btninsert,btnupdate,btnprev,clrpatdet,uploadimg,btnnxt;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    ProgressDialog pDialog;
    //AutoCompleteTextView tvac;
    int selectedId = -1;
    ArrayList<String> userList;
    MydbAdapter_tkt db;
    AutoCompleteTextView actv;
    EditText et_revday, et_tstday,et_remark;
    Button previ, nexti;
    RadioGroup daygroup1, daygroup2;
    RadioButton radioButton1, radioButton2;
    String revDay,tstDay,Remar,dtsrd,dtbtd;
    int selectedId1 = -1;
    int selectedId2 = -1;
    Uri fileUri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pres_nw);

        //Headar Part

        actv = (AutoCompleteTextView) findViewById(R.id.actv_ps);
        btninsert = (Button) findViewById(R.id.btn_insert);
        btnupdate = (Button) findViewById(R.id.btn_update);
        btnprev = (Button) findViewById(R.id.btn_back);
        btnnxt = (Button) findViewById(R.id.btn_list);
        clcdet = (EditText) findViewById(R.id.clcdet);
        clcdet.setVisibility(View.GONE);
        clrpatdet = (Button) findViewById(R.id.btn_clrsrchpat);
        clrpatdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actv.setText("");
                ClearText();
                Insertview();
            }
        });
        //Patient Entry Details

        name = (EditText) findViewById(R.id.t_name);
        age = (EditText) findViewById(R.id.t_age);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
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
        area=(Spinner)findViewById(R.id.t_lstarea);
        doc=(Spinner)findViewById(R.id.t_lstdoc);

        //revisit and tst section

        rvw = (TextView) findViewById(R.id.tv_rvwdates);
        tst = (TextView) findViewById(R.id.tv_tstdate);
        et_revday = (EditText) findViewById(R.id.et_rvwdt);
        et_tstday = (EditText) findViewById(R.id.et_tstday);
        daygroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        daygroup2 = (RadioGroup) findViewById(R.id.radiogroup2);
        ulimag = (TextView) findViewById(R.id.tv_ulimg);
        tid=(TextView)findViewById(R.id.tv_id);
        tid2=(TextView)findViewById(R.id.tv_id2);
        loadPatTkt();

        //cam section

        viewImage = (ImageView) findViewById(R.id.imageViewPic);

        //Clear


        //Image Upload Section

        b = (Button) findViewById(R.id.uploadbtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MakePresNw.this,Uploadpresimg.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id",tid.getText().toString().trim());
               // bundle.putString("oId",tid2.getText().toString().trim());
                in.putExtras(bundle);
                startActivity(in);
            }
        });

        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in= new Intent(MakePresNw.this,TestList.class);
                startActivity(in);
            }
        });
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(MakePresNw.this,Dashboard.class);
                startActivity(in);

            }
        });
        btninsert.setOnClickListener(new View.OnClickListener() {
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

                if (validateInputs()) {
                    displayLoader();
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    sex1 = radioSexButton.getText().toString();
                    if (!KEY_EMPTY.equals(wt1) && !KEY_EMPTY.equals(ht1)) {
                        Double Wtss = (Double.parseDouble(wt1) * 2.204) * 703;
                        Double Htss = ((Double.parseDouble(ht1) * 0.39f) * (Double.parseDouble(ht1) * 0.39f));
                        Double bmis = Wtss / Htss;
                        bmi1 = String.format("%.1f", bmis);
                        bdystrc1 = GetObese(bmis);
                    }
                    viewImage.setImageBitmap(mImageBitmap);
                 callvolly(name1.toUpperCase() + "(" + age1 + "Y/" + sex1.toUpperCase() + ")", address1.toUpperCase(), phno1, Email1, "\nBasic Interpretation:\n BP:-->" + sbp1 + "/" + dbp1 + "\nPulse:-->" + pulse1 + "\nRandom Blood Sugar:-->" + rbs1 + "mg/dL"+"\nWeight(Kg.):-->"+wt1+"\nHeight(cm):-->"+ht1+"\nBMI:-->" + bmi1 + "(" + bdystrc1 + ")", remark1, visit1,area.getSelectedItem().toString(),doc.getSelectedItem().toString());


                }

            }
        });
    btnupdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(b.getVisibility()!=View.VISIBLE) {
            selectedId1 = daygroup1.getCheckedRadioButtonId();
            selectedId2 = daygroup2.getCheckedRadioButtonId();
            if (validateInputs2()) {
                displayLoader();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yy");
                String strDate = mdformat.format(calendar.getTime());
                // odt
                revDay = et_revday.getText().toString().trim();
                tstDay = et_tstday.getText().toString().trim();
                //Remar=orem+"-->"+et_remark.getText().toString();
                radioButton1 = (RadioButton) findViewById(selectedId1);
                radioButton2 = (RadioButton) findViewById(selectedId2);
                String Text = radioButton1.getText().toString().trim();
                String Text1 = radioButton2.getText().toString().trim();
                dtsrd = gtdt(strDate, getdays(Text), revDay);
                dtbtd = gtdt(strDate, getdays(Text1), tstDay);

                db.UpdateTicketDetails(name.getText().toString(), address.getText().toString(), phno.getText().toString(), Email.getText().toString(), clcdet.getText().toString(), visit.getText().toString(), remark.getText().toString(), dtsrd, dtbtd, "", tid.getText().toString().trim());//
                Insertview();
                ClearText();
                loadPatTkt();
                actv.setText("");
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Review Date:"+dtsrd+"\nTest Date:"+dtbtd, Toast.LENGTH_LONG).show();
            }
        }
        else
            {
                Toast.makeText(getApplicationContext(),"Sorry!Please Capture a image of Prescription.", Toast.LENGTH_SHORT).show();
        }
        }
    });

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                ClearText();
                EditView();
                //TODO Do something with the selected text
                String[] arr = selection.split("-");
                tid.setText(arr[1].trim());
                Toast.makeText(getApplicationContext(),arr[1], Toast.LENGTH_SHORT).show();
                ArrayList<HashMap<String, String>> details =db.GetTicketId(arr[1].trim());
                HashMap<String, String> user = details.get(0); // If it contains only one key value
                name.setText(user.get("nm"));
                phno.setText(user.get("ph"));
                clcdet.setText(user.get("cdet"));
                address.setText(user.get("add"));
                Email.setText(user.get("eml"));
                remark.setText(user.get("remark"));
                visit.setText(user.get("visit"));
                tid2.setText(user.get("tmpid"));
            }
        });

        Insertview();
    }
    public void EditView(){
        name.setVisibility(View.VISIBLE);
        name.setText("");
        age.setVisibility(View.GONE);
        radioSexGroup.setVisibility(View.GONE);
        phno.setVisibility(View.VISIBLE);
        phno.setText("");
        address.setVisibility(View.VISIBLE);
        address.setText("");
        sbp.setVisibility(View.GONE);
        dbp.setVisibility(View.GONE);
        pulse.setVisibility(View.GONE);
        rbs.setVisibility(View.GONE);
        wt.setVisibility(View.GONE);
        ht.setVisibility(View.GONE);
        Email.setVisibility(View.VISIBLE);
        Email.setText("");
        remark.setVisibility(View.VISIBLE);
        remark.setText("");
        visit.setVisibility(View.VISIBLE);
        visit.setText("");
        clcdet.setVisibility(View.VISIBLE);
        clcdet.setText("");
        rvw.setVisibility(View.VISIBLE);
        tst.setVisibility(View.VISIBLE);
        et_revday.setVisibility(View.VISIBLE);
        et_tstday.setVisibility(View.VISIBLE);
        daygroup1.setVisibility(View.VISIBLE);
        daygroup2.setVisibility(View.VISIBLE);;
        b.setVisibility(View.VISIBLE);
        ulimag.setVisibility(View.VISIBLE);
        viewImage.setVisibility(View.VISIBLE);
        btninsert.setVisibility(View.GONE);
        btnupdate.setVisibility(View.VISIBLE);
        tid.setVisibility(View.VISIBLE);
        tid.setText("");
        tid2.setVisibility(View.VISIBLE);
        area.setVisibility(View.GONE);
        doc.setVisibility(View.GONE);
        //

    }
    public void Insertview(){  name.setVisibility(View.VISIBLE);
        age.setVisibility(View.VISIBLE);
        radioSexGroup.setVisibility(View.VISIBLE);
        phno.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        sbp.setVisibility(View.VISIBLE);
        dbp.setVisibility(View.VISIBLE);
        pulse.setVisibility(View.VISIBLE);
        rbs.setVisibility(View.VISIBLE);
        wt.setVisibility(View.VISIBLE);
        ht.setVisibility(View.VISIBLE);
        Email.setVisibility(View.VISIBLE);
        remark.setVisibility(View.VISIBLE);
        visit.setVisibility(View.VISIBLE);
        clcdet.setVisibility(View.GONE);
        rvw.setVisibility(View.GONE);
        tst.setVisibility(View.GONE);
        et_revday.setVisibility(View.GONE);
        et_revday.setText("");
        et_tstday.setVisibility(View.GONE);
        et_tstday.setText("");
        daygroup1.setVisibility(View.GONE);
        daygroup2.setVisibility(View.GONE);
        daygroup1.clearCheck();
        daygroup2.clearCheck();
        b.setVisibility(View.GONE);
        ulimag.setVisibility(View.GONE);
        viewImage.setVisibility(View.GONE);
        btninsert.setVisibility(View.VISIBLE);
        btnupdate.setVisibility(View.GONE);
        tid.setVisibility(View.GONE);
        tid2.setVisibility(View.GONE);
        area.setVisibility(View.VISIBLE);
        doc.setVisibility(View.VISIBLE);
        loadArea();

    }
    public void loadPatTkt() {
        db=new MydbAdapter_tkt(MakePresNw.this);
         List<String> tktPat = db.getAllTableName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, tktPat);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
    }
    public void loadArea() {

        db=new MydbAdapter_tkt(MakePresNw.this);

        ArrayList<HashMap<String, String>> details =db.GetUserDet();
        HashMap<String, String> user = details.get(0);
        String areas=user.get("area");
        String[] areatable=areas.split(",");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,areatable);
        area.setAdapter(adapter);
       // doc section

        String docs=user.get("doc");
        String[] doctable=docs.split(",");
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,doctable);
        doc.setAdapter(adapter1);

    }
    public void insDatalocal(final String nm, final String add, final String ph, final String eml, final String clncldet, final String Remarks, final Double ccrg,final String tempid,final String area,final String doc)
    {
        db.insertTestDetails(nm,ph,add,eml,clncldet,ccrg,"NIL","NIL","NIL",Remarks,tempid,area,doc);
        loadPatTkt() ;
        ClearText();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
               // Log.w("path of image from gallery", picturePath + "");
                viewImage.setImageBitmap(thumbnail);
            }
        }
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
        pDialog = new ProgressDialog(MakePresNw.this);
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

        if (area.getSelectedItemPosition() == 0)
        {

            Toast.makeText(this, "** Error:Select a Area. **", Toast.LENGTH_SHORT).show();
            area.requestFocus();
            return false;
        }
        if (doc.getSelectedItemPosition() == 0)
        {

            Toast.makeText(this, "** Error:Select a Doctor. **", Toast.LENGTH_SHORT).show();
            area.requestFocus();
            return false;
        }
        // pDialog.dismiss();
        return true;

    }
    private boolean validateInputs2() {
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
            Toast.makeText(getApplicationContext(),"Please choose Day/Week/Month/Year", Toast.LENGTH_SHORT).show();
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
            c.setTime(sdf.parse(dateInString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int daysss=days*dki;
        c.add(Calendar.DATE, daysss);
        String dt = sdf.format(c.getTime());
        return  dt;
    }
    public void callvolly(final String nm, final String add, final String ph, final String eml, final String clncldet, final String Remarks, final String ccrg,final String area,final String doc)
    {

       insDatalocal(nm,add,ph,eml,clncldet,Remarks,Double.parseDouble(ccrg),"0",area,doc);
       pDialog.dismiss();
    }
    public void onBackPressed() {}
}