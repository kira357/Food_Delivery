package com.trantiendat.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GioHang {

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

}