package com.trantiendat.Service;

import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Model.PhanLoai;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Model.TaiKhoan;
import com.trantiendat.Model.ThongTin;
import com.trantiendat.Model.User;
import com.trantiendat.Model.YeuThich;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {
    @GET("Quan%20an.php")
    Call<List<DiaDiem>> getDataDiaDiem();

    @GET("Quang%20cao.php")
    Call<List<QuangCao>> getDataQuangCao();

    @GET("danhsachgiohang.php")
    Call<List<GioHang>> getDataGioHang();

    @GET("danhsachCTHD.php")
    Call<List<ChiTietHoaDon>> getDataCTHD();

    @GET("danhsachyeuthich.php")
    Call<List<DiaDiem>> getDataYeuThich();

    @GET("PhanLoai.php")
    Call<List<PhanLoai>> getDataPhanLoai();

    @FormUrlEncoded
    @POST("danhsachhoadon.php")
    Call<List<HoaDon>> getDataDanhsachhoadon(@Field("ID_User") String ID_user);

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
    Call<String> taoHoaDon(@Field("Tong_HoaDon") String Tong_HoaDon,@Field("ID_User") String ID_User);


    @FormUrlEncoded
    @POST("themChiTietHoaDon.php")
    Call<String> insertIDhoadon(@Field("ID_HoaDon") String ID_HoaDon );

    @FormUrlEncoded
    @POST("XoaGioHang.php")
    Call<String> xoaGioHang(@Field("ID_MonAn") String ID_MonAn);

    @FormUrlEncoded
    @POST("Timkiem.php")
    Call<List<DiaDiem>> getDataSearch(@Field("Key") String Key);

    @FormUrlEncoded
    @POST("DangKi.php")
    Call<String> DangKi(@Field("user") String user,@Field("password") String password,@Field("email") String email);

    @FormUrlEncoded
    @POST("suaUser.php")
    Call<String> updateDataUser(@Field("ID_User") String ID_User , @Field("Ten") String Ten,@Field("Diachi") String Diachi);

    @FormUrlEncoded
    @POST("DangNhap.php")
    Call<ArrayList<User>> DangNhap(@Field("user") String user, @Field("password") String password);


    @FormUrlEncoded
    @POST("suaYeuThich.php")
    Call<String> suaYeuThich(@Field("ID_DiaDiem") String ID_DiaDiem,@Field("TrangThai") int TrangThai);

    @FormUrlEncoded
    @POST("ChiTietPhanLoai.php")
    Call<List<DiaDiem>> getDatadanhsanhPhanLoai(@Field("id_PhanLoai") String id_PhanLoai);

    @FormUrlEncoded
    @POST("suaPassword.php")
    Call<String> resetDataPassword(@Field("user") String user,@Field("newPass") String newPass);

    @FormUrlEncoded
    @POST("thongtinCTHD.php")
    Call<List<ThongTin>> thongtinCTHD(@Field("id_HoaDon") String id_HoaDon);

}
