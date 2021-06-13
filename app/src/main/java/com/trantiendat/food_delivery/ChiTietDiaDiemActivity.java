package com.trantiendat.food_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.trantiendat.Adapter.Bottomsheet;
import com.trantiendat.Adapter.DanhSachDiaDiemAdapter;
import com.trantiendat.Adapter.MonAnAdapter;

import com.trantiendat.Database.Database;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDiaDiemActivity extends AppCompatActivity {

    private ArrayList<MonAn> monAnArrayList;
    private ArrayList<DiaDiem> diaDiemArrayList;
    private TextView tv_diachi;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar_back;
    private ImageButton imgvbtn_like, imgvbtn_Dislike;
    private RecyclerView rcv_danhsachmonan;
    private ImageView img_DiaDiem, imgv_bottom;
    private DanhSachDiaDiemAdapter danhSachDiaDiemAdapter;
    private MonAnAdapter monAnAdapter;
    private DiaDiem diaDiem;
    private QuangCao quangCao;
    private FrameLayout layoutFrame;
    private Database database;
    String like = "1";
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dia_diem);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        init();
        DataIntent();
        setToolbar();
        CheckData();
        showBottomSheet();

    }

    private void init() {
        coordinatorLayout = findViewById(R.id.coordinator);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        rcv_danhsachmonan = findViewById(R.id.rcv_danhsachmonan);
        toolbar_back = findViewById(R.id.toolbar_back);
        img_DiaDiem = findViewById(R.id.img_DiaDiem);
        imgvbtn_like = findViewById(R.id.imgvbtn_like);
        imgvbtn_Dislike = findViewById(R.id.imgvbtn_Dislike);
        tv_diachi = findViewById(R.id.tv_diachiDiaDiem);
        layoutFrame = findViewById(R.id.layoutFrame);
        imgv_bottom = findViewById(R.id.imgv_bottom);


    }

    private void CheckData() {
        if (quangCao != null && !quangCao.getNoiDungQuangCao().equals("")) {
            setValueInView(quangCao.getNoiDungQuangCao(), quangCao.getHinhQuangCao());
            getDataQuangCao(quangCao.getIDQuangCao());
        }
        if (diaDiem != null && !diaDiem.getTenDiaDiem().equals("")) {
            setValueInView(diaDiem.getTenDiaDiem(), diaDiem.getHinhDiaDiem());
            getDataMonAn(diaDiem.getIDDiaDiem());
        }
    }

    private void setValueInView(String ten, String hinh) {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(hinh).into(imageView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        Glide.with(this).load(hinh).into(img_DiaDiem);


    }

    private void getDataQuangCao(String ID_QuangCao) {
        DataService dataService = APIService.getService();
        Call<List<DiaDiem>> callback = dataService.getDataChitietquangcao(ID_QuangCao);
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                danhSachDiaDiemAdapter = new DanhSachDiaDiemAdapter(ChiTietDiaDiemActivity.this, diaDiemArrayList);
                rcv_danhsachmonan.setLayoutManager(new LinearLayoutManager(ChiTietDiaDiemActivity.this));
                rcv_danhsachmonan.setHasFixedSize(true);
                rcv_danhsachmonan.setAdapter(danhSachDiaDiemAdapter);
            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

            }
        });
    }

    private void getDataMonAn(String ID_DiaDiem) {
        DataService dataService = APIService.getService();
        Call<List<MonAn>> callback = dataService.getDatadanhsachmonan(ID_DiaDiem);
        callback.enqueue(new Callback<List<MonAn>>() {
            @Override
            public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                monAnArrayList = (ArrayList<MonAn>) response.body();
                monAnAdapter = new MonAnAdapter(ChiTietDiaDiemActivity.this, monAnArrayList);
                rcv_danhsachmonan.setLayoutManager(new LinearLayoutManager(ChiTietDiaDiemActivity.this));
                rcv_danhsachmonan.setHasFixedSize(true);
                rcv_danhsachmonan.setAdapter(monAnAdapter);
            }

            @Override
            public void onFailure(Call<List<MonAn>> call, Throwable t) {

            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        database = new Database(ChiTietDiaDiemActivity.this, "YeuThich.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS YeuThich(ID INTEGER PRIMARY KEY, " +
                "Ten TEXT, " + "diachi TEXT, " + "hinh TEXT," + "trangthai INTEGER)");
        String sql = "SELECT * FROM YeuThich";
        boolean check = false;
        Cursor cursor = database.GetData(sql);
        String trangthai = "1";
        while (cursor.moveToNext()) {
            if (diaDiem.getIDDiaDiem().equals(cursor.getString(0)) && trangthai.equals(cursor.getString(4))) ;
            check = true;
            break;
        }
        if (check) {
            imgvbtn_like.setVisibility(View.VISIBLE);
            imgvbtn_Dislike.setVisibility(View.INVISIBLE);

        } else {
            imgvbtn_like.setVisibility(View.INVISIBLE);
            imgvbtn_Dislike.setVisibility(View.VISIBLE);

        }

        imgvbtn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = new Database(ChiTietDiaDiemActivity.this, "YeuThich.sqlite", null, 1);
                database.QueryData("CREATE TABLE IF NOT EXISTS YeuThich(ID INTEGER PRIMARY KEY, " +
                        "Ten TEXT, " + "diachi TEXT, " + "hinh TEXT," + "trangthai INTEGER)");
                database.QueryData("DELETE FROM YeuThich WHERE ID= " + diaDiem.getIDDiaDiem());
                imgvbtn_like.setVisibility(View.INVISIBLE);
                imgvbtn_Dislike.setVisibility(View.VISIBLE);
                Toast.makeText(ChiTietDiaDiemActivity.this, "đã bỏ lưu", Toast.LENGTH_SHORT).show();

            }
        });
        imgvbtn_Dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = new Database(ChiTietDiaDiemActivity.this, "YeuThich.sqlite", null, 1);
                database.QueryData("CREATE TABLE IF NOT EXISTS YeuThich(ID INTEGER PRIMARY KEY, " +
                        "Ten TEXT, " + "diachi TEXT, " + "hinh TEXT," + "trangthai INTEGER)");
                database.QueryData("INSERT INTO YeuThich VALUES('" + diaDiem.getIDDiaDiem() + "', '" + diaDiem.getTenDiaDiem() + "'," +
                        " '" + diaDiem.getDiaChiDiaDiem() + "', '" + diaDiem.getHinhDiaDiem() + "', '" + like + "')");

                imgvbtn_like.setVisibility(View.VISIBLE);
                imgvbtn_Dislike.setVisibility(View.INVISIBLE);
                Toast.makeText(ChiTietDiaDiemActivity.this, "Đã thêm vào danh sách yêu thích: " + diaDiem.getTenDiaDiem() + "", Toast.LENGTH_SHORT).show();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("QuangCao")) {
                quangCao = (QuangCao) intent.getSerializableExtra("QuangCao");
            }
            if (intent.hasExtra("DiaDiem")) {
                diaDiem = (DiaDiem) intent.getSerializableExtra("DiaDiem");
            }
            if (intent.hasExtra("pos")) {
                pos = intent.getIntExtra("pos", 0);
            }

        }
    }

    private void showBottomSheet() {
        imgv_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottom();
            }
        });
    }


    private void clickOpenBottom() {
        Bottomsheet bottomsheet = Bottomsheet.getInstance(diaDiem);
        bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
        bottomsheet.setCancelable(false);
    }


}