package com.trantiendat.Service;

import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Model.YeuThich;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("Quan%20an.php")
    Call<List<DiaDiem>> getDataDiaDiem();
    @GET("Quang%20cao.php")
    Call<List<QuangCao>> getDataQuangCao();

    @GET("danhsachgiohang.php")
    Call<List<GioHang>> getDataGioHang();

    @GET("danhsachCTHD.php")
    Call<List<ChiTietHoaDon>> getDataCTHD();


    @FormUrlEncoded
    @POST("chitietquangcao.php")
    Call<List<DiaDiem>> getDataChitietquangcao(@Field("ID_QuangCao") String ID_QuangCao);

    @FormUrlEncoded
    @POST("danhsachmonan.php")
    Call<List<MonAn>> getDatadanhsachmonan(@Field("ID_DiaDiem") String ID_DiaDiem);

    @FormUrlEncoded
    @POST("themGioHang.php")
    Call<String> insertDatagiohang(@Field("ID_MonAn") String ID_MonAn,@Field("SoLuong") String SoLuong);

    @FormUrlEncoded
    @POST("CTHD.php")
    Call<String> insertDataCTHD(@Field("ID_MonAn") String ID_MonAn ,@Field("SoLuong") String SoLuong,@Field("Gia") String Gia,@Field("ThanhTien") String ThanhTien);

    @FormUrlEncoded
    @POST("suaDon.php")
    Call<String> updateDatahoadon(@Field("ID_MonAn") String ID_MonAn , @Field("SoLuong") String SoLuong,@Field("ThanhTien") String ThanhTien);

    @FormUrlEncoded
    @POST("xoaDon.php")
    Call<String> deleteDatahoadon(@Field("ID_MonAn") String ID_MonAn);

    @FormUrlEncoded
    @POST("luuHoaDon.php")
    Call<String> saveDatahoadon(@Field("Tong") Long Tong ,@Field("ID_HoaDon") String ID_HoaDon );


    @FormUrlEncoded
    @POST("taoHoaDon.php")
    Call<String> taoHoaDon(@Field("Tong_HoaDon") String Tong_HoaDon);


    @FormUrlEncoded
    @POST("themChiTietHoaDon.php")
    Call<String> insertIDhoadon(@Field("ID_HoaDon") String ID_HoaDon );
//
//    @FormUrlEncoded
//    @POST("taoHoaDon.php")
//    Call<String> taoHoaDon(@Field("Tong_HoaDon") String Tong_HoaDon);
}
