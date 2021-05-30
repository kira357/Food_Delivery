package com.trantiendat.Service;

import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.QuangCao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("Quan%20an.php")
    Call<List<DiaDiem>> getDataDiaDiem();
    @GET("Quang%20cao.php")
    Call<List<QuangCao>> getDataQuangCao();
}
