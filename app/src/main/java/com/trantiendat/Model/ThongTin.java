package com.trantiendat.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThongTin implements Serializable {

    @SerializedName("ID_MonAn")
    @Expose
    private String iDMonAn;
    @SerializedName("ID_DiaDiem")
    @Expose
    private String iDDiaDiem;
    @SerializedName("Hinh_MonAn")
    @Expose
    private String hinhMonAn;
    @SerializedName("Ten_MonAn")
    @Expose
    private String tenMonAn;
    @SerializedName("Ten_DiaDiem")
    @Expose
    private String tenDiaDiem;
    @SerializedName("Gia")
    @Expose
    private String gia;
    @SerializedName("SoLuong")
    @Expose
    private String soLuong;

    public String getIDMonAn() {
        return iDMonAn;
    }

    public void setIDMonAn(String iDMonAn) {
        this.iDMonAn = iDMonAn;
    }

    public String getIDDiaDiem() {
        return iDDiaDiem;
    }

    public void setIDDiaDiem(String iDDiaDiem) {
        this.iDDiaDiem = iDDiaDiem;
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

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

}