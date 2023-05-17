package com.example.taskreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Taskdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
       DB.execSQL("create Table Taskdetails(name TEXT primary key, category TEXT, date TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Taskdetails");
    }

    public boolean insertUserData(String name, String category,String date, String time) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("category", category);
        contentValues.put("date", date);
        contentValues.put("time", time);

        long result = DB.insert("Taskdetails", null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TaskDetails", null);
        return cursor;
    }

    public String todaysDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date date = formatter.parse(dateInString);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String _date = simpleDateFormat.format(date);

        return _date;
    }

    public Cursor getTodaysData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TaskDetails ORDER BY date ASC", null);
        return cursor;
    }


    public Cursor getSchoolData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String n = "School";
        Cursor cursor = DB.rawQuery("Select * from TaskDetails WHERE category = ? ORDER BY date ASC", new String[]{n});
        return cursor;
    }

    public Cursor getSportData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String n = "Sports";
        Cursor cursor = DB.rawQuery("Select * from TaskDetails WHERE category = ? ORDER BY date ASC", new String[]{n});
        return cursor;
    }


    public Cursor getFamilyData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String n = "Family";
        Cursor cursor = DB.rawQuery("Select * from TaskDetails WHERE category = ? ORDER BY date ASC", new String[]{n});
        return cursor;
    }


    public Cursor getWorkData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String n = "Work";
        Cursor cursor = DB.rawQuery("Select * from TaskDetails WHERE category = ? ORDER BY date ASC", new String[]{n});
        return cursor;
    }
}
