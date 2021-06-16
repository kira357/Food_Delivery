package com.trantiendat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class GioHang implements Serializable {

    @SerializedName("ID_MonAn")
    @Expose
    private String iDMonAn;
    @SerializedName("Hinh_MonAn")
    @Expose
    private String hinhMonAn;
    @SerializedName("Ten_MonAn")
    @Expose
    private String tenMonAn;
    @SerializedName("DiaChi_DiaDiem")
    @Expose
    private String diaChiDiaDiem;
    @SerializedName("Gia_MonAn")
    @Expose
    private String giaMonAn;
    @SerializedName("SoLuong")
    @Expose
    private String soLuong;

    public GioHang(String iDMonAn, String hinhMonAn, String tenMonAn, String diaChiDiaDiem, String giaMonAn, String soLuong) {
        this.iDMonAn = iDMonAn;
        this.hinhMonAn = hinhMonAn;
        this.tenMonAn = tenMonAn;
        this.diaChiDiaDiem = diaChiDiaDiem;
        this.giaMonAn = giaMonAn;
        this.soLuong = soLuong;
    }

    public String getIDMonAn() {
        return iDMonAn;
    }

    public void setIDMonAn(String iDMonAn) {
        this.iDMonAn = iDMonAn;
    }

    public String getHinhMonAn() {
        return hinhMonAn;
    }

    public void setHinhMonAn(String hinhMonAn) {
        this.hinhMonAn = hinhMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getDiaChiDiaDiem() {
        return diaChiDiaDiem;
    }

    public void setDiaChiDiaDiem(String diaChiDiaDiem) {
        this.diaChiDiaDiem = diaChiDiaDiem;
    }

    public String getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(String giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

}