package com.inf.tdfc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//volley


public class MydbAdapter_tkt extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DbTkt.db";
    //Ticket Details Section
    private static final String TABLE_Users = "TicketDetails";
    private static final String KEY_id = "id";
    private static final String KEY_nm = "nm";
    private static final String KEY_ad = "adr";
    private static final String KEY_ph = "ph";
    private static final String KEY_eml = "eml";
    private static final String KEY_clcdet = "clncdet";
    private static final String KEY_ccrg = "ccrg";
    private static final String KEY_remrk="remark";
    private static final String KEY_rvwdt = "rvwdt";
    private static final String KEY_tstdt = "tstdt";
    private static final String KEY_img = "img";
    private static final String KEY_stat = "Status";
    private static final String KEY_tempid = "tempid";
    private static final String KEY_area = "area";
    private static final String KEY_doc= "doc";
    private static final String KEY_spid= "spid";
    //End Ticket Details
    //User Detaills Section
    private static final String TABLE_Users1 = "UserDetails";
    private static final String KEY1_id = "id";
    private static final String KEY1_name = "name";
    private static final String KEY1_img = "img";
    private static final String KEY1_area = "area";
    private static final String KEY1_logstat = "logstat";
    private static final String KEY1_doc = "doc";
    int i;
    //End User Detaills
    public Context contex;
    public MydbAdapter_tkt(Context context)
    {

        super(context,DB_NAME, null, DB_VERSION);
        contex=context;
       // Toast.makeText(context,"Ok1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db){

            String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                    + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_nm +" TEXT,"
                    + KEY_ad + " TEXT,"
                    + KEY_ph + " TEXT,"
                    + KEY_eml + " TEXT,"
                    + KEY_clcdet + " TEXT,"
                    + KEY_ccrg + " DOUBLE,"
                    + KEY_tstdt + " TEXT,"
                    + KEY_rvwdt + " TEXT,"
                    + KEY_img + " TEXT,"
                    + KEY_remrk + " TEXT,"
                    + KEY_tempid + " TEXT,"
                    + KEY_area + " TEXT,"
                    + KEY_doc + " TEXT,"
                    + KEY_stat + " TEXT,"
                    + KEY_spid + " TEXT"
                    + ")";

        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_Users1 + "("
                + KEY1_id + " TEXT,"
                + KEY1_name +" TEXT,"
                + KEY1_img + " TEXT,"
                + KEY1_area + " TEXT,"
                + KEY1_doc + " TEXT,"
                + KEY1_logstat + " TEXT" + ")";
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE1);
        //Toast.makeText(getApplicationContext(),"Ok2", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       //  Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users1);
         //Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertTestDetails(String name, String ph,String add, String eml,String clcdet,Double ccrg,String tstdt,String rvdt,String img,String remarks,String tmpid,String area,String doc)
    {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_nm,name);
        cValues.put(KEY_ph,ph);
        cValues.put(KEY_ad,add);
        cValues.put(KEY_eml,eml);
        cValues.put(KEY_clcdet,clcdet);
        cValues.put(KEY_ccrg,ccrg);
        cValues.put(KEY_tstdt,tstdt);
        cValues.put(KEY_rvwdt,rvdt);
        cValues.put(KEY_img,img);
        cValues.put(KEY_remrk,remarks);
        cValues.put(KEY_stat,"0");
        cValues.put(KEY_tempid,tmpid);
        cValues.put(KEY_area,area);
        cValues.put(KEY_doc,doc);
        cValues.put(KEY_spid,GetSpCode());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        //Toast.makeText(contex,"Data Insert Succesfully", Toast.LENGTH_SHORT).show();
        db.close();
    }
    void insertuserDetails(String id, String name,String img, String area,String logstat,String doc)
    {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY1_id,id);
        cValues.put(KEY1_name,name);
        cValues.put(KEY1_img,img);
        cValues.put(KEY1_area,area);
        cValues.put(KEY1_logstat,logstat);
        cValues.put(KEY1_doc,doc);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users1,null, cValues);
        //Toast.makeText(contex,"Data Insert Succesfully", Toast.LENGTH_SHORT).show();
        db.close();
    }
    // Get User Details

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTicket()    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("pid",cursor.getString(cursor.getColumnIndex(KEY_id)));
            user.put("pnm",cursor.getString(cursor.getColumnIndex(KEY_nm)));
           // user.put("pph",cursor.getString(cursor.getColumnIndex(KEY_ph)));
           // user.put("oid",cursor.getString(cursor.getColumnIndex(KEY_clcdet)));
           // user.put("visit",cursor.getString(cursor.getColumnIndex(KEY_ccrg)));
            userList.add(user);
        }
        return  userList;
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTicketsDirect(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query =  "SELECT * FROM "+ TABLE_Users +" WHERE "+KEY_stat+"=1" ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_id)));
            user.put("nm",cursor.getString(cursor.getColumnIndex(KEY_nm)));
            user.put("ad",cursor.getString(cursor.getColumnIndex(KEY_ad)));
            user.put("ph", cursor.getString(cursor.getColumnIndex(KEY_ph)));
            user.put("eml", cursor.getString(cursor.getColumnIndex(KEY_eml)));
            user.put("clcdet", cursor.getString(cursor.getColumnIndex(KEY_clcdet)));
            user.put("ccrg", cursor.getString(cursor.getColumnIndex(KEY_ccrg)));
            user.put("tstdt", cursor.getString(cursor.getColumnIndex(KEY_tstdt)));
            user.put("rvwdt", cursor.getString(cursor.getColumnIndex(KEY_rvwdt)));
            user.put("img", cursor.getString(cursor.getColumnIndex(KEY_img)));
            user.put("remrk", cursor.getString(cursor.getColumnIndex(KEY_remrk)));
            user.put("tempid", cursor.getString(cursor.getColumnIndex(KEY_tempid)));
            user.put("stst",cursor.getString(cursor.getColumnIndex(KEY_stat)));
            user.put("area",cursor.getString(cursor.getColumnIndex(KEY_area)));
            user.put("doc",cursor.getString(cursor.getColumnIndex(KEY_doc)));
            user.put("spid",cursor.getString(cursor.getColumnIndex(KEY_spid)));

            userList.add(user);
        }
        return  userList;
    }
    @SuppressLint("Range")
    public  String GetSpCode() {
        String r = "";
        String ids="";
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_Users1;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            while (!cursor.isAfterLast()) {
                ids = cursor.getString(cursor.getColumnIndex(KEY1_id));
              /*  String adress = cursor.getString(cursor.getColumnIndex(KEY_ad));
                String ph = cursor.getString(cursor.getColumnIndex(KEY_ph));
                String eml = cursor.getString(cursor.getColumnIndex(KEY_eml));
                String clcdet = cursor.getString(cursor.getColumnIndex(KEY_clcdet));
                String ccrg = cursor.getString(cursor.getColumnIndex(KEY_ccrg));
                String tstdt = cursor.getString(cursor.getColumnIndex(KEY_tstdt));
                String rvwdt = cursor.getString(cursor.getColumnIndex(KEY_rvwdt));
                String img = cursor.getString(cursor.getColumnIndex(KEY_img));
                String remrk = cursor.getString(cursor.getColumnIndex(KEY_remrk));
                String tempid = cursor.getString(cursor.getColumnIndex(KEY_tempid));
                String stat = cursor.getString(cursor.getColumnIndex(KEY_stat));*/


                cursor.moveToNext();
            }


        }
        return ids;
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTicketId1(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users+" WHERE "+KEY_id +"='"+Id+"'";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("nm",cursor.getString(cursor.getColumnIndex(KEY_nm)));
            user.put("ph",cursor.getString(cursor.getColumnIndex(KEY_ph)));
            user.put("cdet",cursor.getString(cursor.getColumnIndex(KEY_clcdet)));
            user.put("add",cursor.getString(cursor.getColumnIndex(KEY_ad)));
            user.put("eml",cursor.getString(cursor.getColumnIndex(KEY_eml)));
            user.put("remark",cursor.getString(cursor.getColumnIndex(KEY_remrk)));
            user.put("visit",cursor.getString(cursor.getColumnIndex(KEY_ccrg)));
            user.put("tmpid",cursor.getString(cursor.getColumnIndex(KEY_tempid)));
            user.put("doc",cursor.getString(cursor.getColumnIndex(KEY_doc)));
            userList.add(user);
        }
        return  userList;
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUserDet(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users1;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("nm",cursor.getString(cursor.getColumnIndex(KEY1_id)));
            user.put("ph",cursor.getString(cursor.getColumnIndex(KEY1_name)));
            user.put("area",cursor.getString(cursor.getColumnIndex(KEY1_area)));
            user.put("add",cursor.getString(cursor.getColumnIndex(KEY1_img)));
            user.put("eml",cursor.getString(cursor.getColumnIndex(KEY1_logstat)));
            user.put("doc",cursor.getString(cursor.getColumnIndex(KEY1_doc)));
            userList.add(user);
        }
        return  userList;
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetTicketId(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users+" WHERE "+KEY_id +"='"+Id+"'"+" AND "+KEY_stat+"=0" ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("nm",cursor.getString(cursor.getColumnIndex(KEY_nm)));
            user.put("ph",cursor.getString(cursor.getColumnIndex(KEY_ph)));
            user.put("cdet",cursor.getString(cursor.getColumnIndex(KEY_clcdet)));
            user.put("add",cursor.getString(cursor.getColumnIndex(KEY_ad)));
            user.put("eml",cursor.getString(cursor.getColumnIndex(KEY_eml)));
            user.put("remark",cursor.getString(cursor.getColumnIndex(KEY_remrk)));
            user.put("visit",cursor.getString(cursor.getColumnIndex(KEY_ccrg)));
            user.put("tmpid",cursor.getString(cursor.getColumnIndex(KEY_tempid)));
            userList.add(user);
        }
        return  userList;
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetImage(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT "+KEY_img+" FROM "+ TABLE_Users+" WHERE "+KEY_id +"='"+Id+"'";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            HashMap<String,String> user = new HashMap<>();
            user.put("img",cursor.getString(cursor.getColumnIndex(KEY_img)));
        }
        return  userList;
    }
    public List<String> getPatientTicketList(){
        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = " SELECT "+ KEY_nm + " FROM "+ TABLE_Users;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    @SuppressLint("Range")
    public ArrayList<String> getAllTableName(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> allTableNames=new ArrayList<String>();
        String selectQuery = " SELECT "+ KEY_nm +" || "+" '-' "+" || "+KEY_id +" AS ni FROM "+ TABLE_Users +" WHERE "+KEY_stat+"=0";
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                allTableNames.add(cursor.getString(cursor.getColumnIndex("ni")));
            }
        }
        cursor.close();
        db.close();
        return allTableNames;
    }
    // Get User Details based on userid
    /*   public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        return  userList;
    }
    // Delete User Details*/
    public void DeleteUser(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_id+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    public  boolean checkUser() {
        boolean val=true;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> allTableNames=new ArrayList<String>();
        String selectQuery = "SELECT * FROM "+ TABLE_Users1;
        Cursor cursor=db.rawQuery(selectQuery,null);
        i=cursor.getCount();
        if (i<1)
        {
            val= false;
            //return val;
        }

        return val;
    }
    // Update User Details
    public int UpdateTicketDetails(String nm,String add,String ph,String eml,String clcdt,String ccr,String remark,String rvwdt, String tstdt,String img, String id){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cVals = new ContentValues();
    cVals.put(KEY_nm, nm);
    cVals.put(KEY_ad, add);
    cVals.put(KEY_ph, ph);
    cVals.put(KEY_eml, eml);
    cVals.put(KEY_clcdet, clcdt);
    cVals.put(KEY_ccrg, ccr);
    cVals.put(KEY_remrk, remark);
    cVals.put(KEY_rvwdt, rvwdt);
    cVals.put(KEY_tstdt, tstdt);
    cVals.put(KEY_stat, "1");
    int count = db.update(TABLE_Users, cVals, KEY_id+" = ?",new String[]{String.valueOf(id)});
    return  count;
}
    public int UpdateImage(String img, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_img, img);
        int count = db.update(TABLE_Users, cVals, KEY_id+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}
