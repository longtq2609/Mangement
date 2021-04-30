package com.example.quanlycanhan.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ThuChi implements Serializable {


    private int maTC;
    private String tenTC;
    private int loaiTC;

    public ThuChi() {
    }


    public int isloaiTC() {
        return loaiTC;
    }

    public ThuChi(int maTC, String tenTC, int loaiTC) {
        this.maTC = maTC;
        this.tenTC = tenTC;
        this.loaiTC = loaiTC;
    }

    public int getMaTC() {
        return maTC;
    }

    public void setMaTC(int maTC) {
        this.maTC = maTC;
    }

    public String getTenTC() {
        return tenTC;
    }

    public void setTenTC(String tenTC) {
        this.tenTC = tenTC;
    }


    public void setLoaiTC(int loaiTC) {
        this.loaiTC = loaiTC;
    }

    @NonNull
    @Override
    public String toString() {
        return tenTC;
    }
}
