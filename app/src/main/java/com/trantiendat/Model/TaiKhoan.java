package com.trantiendat.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaiKhoan implements Serializable {

    @SerializedName("ID_TaiKhoan")
    @Expose
    private String iDTaiKhoan;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ID_User")
    @Expose
    private String iDUser;

    public String getiDUser() {
        return iDUser;
    }

    public void setiDUser(String iDUser) {
        this.iDUser = iDUser;
    }

    public TaiKhoan(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public TaiKhoan(String iDTaiKhoan) {
        this.iDTaiKhoan = iDTaiKhoan;
    }

    public TaiKhoan(String iDTaiKhoan, String user, String email) {
        this.iDTaiKhoan = iDTaiKhoan;
        this.user = user;
        this.email = email;
    }

    public String getIDTaiKhoan() {
        return iDTaiKhoan;
    }

    public void setIDTaiKhoan(String iDTaiKhoan) {
        this.iDTaiKhoan = iDTaiKhoan;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}