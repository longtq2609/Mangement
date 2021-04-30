package com.example.quanlycanhan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlycanhan.SQLite.Database;
import com.example.quanlycanhan.model.ThuChi;

import java.util.ArrayList;

public class ThuChiDAO {
    Database database;
    SQLiteDatabase sqLiteDatabase;

    public ThuChiDAO(Context context) {
        database = new Database(context);
        sqLiteDatabase = database.getReadableDatabase();
    }

    public ArrayList<ThuChi> getTC(String sql, String... a) {
        ArrayList<ThuChi> list = new ArrayList<>();
        Cursor cs = sqLiteDatabase.rawQuery(sql, a);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int maTc = Integer.parseInt(cs.getString(0));
            String tenTc = cs.getString(1);
            int loaiTc = Integer.parseInt(cs.getString(2));

            ThuChi tc = new ThuChi(maTc, tenTc, loaiTc);
            list.add(tc);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }


    public boolean addTC(ThuChi tc) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTC", tc.getTenTC());
        contentValues.put("loaiTC", tc.isloaiTC());
        long r = db.insert("thuchi", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean deleteTC(ThuChi tc) {
        SQLiteDatabase db = database.getWritableDatabase();
        int r = db.delete("thuchi", "maTC=?", new String[]{String.valueOf(tc.getMaTC())});
        int s = db.delete("giaodich", "maTC=?", new String[]{String.valueOf(tc.getMaTC())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean editTC(ThuChi tc) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTC", tc.getTenTC());
        contentValues.put("loaiTC", tc.isloaiTC());
        int r = db.update("thuchi", contentValues, "maTC=?", new String[]{String.valueOf(tc.getMaTC())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public ArrayList<ThuChi> getAll() {
        String sql = "SELECT * FROM thuchi";
        return getTC(sql);
    }

    public ArrayList<ThuChi> getThuChi(int loaiKhoan) {
        String sql = "SELECT * FROM thuchi WHERE loaiTC=?";
        ArrayList<ThuChi> list = getTC(sql, String.valueOf(loaiKhoan));
        return list;
    }

    public String getTen(int maKhoan) {
        String tenTC = " ";

        String sql = "SELECT * FROM thuchi WHERE maTC=?";
        ArrayList<ThuChi> list = getTC(sql, String.valueOf(maKhoan));
        return list.get(0).getTenTC();
    }
}