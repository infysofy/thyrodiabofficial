package com.inf.tdfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.text.DecimalFormat;
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
    BaseFont bfBold;
    BaseFont bf;
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




    public void createPdf() throws DocumentException, IOException {
        initializeFonts();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/";// Environment.getExternalStorageDirectory().getPath() + "/Hello.pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(file + currentDateandTime + ".pdf"));
        document.open();
        PdfContentByte cb=writer.getDirectContent();
        generateLayout(document,cb);
        //createContent(cb,0,805,"Invoice | Clinical Interpretation",);
        //writer.open();
        document.newPage();
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
    private void generateLayout(Document doc, PdfContentByte cb) {



            cb.setLineWidth(1f);
            cb.rectangle(220, 800, 150, 15);
            createHeadings(cb, 230, 805, "Tax Invoice | Clinical Interpretation");

            createHeadings(cb, 260, 777, "MBODYMENT");
            createHeadings2(cb, 220, 765, " THYRODIAB CLINIC");
            createHeadings(cb, 200, 750, "10A Mohendra Bagchi Road,Bally,Howrah-711202");
            createHeadings(cb, 190, 742, "Email : thyrodiab@gmail.com | Web : www.thyrodiab.in");
            createHeadings(cb, 215, 734, "Contact No : +91 98309 88233 | 90380 03788");

            cb.rectangle(10, 730, 550, 55);
            createHeadings(cb, 15, 720, "PATIENT DETAILS");
            createHeadings(cb, 20, 710, "Name : ABHIRUP BHATTACHARYYA");
            createHeadings(cb, 20, 700, "Address : 18/B DR SAROJ NATH MUKHERJEE STREET UTTARPARA HOOGHLY-712258");
            createHeadings(cb, 20, 690, "Contact No. : 91 78906 16811");
            createHeadings(cb, 20, 680, "Email : ABCDEFGHIJ@GMAIL.COM");

            cb.moveTo(395, 675);
            cb.lineTo(395, 730);

            createHeadings(cb, 400, 720, "INVOICE DETAILS");
            createHeadings(cb, 405, 710, "Invoice No. : 214788");
            createHeadings(cb, 405, 700, "Date : 23/04/2022");
            createHeadings(cb, 405, 690, "C Area : GHUSURI");
            createHeadings(cb, 405, 680, "SP Code : SP08");
            cb.rectangle(10, 675, 550, 55);

            //Clinical Interpretation Section
            createHeadings(cb, 10, 665, "Clinical Interpretation:");
            createHeadings(cb, 10, 655, "" +
                    " BP:-->120/80\n" +
                    "Pulse:-->120\n" +
                    "Random Blood Sugar:-->154mg/dL\n" +
                    "Weight(Kg.):-->74\n" +
                    "Height(cm):-->65\n" +
                    "BMI:-->178.4(FATTY)");
            cb.rectangle(10, 645, 550, 30);

            //INVOICE SECTION
        cb.rectangle(10, 630, 550, 10);
/*
            // Invoice Detail box layout
            cb.rectangle(20,50,550,600);
            cb.moveTo(20,630);
            cb.lineTo(570,630);
            cb.moveTo(50,50);
            //cb.lineTo(50,650);
            //cb.moveTo(150,50);
            cb.lineTo(150,650);
            cb.moveTo(430,50);
            cb.lineTo(430,650);
            //cb.moveTo(500,50);
            //cb.lineTo(500,650);
            cb.stroke();

            // Invoice Detail box Text Headings
            createHeadings(cb,22,633,"#");
           // createHeadings(cb,52,633,"Item Number");
            createHeadings(cb,152,633,"Paticulars");
            createHeadings(cb,432,633,"Price");
           // createHeadings(cb,502,633,"Ext Price");

            //add the images
            Image companyLogo = Image.getInstance("images/olympics_logo.gif");
            companyLogo.setAbsolutePosition(25,700);
            companyLogo.scalePercent(25);
            doc.add(companyLogo);
*/


    }

    private void generateHeader(Document doc, PdfContentByte cb)  {

        try {

            createHeadings(cb,200,750,"Company Name");
            createHeadings(cb,200,735,"Address Line 1");
            createHeadings(cb,200,720,"Address Line 2");
            createHeadings(cb,200,705,"City, State - ZipCode");
            createHeadings(cb,200,690,"Country");

            createHeadings(cb,482,743,"ABC0001");
            createHeadings(cb,482,723,"123456");
            createHeadings(cb,482,703,"09/26/2012");

        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y)  {
        DecimalFormat df = new DecimalFormat("0.00");

        try {

            createContent(cb,48,y,String.valueOf(index+1),PdfContentByte.ALIGN_RIGHT);
            createContent(cb,52,y, "ITEM" + String.valueOf(index+1),PdfContentByte.ALIGN_LEFT);
            createContent(cb,152,y, "Product Description - SIZE " + String.valueOf(index+1),PdfContentByte.ALIGN_LEFT);

            double price = Double.valueOf(df.format(Math.random() * 10));
            double extPrice = price * (index+1) ;
            createContent(cb,498,y, df.format(price),PdfContentByte.ALIGN_RIGHT);
            createContent(cb,568,y, df.format(extPrice),PdfContentByte.ALIGN_RIGHT);

        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text){
        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }
    private void createHeadings2(PdfContentByte cb, float x, float y, String text){
        cb.beginText();
        cb.setFontAndSize(bfBold, 16);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }
    private void createContent(PdfContentByte cb, float x, float y, String text, int align){
        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned( align, text.trim(), x , y, 0);
        cb.endText();

    }
    private void initializeFonts(){
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}