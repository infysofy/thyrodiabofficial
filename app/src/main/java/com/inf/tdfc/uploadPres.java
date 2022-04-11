package com.inf.tdfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class uploadPres extends AppCompatActivity {
    private Bitmap photo;
    Button nextimg, upldimg;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 1888;

    ImageView imageView;
    ArrayList<Uri> uries = new ArrayList<Uri>();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Uri fileUri = null;
    int j;
    public int i = 0;
    private String curr_photopath;
    Uri imageuri;
    private BaseFont bfBold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pres);
        imageView = (ImageView) findViewById(R.id.imagesView);
        nextimg = (Button) findViewById(R.id.nxt_img);
        upldimg = (Button) findViewById(R.id.upld_pdf);
        uries.clear();

            StartCapture();


        nextimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i += 1;

                    StartCapture();

            }
        });
        upldimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    createPdf();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        {

        }
    }

    public void Checkpath() {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pathDir = baseDir + "/Android/data/com.inf.tdfc/";
        File f = new File(baseDir);

        if (f.exists()) {
            Log.d("Application", "The file  exists!");
        } else {
            Log.d("Application", "The file no longer exists!");
        }
    }


    public void cpdf() throws FileNotFoundException, DocumentException {
        PdfDocument document = new PdfDocument();
        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";
        // File files = new File(Environment.getExternalStorageDirectory(), "GFG23.pdf");

        PdfWriter.getInstance(document, new FileOutputStream(file + "hlo.pdf"));
        document.open();
        Paragraph p = new Paragraph("Hello PDF");
        document.add(p);
        document.close();
    }

    public void createPdf() throws DocumentException, IOException {


        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";// Environment.getExternalStorageDirectory().getPath() + "/Hello.pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(file + currentDateandTime + ".pdf"));
        document.open();

        //1st page logo section

        PdfContentByte cb = writer.getDirectContent();
        //initialize fonts for text printing
        initializeFonts();

        //the company logo is stored in the assets which is read only
        //get the logo and print on the document
      /*  AssetManager am = this.getApplicationContext().getAssets();
        InputStream inputStream = am.open("mipmap/logo.png");//getAssets().open("logo.png");
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image companyLogo = Image.getInstance(stream.toByteArray());
        companyLogo.setAbsolutePosition(25,700);
        companyLogo.scalePercent(25);
        document.add(companyLogo);*/
        //cb.rectangle(420,700,150,60);
        //cb.moveTo(420,720);
        //cb.lineTo(570,720);
        //cb.moveTo(420,740);
        //cb.lineTo(570,740);
        //cb.moveTo(480,700);
        //cb.lineTo(480,760);
        //cb.stroke();
        cb.rectangle(210,820,135,15);
        cb.stroke();
        createHeadings(cb,222,825,"Invoice | Clinical Interpretation");
        createHeadings(cb,250,810,"MBODYMENT");
        createHeadings1(cb,200,795,"THYRODIAB CLINIC");
        createHeadings(cb,150,780,"Address:10A, Mohendra Bagchi Road,Howrah-711202");
        createHeadings(cb,150,765,"Email:thyrodiab@gmail.com  Website: www.thyrodiab.in");
        createHeadings(cb,150,750,"Contact No:98309 88233 | 90380 03788");
        //createHeadings(cb,220,50,"MBODYMENT");
        //patient details
        cb.rectangle(20,670,550,75);
        cb.stroke();

        createHeadings(cb,25,735,"Patient Details");
        createHeadings(cb,30,720,"Name : ABHIRUP BHATTACHARYYA");
        createHeadings(cb,30,705,"Address : 18/B DR SAROJ NATH MUKHERJEE STREET UTTARPARA HOOGHLY-712258 ");
        createHeadings(cb,30,690,"Contact No : +91 7890616811 ");
        createHeadings(cb,30,675,"Email : doctofdffgfds@gmail.com ");
        //invoice details
        cb.moveTo(445,745);
        cb.lineTo(445,670);

        cb.stroke();
        createHeadings(cb,450,735,"Invoice Details");
        createHeadings(cb,455,720,"Invoice No : 214789");
        createHeadings(cb,455,705,"Date : 22/04/2022");
        createHeadings(cb,455,690,"C Area : GHUSURI");
        createHeadings(cb,455,675,"SP Code : SP04");
        document.newPage();

        //writer.open();
        for (j = 0; j <= i; j++) {
            String imageIpath = uries.get(j).toString();
            Image image1 = Image.getInstance(imageIpath);
            image1.setAbsolutePosition(10f, 10f);
            image1.scaleAbsolute(600, 800);
            document.newPage();
            document.add(image1);
        }
        //writer.close();
        document.close();
        Log.d("Application", "The file  saved!");
    }
    private void createHeadings(PdfContentByte cb, float x, float y, String text){

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }
    private void createHeadings1(PdfContentByte cb, float x, float y, String text){

        cb.beginText();
        cb.setFontAndSize(bfBold, 14);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }
    private void initializeFonts(){


        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void StartCapture()  {
        String file_name=getFileName();
        File storage_directory=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imgFile= null;
        try {
            imgFile = File.createTempFile(file_name,".jpg",storage_directory);
            // curr_photopath=imgFile.getAbsolutePath();
            imageuri= FileProvider.getUriForFile(uploadPres.this,"com.inf.tdfc.fileprovider",imgFile);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

           // Bitmap photo= BitmapFactory.decodeFile(curr_photopath);
            //Uri tt = data.getData();
            //photo = (Bitmap) data.getExtras().get("data");


             CropImage.activity(imageuri).start(uploadPres.this);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                uries.add(result.getUri());
                imageView.setImageURI(uries.get(i));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }


    }
    public  String getFileName(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }



}