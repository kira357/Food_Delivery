package com.trantiendat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("ID_User")
    @Expose
    private String iDUser;
    @SerializedName("Ten")
    @Expose
    private String ten;
    @SerializedName("Diachi")
    @Expose
    private String diachi;
    @SerializedName("Hinh")
    @Expose
    private String hinh;

    public User(String iDUser, String ten, String diachi, String hinh) {
        this.iDUser = iDUser;
        this.ten = ten;
        this.diachi = diachi;
        this.hinh = hinh;
    }

    public String getIDUser() {
        return iDUser;
    }

    public void setIDUser(String iDUser) {
        this.iDUser = iDUser;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}