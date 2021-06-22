package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.trantiendat.Adapter.DanhSachHoaDonAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Model.User;
import com.trantiendat.Service.API;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachHoaDonActivity extends AppCompatActivity {
    ListView lv_danhsachhoadon;
    DanhSachHoaDonAdapter danhSachHoaDonAdapter;
    ArrayList<HoaDon> hoaDonArrayList;
    private Toolbar toolbar_backHoaDon;

    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_hoa_don);
        sessionManagement = new SessionManagement(this);
        init();
        setData();
        setToolbar();
    }

    private void init() {
        lv_danhsachhoadon = findViewById(R.id.lv_danhsachhoadon);
        toolbar_backHoaDon = findViewById(R.id.toolbar_backHoaDon);

    }

    private void setData() {
        String id_user = sessionManagement.getUser().getIDUser();
        DataService dataService = APIService.getService();
        Call<List<HoaDon>> callback = dataService.getDataDanhsachhoadon(id_user);
        callback.enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                hoaDonArrayList = (ArrayList<HoaDon>) response.body();
                danhSachHoaDonAdapter = new DanhSachHoaDonAdapter(DanhSachHoaDonActivity.this, hoaDonArrayList);
                lv_danhsachhoadon.setAdapter(danhSachHoaDonAdapter);
                danhSachHoaDonAdapter.notifyDataSetChanged();

                lv_danhsachhoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HoaDon hoaDon = hoaDonArrayList.get(position);
                        Intent intent = new Intent(DanhSachHoaDonActivity.this,ChiTietHoaDonActivity.class);
                        intent.putExtra("ID_HoaDon" , hoaDon);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {

            }
        });
    }


    private void setToolbar() {
        setSupportActionBar(toolbar_backHoaDon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_backHoaDon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }
}