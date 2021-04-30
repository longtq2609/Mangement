package com.example.quanlycanhan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.quanlycanhan.SQLite.Database;
import com.example.quanlycanhan.model.GiaoDich;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GiaoDichDAO {
    Database database;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SQLiteDatabase sqLiteDatabase;

    public GiaoDichDAO(Context context) {
        database = new Database(context);

    }

    public ArrayList<GiaoDich> getGD(String sql, String... a) {
        ArrayList<GiaoDich> list = new ArrayList<>();
        sqLiteDatabase = database.getReadableDatabase();
        Cursor cs = sqLiteDatabase.rawQuery(sql, a);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            try {
                int ma = Integer.parseInt(cs.getString(0));
                String mota = cs.getString(1);
                String ngay = cs.getString(2);
                int tien = Integer.parseInt(cs.getString(3));
                int maK = Integer.parseInt(cs.getString(4));
                GiaoDich gd = new GiaoDich(ma, mota, simpleDateFormat.parse(ngay), tien, maK);
                list.add(gd);
                cs.moveToNext();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        cs.close();
        return list;
    }


    public ArrayList<GiaoDich> getAll() {
        String sql = "SELECT * FROM giaodich";
        return getGD(sql);
    }


    public ArrayList<GiaoDich> getGDtheoTC(int loaiKhoan) {
        String sql = "SELECT * FROM giaodich as gd INNER JOIN thuchi as tc ON gd.maTC = tc.maTC WHERE tc.loaiTC=?";
        ArrayList<GiaoDich> list = getGD(sql, String.valueOf(loaiKhoan));
        return list;
    }

    public boolean addGD(GiaoDich gd) {
        sqLiteDatabase = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenGD", gd.getTenGD());
        contentValues.put("ngayGD", simpleDateFormat.format(gd.getNgayGD()));
        contentValues.put("tienGD", gd.getTienGD());
        contentValues.put("maTC", gd.getMaTC());
        long r = sqLiteDatabase.insert("giaodich", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }


    public boolean editGD(GiaoDich gd) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenGD", gd.getTenGD());
        contentValues.put("ngayGD", simpleDateFormat.format(gd.getNgayGD()));
        contentValues.put("tienGD", gd.getTienGD());
        contentValues.put("maTC", gd.getMaTC());
        int r = db.update("giaodich", contentValues, "maGD=?", new String[]{String.valueOf(gd.getMaGD())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean deleteGD(GiaoDich gd) {
        SQLiteDatabase db = database.getWritableDatabase();
        int r = db.delete("giaodich", "maGD=?", new String[]{String.valueOf(gd.getMaGD())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
}
