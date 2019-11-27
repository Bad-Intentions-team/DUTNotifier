package com.example.dutnotifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dutnotifier.model.modelNoti;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Noti_Manager";

    // Tên bảng: Note.
    private static final String TABLE_NOTI = "Notification";

    private static final String COLUMN_NOTI_ID ="Noti_Id";
    private static final String COLUMN_NOTI_TITLE ="Noti_Title";
    private static final String COLUMN_NOTI_CONTENT = "Noti_Content";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_NOTI + "("
                + COLUMN_NOTI_ID + " INTEGER PRIMARY KEY," + COLUMN_NOTI_TITLE + " TEXT,"
                + COLUMN_NOTI_CONTENT + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTI);
        // Và tạo lại.
        onCreate(db);
    }

    public void addNoti(modelNoti noti) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTI_TITLE, noti.getTitle());
        values.put(COLUMN_NOTI_CONTENT, noti.getContent());
        // Crèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NOTI, null, values);
        // Đóng kết nối database.
        db.close();
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTI);
        onCreate(db);
    }

    public int getCountNoti() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        // return count
        return cursor.getCount();
    }

    public ArrayList<modelNoti> getAllNotis() {
        ArrayList<modelNoti> notiList = new ArrayList<modelNoti>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                modelNoti noti = new modelNoti();
                noti.setId(Integer.parseInt(cursor.getString(0)));
                noti.setTitle(cursor.getString(1));
                noti.setContent(cursor.getString(2));
                // Thêm vào danh sách.
                notiList.add(noti);
            } while (cursor.moveToNext());
        }

        // return note list
        return notiList;
    }
}
