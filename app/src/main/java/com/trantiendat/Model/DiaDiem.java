package com.trantiendat.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DiaDiem implements Serializable {

    @SerializedName("ID_DiaDiem")
    @Expose
    private String iDDiaDiem;
    @SerializedName("Ten_DiaDiem")
    @Expose
    private String tenDiaDiem;
    @SerializedName("Hinh_DiaDiem")
    @Expose
    private String hinhDiaDiem;
    @SerializedName("DiaChi_DiaDiem")
    @Expose
    private String diaChiDiaDiem;
    @SerializedName("Rating_DiaDiem")
    @Expose
    private String ratingDiaDiem;
    @SerializedName("TrangThai")
    @Expose
    private String trangThai;
    @SerializedName("UuDai")
    @Expose
    private String uuDai;

    public String getIDDiaDiem() {
        return iDDiaDiem;
    }

    public void setIDDiaDiem(String iDDiaDiem) {
        this.iDDiaDiem = iDDiaDiem;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getHinhDiaDiem() {
        return hinhDiaDiem;
    }

    public void setHinhDiaDiem(String hinhDiaDiem) {
        this.hinhDiaDiem = hinhDiaDiem;
    }

    public String getDiaChiDiaDiem() {
        return diaChiDiaDiem;
    }

    public void setDiaChiDiaDiem(String diaChiDiaDiem) {
        this.diaChiDiaDiem = diaChiDiaDiem;
    }

    public String getRatingDiaDiem() {
        return ratingDiaDiem;
    }

    public void setRatingDiaDiem(String ratingDiaDiem) {
        this.ratingDiaDiem = ratingDiaDiem;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getUuDai() {
        return uuDai;
    }

    public void setUuDai(String uuDai) {
        this.uuDai = uuDai;
    }

}