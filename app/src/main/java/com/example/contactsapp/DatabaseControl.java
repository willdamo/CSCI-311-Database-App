package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    //adds, retrieve, and remove data from the database
    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    //adds data to the database
    public boolean insert(String name, String state) {
        //stores information for database
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("state", state);
        return database.insert("contact", null, values) > 0;
    }

    //retrieves info from database
    public String getState(String name) {
        String query = "select state from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String state = cursor.getString(0);
        cursor.close();
        return state;
    }

    public String[] getNames(){
        String query = "select name from contact";
        ArrayList<String> array = new ArrayList<String>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return array.toArray(new String[array.size()]);
    }
}







