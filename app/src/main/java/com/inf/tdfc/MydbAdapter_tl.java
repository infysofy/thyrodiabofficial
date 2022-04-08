package com.inf.tdfc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class MydbAdapter_tl extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Dbtl.db";
    private static final String TABLE_Users = "TestDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_CODE = "code";
    private static final String KEY_DES = "description";
    private static final String KEY_RATE = "rate";
    private static final String KEY_B2B = "b2b";
    private static final String KEY_LAB = "lab";
    public MydbAdapter_tl(Context context)
    {
        super(context,DB_NAME, null, DB_VERSION);
       // Toast.makeText(context,"Ok1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db){

            String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_CODE + " TEXT,"
                    + KEY_DES + " TEXT,"
                    + KEY_RATE + " TEXT,"
                    + KEY_B2B + " TEXT,"
                    + KEY_LAB + " TEXT" + ")";
            db.execSQL(CREATE_TABLE);
        //Toast.makeText(getApplicationContext(),"Ok2", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        //onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertTestDetails(String id, String code, String des,String rate,String b2b,String lab){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ID, id);
        cValues.put(KEY_CODE, code);
        cValues.put(KEY_DES, des);
        cValues.put(KEY_RATE, rate);
        cValues.put(KEY_B2B,b2b);
        cValues.put(KEY_LAB,lab);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    // Get User Details

    public ArrayList<HashMap<String, String>> GetUsers(String labs){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users+" WHERE "+KEY_LAB+"='"+labs+"';" ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("code",cursor.getString(cursor.getColumnIndex(KEY_CODE)));
            user.put("des",cursor.getString(cursor.getColumnIndex(KEY_DES)));
            userList.add(user);
        }
        return  userList;
    }
    public ArrayList<String> getAllTabletests()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> allTableNames=new ArrayList<String>();
        String selectQuery = " SELECT "+ KEY_DES +" || "+" '(' "+" || "+ KEY_ID +" || "+"')'" +" AS ni FROM "+ TABLE_Users +" WHERE "+KEY_LAB+"='TTC'";
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
    public ArrayList<HashMap<String, String>> GetTestlistttc(String srch,String lab)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users + " WHERE "+KEY_LAB+"='"+lab+"' AND "+KEY_DES+" LIKE '%"+srch+"%'" ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("gid",cursor.getString(cursor.getColumnIndex(KEY_CODE)));
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_DES)));
            user.put("mrp",cursor.getString(cursor.getColumnIndex(KEY_RATE)));
            user.put("b2b",cursor.getString(cursor.getColumnIndex(KEY_B2B)));
            userList.add(user);
        }
        return  userList;
    }
    public ArrayList<String> getTestDet()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> allTableNames=new ArrayList<String>();
        String selectQuery = " SELECT "+ KEY_DES +" || "+" '-' "+" || "+KEY_CODE + " || "+'-'+KEY_RATE+" AS ni FROM "+ TABLE_Users;
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
/*
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
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
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
    public int UpdateUserDetails(String location, String designation, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_LOC, location);
        cVals.put(KEY_DESG, designation);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }*/
}
