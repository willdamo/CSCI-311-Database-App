package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseControl {

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

    public boolean insert(String name, String state) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("state", state);
        return database.insert("contact", null, values) > 0;
    }

    public String getState(String name) {
        String query = "select state from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String state = cursor.getString(0);
        cursor.close();
        return state;
    }

}







