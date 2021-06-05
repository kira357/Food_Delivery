package com.trantiendat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YeuThich {

    @SerializedName("ID_DiaDiem")
    @Expose
    private String iDDiaDiem;
    @SerializedName("Ten_DiaDiem")
    @Expose
    private String tenDiaDiem;
    @SerializedName("Hinh_DiaDiem")
    @Expose
    private String hinhDiaDiem;
    @SerializedName("Diachi_DiaDiem")
    @Expose
    private String diachiDiaDiem;
    @SerializedName("ID_YeuThich")
    @Expose
    private String iDYeuThich;
    @SerializedName("TrangThai")
    @Expose
    private String trangThai;

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

    public String getDiachiDiaDiem() {
        return diachiDiaDiem;
    }

    public void setDiachiDiaDiem(String diachiDiaDiem) {
        this.diachiDiaDiem = diachiDiaDiem;
    }

    public String getIDYeuThich() {
        return iDYeuThich;
    }

    public void setIDYeuThich(String iDYeuThich) {
        this.iDYeuThich = iDYeuThich;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}