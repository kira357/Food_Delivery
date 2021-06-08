package com.trantiendat.Model;

public class YeuThich {
    private String id;
    private String ten;
    private String diachi;
    private String hinh;
    private int favourite;

    public YeuThich(String id, String ten, String diachi, String hinh, int favourite) {
        this.id = id;
        this.ten = ten;
        this.diachi = diachi;
        this.hinh = hinh;
        this.favourite = favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
