package com.inf.tdfc;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Uploadpresimg extends AppCompatActivity
{
ProgressDialog pDialog;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    Uri fileUri=null;
    String oid,oids;
    final int CROP_PIC = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpresimg);
        mImageView=(ImageView)findViewById(R.id.imageView2);
        Bundle bundle = getIntent().getExtras();
        oid = bundle.getString("Id");
       //////// oids = bundle.getString("oId");

         captureImage();
    }

    private void captureImage() {
       // Intent intent = new Intent(Intent.ACTION_PICK,
                //android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            // File   fileTemp = ImageUtils.getOutputMediaFile();
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
           // if (fileTemp != null) {
           // fileUri = Uri.fromFile(fileTemp);
            intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE,fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            //}
            // else {
            // Toast.makeText(this, "Error to create image file", Toast.LENGTH_LONG).show();
            // }
        }

        else {
            Toast.makeText(this, "Error on Camera", Toast.LENGTH_LONG).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            // File   fileTemp = ImageUtils.getOutputMediaFile();
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // if (fileTemp != null) {
            // fileUri = Uri.fromFile(fileTemp);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            //}
            // else {
            // Toast.makeText(this, "Error to create image file", Toast.LENGTH_LONG).show();
            // }
        }

        else {
            Toast.makeText(this, "Error on Camera", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK)//requestCode == REQUEST_IMAGE_CAPTURE &&
        {

           try {
                 displayLoader();
               mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
               mImageView.setImageBitmap(mImageBitmap);
              Bitmap  bitmap = getResizedBitmap(((BitmapDrawable) mImageView.getDrawable()).getBitmap(),768,1024);//((BitmapDrawable) mImageView.getDrawable()).getBitmap();
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG,30, stream);
               byte[] byteArray = stream.toByteArray();
               //imageHolder.setImageBitmap(bitmap);
               String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
               UpdateImg(encodedImage,oid);

            } catch (IOException e) {
                e.printStackTrace();
               // pDialog.dismiss();
            }

        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    public void UpdateImg(String img,String id){
        displayLoader();
        Button ub=(Button)findViewById(R.id.uploadbtn);
        MydbAdapter_tkt db=new MydbAdapter_tkt(this);
        db.UpdateImage(img,id);
        Toast.makeText(getApplicationContext(),"Image upload succesfully", Toast.LENGTH_LONG).show();
        MakePresNw.b.setVisibility(View.GONE);
        MakePresNw.ulimag.setVisibility(View.GONE);
        //ub.setVisibility(View.GONE);
        pDialog.dismiss();
        finish();

    }
    public void callvolly(final String name, final String id) {
        RequestQueue myreqque = Volley.newRequestQueue(this);
        String url = "http://api.thyrodiab.in/androidapp/services.php?action=upldFilesss";
        StringRequest mystringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jd = new JSONObject(response);

                    Toast.makeText(getApplicationContext(), jd.getString("message"), Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR :2", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();

                myData.put("id", id);
                myData.put("name", name);
                return myData;
            }

        };
        myreqque.add(mystringrequest);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Uploadpresimg.this);
        pDialog.setMessage("Uploading Photo.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    private void setResultOk(Uri imagePath) {
        Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
