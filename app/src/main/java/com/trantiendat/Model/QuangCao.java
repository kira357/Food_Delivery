package com.trantiendat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuangCao implements Serializable {

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
    @SerializedName("ID_QuangCao")
    @Expose
    private String iDQuangCao;
    @SerializedName("Hinh_QuangCao")
    @Expose
    private String hinhQuangCao;
    @SerializedName("NoiDung_QuangCao")
    @Expose
    private String noiDungQuangCao;

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

    public String getIDQuangCao() {
        return iDQuangCao;
    }

    public void setIDQuangCao(String iDQuangCao) {
        this.iDQuangCao = iDQuangCao;
    }

    public String getHinhQuangCao() {
        return hinhQuangCao;
    }

    public void setHinhQuangCao(String hinhQuangCao) {
        this.hinhQuangCao = hinhQuangCao;
    }

    public String getNoiDungQuangCao() {
        return noiDungQuangCao;
    }

    public void setNoiDungQuangCao(String noiDungQuangCao) {
        this.noiDungQuangCao = noiDungQuangCao;
    }

}