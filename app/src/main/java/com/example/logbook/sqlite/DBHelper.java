package com.example.logbook.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="lb_url";
    public static final int DB_VERSION = 5;

    private static final String TABLE_NAME = "inputedurl";
    private static final String ID = "id";
    private static final String URL = "url";
    private final Context context;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql ="CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                URL + " TEXT NOT NULL) ;";
        sqLiteDatabase.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addURL(String imgUrl){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(URL, imgUrl);
        long result = database.insert(TABLE_NAME,null, contentValues);
        Toast.makeText(context,"Added Successfully !", Toast.LENGTH_SHORT).show();
    }
}

