package com.trantiendat.Service;

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



    @FormUrlEncoded
    @POST("chitietquangcao.php")
    Call<List<DiaDiem>> getDataChitietquangcao(@Field("ID_QuangCao") String ID_QuangCao);

    @FormUrlEncoded
    @POST("danhsachmonan.php")
    Call<List<MonAn>> getDatadanhsachmonan(@Field("ID_DiaDiem") String ID_DiaDiem);

    @FormUrlEncoded
    @POST("YeuThich.php")
    Call<String> insertDatayeuthich(@Field("ID_DiaDiem") String ID_DiaDiem ,@Field("TrangThai") String TrangThai);

    @GET("danhsachyeuthich.php")
    Call<List<YeuThich>> getDataDanhsachyeuthich();

    @FormUrlEncoded
    @POST("themGioHang.php")
    Call<String> insertDatagiohang(@Field("ID_MonAn") String ID_MonAn);

}
