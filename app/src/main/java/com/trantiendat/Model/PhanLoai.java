package com.trantiendat.Model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhanLoai implements Serializable {

    @SerializedName("ID_PhanLoai")
    @Expose
    private String iDPhanLoai;
    @SerializedName("PhanLoai")
    @Expose
    private String phanLoai;
    @SerializedName("Hinh")
    @Expose
    private String hinh;

    public String getIDPhanLoai() {
        return iDPhanLoai;
    }

    public void setIDPhanLoai(String iDPhanLoai) {
        this.iDPhanLoai = iDPhanLoai;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}