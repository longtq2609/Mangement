package com.example.quanlycanhan.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database  extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "QUANLYCHITIEU", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE thuchi( maTC integer PRIMARY KEY AUTOINCREMENT, tenTC text, loaiTC integer)";
        db.execSQL(sql);

        sql = "CREATE TABLE giaodich(maGD integer PRIMARY KEY AUTOINCREMENT, tenGD text, ngayGD date, tienGD integer, maTC integer REFERENCES thuchi(maKhoan))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS thuchi");
        db.execSQL("DROP TABLE IF EXISTS giaodich");
        onCreate(db);
    }
}

