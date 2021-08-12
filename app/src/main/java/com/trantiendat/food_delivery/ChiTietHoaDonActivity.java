package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trantiendat.Adapter.thongtinCTHDAdapter;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Model.ThongTin;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    private ListView lv_thongtinCTHD;
    private ArrayList<ThongTin> thongTinArrayList;
    private thongtinCTHDAdapter thongtinCTHDAdapter;
    private Toolbar toolbar_backThongtin;
    HoaDon hoaDon;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        DataIntent();
        init();
        CheckData();
        setToolbar();
       // setEvent();


    }

    private void init() {
        lv_thongtinCTHD = findViewById(R.id.lv_thongtinCTHD);
        toolbar_backThongtin = findViewById(R.id.toolbar_backThongtin);
    }

    private void getDatathongtin(String id_HoaDon) {
        DataService dataService = APIService.getService();
        Call<List<ThongTin>> callback = dataService.thongtinCTHD(id_HoaDon);
        callback.enqueue(new Callback<List<ThongTin>>() {
            @Override
            public void onResponse(Call<List<ThongTin>> call, Response<List<ThongTin>> response) {
                thongTinArrayList = (ArrayList<ThongTin>) response.body();
                thongtinCTHDAdapter = new thongtinCTHDAdapter(ChiTietHoaDonActivity.this, thongTinArrayList);
                lv_thongtinCTHD.setAdapter(thongtinCTHDAdapter);
                thongtinCTHDAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ThongTin>> call, Throwable t) {

            }
        });
    }

    private void setEvent() {
        lv_thongtinCTHD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DiaDiem diaDiem = diaDiemArrayList.get(position);
                Intent intent = new Intent(ChiTietHoaDonActivity.this, ChiTietDiaDiemActivity.class);
                intent.putExtra("DiaDiem", thongTinArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    private void CheckData() {

        if (hoaDon != null) {
            getDatathongtin(hoaDon.getIDHoaDon());
        }
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("ID_HoaDon")) {
                hoaDon = (HoaDon) intent.getSerializableExtra("ID_HoaDon");
            }

        }
    }

    private void setToolbar() {

        setSupportActionBar(toolbar_backThongtin);
        getSupportActionBar().setTitle("Hoá đơn ngày " + hoaDon.getNgayMua());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar_backThongtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }
}