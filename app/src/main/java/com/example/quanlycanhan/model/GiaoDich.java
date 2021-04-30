package com.example.quanlycanhan.model;

import java.io.Serializable;
import java.util.Date;

public class GiaoDich implements Serializable {


    private int maGD;
    private String tenGD;
    private Date ngayGD;
    private int tienGD;
    private int maTC;

    public GiaoDich() {
    }

    public GiaoDich(int maGD, String tenGD, Date ngayGD, int tienGD, int maTC) {
        this.maGD = maGD;
        this.tenGD = tenGD;
        this.ngayGD = ngayGD;
        this.tienGD = tienGD;
        this.maTC = maTC;
    }

    public int getMaGD() {
        return maGD;
    }

    public void setMaGD(int maGD) {
        this.maGD = maGD;
    }

    public String getTenGD() {
        return tenGD;
    }


    public Date getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(Date ngayGD) {
        this.ngayGD = ngayGD;
    }

    public int getTienGD() {
        return tienGD;
    }

    public void setTienGD(int tienGD) {
        this.tienGD = tienGD;
    }

    public int getMaTC() {
        return maTC;
    }

    public void setMaTC(int maTC) {
        this.maTC = maTC;
    }


}
