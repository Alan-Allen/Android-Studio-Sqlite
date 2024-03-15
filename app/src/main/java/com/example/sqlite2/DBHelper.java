package com.example.sqlite2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    String TableName;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, name, factory, version);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + TableName + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "user TEXT, " +
                "password TEXT" +
                ");";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + TableName;
        db.execSQL(SQL);
    }

    public void chickTable(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "user TEXT," +
                        "password TEXT" +
                        ");");
            cursor.close();
        }
    }

    public void insert(String name, String user, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("user", user);
        values.put("password", password);
        db.insert(TableName, null, values);
    }

    public void modify(String id, String name, String user, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("user", user);
        values.put("password", password);
        db.update(TableName, values, "_id = " + id, null);
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TableName, "_id = " + id, null);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TableName);
    }

    public ArrayList<HashMap<String, String>> searchAll() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM " + TableName, null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while(c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();
            String id = c.getString(0);
            String name = c.getString(1);
            String user = c.getString(2);
            String password = c.getString(3);

            hashMap.put("id", id);
            hashMap.put("name", name);
            hashMap.put("user", user);
            hashMap.put("password", password);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public ArrayList<HashMap<String, String>> search(String ID) {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM " + TableName
                + " WHERE _id = " + "'" + ID + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while(c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();
            String id = c.getString(0);
            String name = c.getString(1);
            String user = c.getString(2);
            String password = c.getString(3);

            hashMap.put("id", id);
            hashMap.put("name", name);
            hashMap.put("user", user);
            hashMap.put("password", password);
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
