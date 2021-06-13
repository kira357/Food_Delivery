package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trantiendat.Adapter.ChiTietHoaDonAdapter;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.Fragment.Cart.PayFragment;
import com.trantiendat.food_delivery.Fragment.CartFragment;
import com.trantiendat.food_delivery.Fragment.DiaDiemFragment;
import com.trantiendat.food_delivery.Fragment.HomeFragment;

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
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        init();
        DataIntent();
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
                        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.SHARE), "");
                        if (loginStatus.equals("log_in")) {
//                            DataService dataService = APIService.getService();
//                            Call<String> callback = dataService.saveDatahoadon(finalTong,id);
//                            callback.enqueue(new Callback<String>() {
//                                @Override
//                                public void onResponse(Call<String> call, Response<String> response) {
//                                    String ketqua = response.body();
//                                    if (ketqua.equals("Success")) {
//                                        Toast.makeText(HoaDonActivity.this, "đã thanh toán", Toast.LENGTH_SHORT).show();
//
//                                    } else {
//                                        Toast.makeText(HoaDonActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<String> call, Throwable t) {
//
//                                }
//                            });
                            startActivity(new Intent(HoaDonActivity.this, MainMenuActivity.class));
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
                            builder.setMessage("Vui lòng đăng nhập trước khi thanh toán")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity(new Intent(HoaDonActivity.this, LoginActivity.class));
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();

                        }

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
        }
    }


}