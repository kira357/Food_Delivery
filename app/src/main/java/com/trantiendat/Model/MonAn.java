package com.trantiendat.Model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MonAn implements Serializable {

    @SerializedName("ID_MonAn")
    @Expose
    private String iDMonAn;
    @SerializedName("Ten_MonAn")
    @Expose
    private String tenMonAn;
    @SerializedName("Loai_MonAn")
    @Expose
    private String loaiMonAn;
    @SerializedName("Gia_MonAn")
    @Expose
    private String giaMonAn;
    @SerializedName("Hinh_MonAn")
    @Expose
    private String hinhMonAn;

    public String getIDMonAn() {
        return iDMonAn;
    }

    public void setIDMonAn(String iDMonAn) {
        this.iDMonAn = iDMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getLoaiMonAn() {
        return loaiMonAn;
    }

    public void setLoaiMonAn(String loaiMonAn) {
        this.loaiMonAn = loaiMonAn;
    }

    public String getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(String giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public String getHinhMonAn() {
        return hinhMonAn;
    }

    public void setHinhMonAn(String hinhMonAn) {
        this.hinhMonAn = hinhMonAn;
    }

}