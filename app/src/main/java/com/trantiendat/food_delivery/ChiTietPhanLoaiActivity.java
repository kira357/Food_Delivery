package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Adapter.MonAnAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Model.PhanLoai;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietPhanLoaiActivity extends AppCompatActivity {
    RecyclerView rcv_PhanloaiDiadiem;
    PhanLoai phanLoai;
    private Toolbar toolbar_backPhanLoai;
    ArrayList<DiaDiem> diaDiemArrayList;
    DiaDiemAdapter diaDiemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phan_loai);
        DataIntent();
        init();
        CheckData();
        setToolbar();
    }

    private void init() {
        rcv_PhanloaiDiadiem = findViewById(R.id.rcv_PhanloaiDiadiem);
        toolbar_backPhanLoai = findViewById(R.id.toolbar_backPhanLoai);
    }


    private void getDataMonAn(String ID_PhanLoai) {
        DataService dataService = APIService.getService();
        Call<List<DiaDiem>> callback = dataService.getDatadanhsanhPhanLoai(ID_PhanLoai);
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                diaDiemAdapter = new DiaDiemAdapter(diaDiemArrayList, ChiTietPhanLoaiActivity.this);
                rcv_PhanloaiDiadiem.setLayoutManager(new LinearLayoutManager(ChiTietPhanLoaiActivity.this));
                rcv_PhanloaiDiadiem.setHasFixedSize(true);
                rcv_PhanloaiDiadiem.setAdapter(diaDiemAdapter);
            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {
                Toast t1 = Toast.makeText(ChiTietPhanLoaiActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_backPhanLoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(phanLoai.getPhanLoai());
        toolbar_backPhanLoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    private void CheckData() {
        if (phanLoai != null && !phanLoai.getPhanLoai().equals("")) {
            getDataMonAn(phanLoai.getIDPhanLoai());
        }
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("PhanLoai")) {
                phanLoai = (PhanLoai) intent.getSerializableExtra("PhanLoai");
            }
        }
    }
}
