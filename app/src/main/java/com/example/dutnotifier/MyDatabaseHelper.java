package com.example.dutnotifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dutnotifier.model.ModelNoti;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Noti_Manager";
    private static final String TABLE_NOTI = "Notification";
    private static final String COLUMN_NOTI_ID ="Noti_Id";
    private static final String COLUMN_NOTI_TITLE ="Noti_Title";
    private static final String COLUMN_NOTI_CONTENT = "Noti_Content";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NOTI + "("
                + COLUMN_NOTI_ID + " INTEGER PRIMARY KEY," + COLUMN_NOTI_TITLE + " TEXT,"
                + COLUMN_NOTI_CONTENT + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTI);
        onCreate(db);
    }

    public void addNoti(ModelNoti noti) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTI_TITLE, noti.getTitle());
        values.put(COLUMN_NOTI_CONTENT, noti.getContent());
        db.insert(TABLE_NOTI, null, values);
        db.close();
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTI);
        onCreate(db);
    }

    public ArrayList<ModelNoti> getAllNotis() {
        ArrayList<ModelNoti> listNoti = new ArrayList<ModelNoti>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ModelNoti noti = new ModelNoti();
                noti.setId(Integer.parseInt(cursor.getString(0)));
                noti.setTitle(cursor.getString(1));
                noti.setContent(cursor.getString(2));
                listNoti.add(noti);
            } while (cursor.moveToNext());
        }
        return listNoti;
    }
    public ArrayList<ModelNoti> getSearch(String s){
        String inputClass = s.toLowerCase();
        ArrayList<ModelNoti> listNoti = new ArrayList<ModelNoti>();
        String script = "select * from " + TABLE_NOTI + " WHERE " + COLUMN_NOTI_TITLE + " LIKE '%" + inputClass +"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script,null);
        while(cursor.moveToNext()){
            ModelNoti noti = new ModelNoti();
            noti.setId(cursor.getInt(0));
            noti.setTitle(cursor.getString(1));
            noti.setContent(cursor.getString(2));
            listNoti.add(noti);
        }
        return listNoti;
    }
}
