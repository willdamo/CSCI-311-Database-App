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
    public boolean insert(String title, String year, String writer) {
        //stores information for database
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("year", year);
        values.put("writer", writer);
        return database.insert("contact", null, values) > 0;
    }

    //retrieves year from database
    public String getYear(String title) {
        String query = "select year from manga where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String year = cursor.getString(0);
        cursor.close();
        return year;
    }

    //retrieves writer from database
    public String getWriter(String title){
        String query = "select writer from manga where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String writer = cursor.getString(0);
        cursor.close();
        return writer;
    }

    //returns an array of titles from the database
    public String[] getTitles(){
        String query = "select title from manga";
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







