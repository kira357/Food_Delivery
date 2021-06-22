package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.trantiendat.Adapter.DanhSachDiaDiemAdapter;
import com.trantiendat.Adapter.MonAnAdapter;
import com.trantiendat.Database.Database;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietQuangCaoActivity extends AppCompatActivity {
    private ArrayList<DiaDiem> diaDiemArrayList;
    private CoordinatorLayout coordinatorQuangCao;
    private CollapsingToolbarLayout collapsingtoolbarQuangCao;
    private Toolbar toolbar_backQuangCao;
    private RecyclerView rcv_danhsachDiaDiemQuangCao;
    private ImageView img_QuangCao;
    private DanhSachDiaDiemAdapter danhSachDiaDiemAdapter;
    private QuangCao quangCao;
    private ProgressBar progress_QuangCao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_quang_cao);
        DataIntent();
        init();
        setToolbar();
        CheckData();
    }

    private void init() {
        coordinatorQuangCao = findViewById(R.id.coordinatorQuangCao);
        collapsingtoolbarQuangCao = findViewById(R.id.collapsingtoolbarQuangCao);
        rcv_danhsachDiaDiemQuangCao = findViewById(R.id.rcv_danhsachDiaDiemQuangCao);
        toolbar_backQuangCao = findViewById(R.id.toolbar_backQuangCao);
        img_QuangCao = findViewById(R.id.img_QuangCao);
        progress_QuangCao = findViewById(R.id.progress_QuangCao);


    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("QuangCao")) {
                quangCao = (QuangCao) intent.getSerializableExtra("QuangCao");
            }
        }
    }

    private void CheckData() {
        if (quangCao != null && !quangCao.getNoiDungQuangCao().equals("")) {
            setValueInView(quangCao.getNoiDungQuangCao(), quangCao.getHinhQuangCao());
            getDataQuangCao(quangCao.getIDQuangCao());
        }
        if (quangCao.getNoiDungQuangCao().equals("")) {

        }
    }

    private void setValueInView(String ten, String hinh) {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(hinh).into(imageView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            collapsingtoolbarQuangCao.setBackground(bitmapDrawable);
        Glide.with(this).load(hinh).into(img_QuangCao);

    }

    private void getDataQuangCao(String ID_QuangCao) {
        DataService dataService = APIService.getService();
        progress_QuangCao.setVisibility(View.VISIBLE);
        Call<List<DiaDiem>> callback = dataService.getDataChitietquangcao(ID_QuangCao);
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                danhSachDiaDiemAdapter = new DanhSachDiaDiemAdapter(ChiTietQuangCaoActivity.this, diaDiemArrayList);
                rcv_danhsachDiaDiemQuangCao.setLayoutManager(new LinearLayoutManager(ChiTietQuangCaoActivity.this));
                rcv_danhsachDiaDiemQuangCao.setHasFixedSize(true);
                rcv_danhsachDiaDiemQuangCao.setAdapter(danhSachDiaDiemAdapter);
                progress_QuangCao.setVisibility(View.GONE);
                RecyclerView.ItemDecoration decoration = new DividerItemDecoration(ChiTietQuangCaoActivity.this, DividerItemDecoration.VERTICAL);
                rcv_danhsachDiaDiemQuangCao.addItemDecoration(decoration);
            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_backQuangCao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_backQuangCao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        collapsingtoolbarQuangCao.setExpandedTitleColor(Color.WHITE);
        collapsingtoolbarQuangCao.setCollapsedTitleTextColor(Color.WHITE);
    }
}