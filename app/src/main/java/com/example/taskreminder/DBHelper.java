package com.example.taskreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
