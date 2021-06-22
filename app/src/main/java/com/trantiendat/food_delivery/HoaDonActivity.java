package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.Request;
import com.trantiendat.Adapter.ChiTietHoaDonAdapter;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoaDonActivity extends AppCompatActivity {
    TextView tv_tong, tv_text;
    Button btn_thanhtoan;
    RecyclerView rcv_cthd;
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    String id;
    String id_user;
    SessionManagement sessionManagement;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        sessionManagement = new SessionManagement(this);


        DataIntent();
        init();
        getDataCTHD();

    }

    private void init() {
        rcv_cthd = findViewById(R.id.rcv_cthd);
        tv_tong = findViewById(R.id.tv_tong);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);

    }

    private void getDataCTHD() {
        DataService dataService = APIService.getService();
        Call<List<ChiTietHoaDon>> callback = dataService.getDataCTHD();
        callback.enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                chiTietHoaDonArrayList = (ArrayList<ChiTietHoaDon>) response.body();
                chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(chiTietHoaDonArrayList, HoaDonActivity.this);
                rcv_cthd.setLayoutManager(new LinearLayoutManager(HoaDonActivity.this));
                rcv_cthd.setHasFixedSize(true);
                rcv_cthd.setAdapter(chiTietHoaDonAdapter);
                chiTietHoaDonAdapter.notifyDataSetChanged();

                RecyclerView.ItemDecoration decoration = new DividerItemDecoration(HoaDonActivity.this, DividerItemDecoration.VERTICAL);
                rcv_cthd.addItemDecoration(decoration);
                DataIntent();
                long tong = 0;
                for (int i = 0; i < chiTietHoaDonArrayList.size(); i++) {
                    tong += (Long.parseLong(chiTietHoaDonArrayList.get(i).getThanhTien()));
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tv_tong.setText(decimalFormat.format(tong) + "Đ");


                long finalTong = tong;

                btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.saveDatahoadon(finalTong, id);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("Success")) {
                                    Toast.makeText(HoaDonActivity.this, "đã thanh toán", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HoaDonActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }

                        });
                        startActivity(new Intent(HoaDonActivity.this, MainMenuActivity.class));
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {

            }
        });
    }


    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id");
            }
            if (intent.hasExtra("id_user")) {
                id_user = intent.getStringExtra("id_user");
            }

        }

    }
}
