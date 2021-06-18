package com.trantiendat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HoaDon implements Serializable {

    @SerializedName("ID_HoaDon")
    @Expose
    private String iDHoaDon;
    @SerializedName("Tong_HoaDon")
    @Expose
    private String tongHoaDon;
    @SerializedName("NgayMua")
    @Expose
    private String ngayMua;
    @SerializedName("ID_User")
    @Expose
    private String iDUser;

    public String getIDHoaDon() {
        return iDHoaDon;
    }

    public void setIDHoaDon(String iDHoaDon) {
        this.iDHoaDon = iDHoaDon;
    }

    public String getTongHoaDon() {
        return tongHoaDon;
    }

    public void setTongHoaDon(String tongHoaDon) {
        this.tongHoaDon = tongHoaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getIDUser() {
        return iDUser;
    }

    public void setIDUser(String iDUser) {
        this.iDUser = iDUser;
    }

}