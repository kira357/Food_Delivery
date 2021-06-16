package com.trantiendat.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChiTietHoaDon  implements Serializable, Parcelable {

    @SerializedName("ID_MonAn")
    @Expose
    private String iDMonAn;
    @SerializedName("Ten_MonAn")
    @Expose
    private String tenMonAn;
    @SerializedName("Hinh_MonAn")
    @Expose
    private String hinhMonAn;
    @SerializedName("ID_DiaDiem")
    @Expose
    private String iDDiaDiem;
    @SerializedName("Ten_DiaDiem")
    @Expose
    private String tenDiaDiem;
    @SerializedName("DiaChi_DiaDiem")
    @Expose
    private String diaChiDiaDiem;
    @SerializedName("Gia")
    @Expose
    private String gia;
    @SerializedName("SoLuong")
    @Expose
    private String soLuong;
    @SerializedName("ThanhTien")
    @Expose
    private String thanhTien;

    public ChiTietHoaDon(String tenMonAn, String hinhMonAn, String tenDiaDiem, String diaChiDiaDiem, String gia, String soLuong) {
        this.tenMonAn = tenMonAn;
        this.hinhMonAn = hinhMonAn;
        this.tenDiaDiem = tenDiaDiem;
        this.diaChiDiaDiem = diaChiDiaDiem;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    protected ChiTietHoaDon(Parcel in) {
        iDMonAn = in.readString();
        tenMonAn = in.readString();
        hinhMonAn = in.readString();
        iDDiaDiem = in.readString();
        tenDiaDiem = in.readString();
        diaChiDiaDiem = in.readString();
        gia = in.readString();
        soLuong = in.readString();
        thanhTien = in.readString();
    }

    public static final Creator<ChiTietHoaDon> CREATOR = new Creator<ChiTietHoaDon>() {
        @Override
        public ChiTietHoaDon createFromParcel(Parcel in) {
            return new ChiTietHoaDon(in);
        }

        @Override
        public ChiTietHoaDon[] newArray(int size) {
            return new ChiTietHoaDon[size];
        }
    };

    public String getIDMonAn() {
        return iDMonAn;
    }

    public void setIDMonAn(String iDMonAn) {
        this.iDMonAn = iDMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getHinhMonAn() {
        return hinhMonAn;
    }

    public void setHinhMonAn(String hinhMonAn) {
        this.hinhMonAn = hinhMonAn;
    }

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

    public String getDiaChiDiaDiem() {
        return diaChiDiaDiem;
    }

    public void setDiaChiDiaDiem(String diaChiDiaDiem) {
        this.diaChiDiaDiem = diaChiDiaDiem;
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

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iDMonAn);
        dest.writeString(tenMonAn);
        dest.writeString(hinhMonAn);
        dest.writeString(iDDiaDiem);
        dest.writeString(tenDiaDiem);
        dest.writeString(diaChiDiaDiem);
        dest.writeString(gia);
        dest.writeString(soLuong);
        dest.writeString(thanhTien);
    }
}