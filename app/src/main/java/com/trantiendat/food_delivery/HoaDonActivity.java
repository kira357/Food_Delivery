package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.Request;
import com.google.android.material.button.MaterialButton;
import com.trantiendat.Adapter.ChiTietHoaDonAdapter;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.MoMoConstants;
import com.trantiendat.direction.SessionManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoaDonActivity extends AppCompatActivity {
    TextView tv_tong;
    MaterialButton btn_thanhtoan, btnPaymentMoMo;
    RecyclerView rcv_cthd;
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    String id;
    String id_user;
    SessionManagement sessionManagement;
    Toolbar toolbar_back;
    ProgressDialog progressDialog;

    int environment = 1;//developer default - Production environment = 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        sessionManagement = new SessionManagement(this);


        DataIntent();
        init();
        getDataCTHD();
        //setToolbar();

    }

    private void init() {
        rcv_cthd = findViewById(R.id.rcv_cthd);
        tv_tong = findViewById(R.id.tv_tong);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        btnPaymentMoMo = findViewById(R.id.btnPaymentMoMo);
        //  toolbar_back = findViewById(R.id.toolbar_back);

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
                int tong = 0;
                for (int i = 0; i < chiTietHoaDonArrayList.size(); i++) {
                    tong += (Long.parseLong(chiTietHoaDonArrayList.get(i).getThanhTien()));
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tv_tong.setText(decimalFormat.format(tong) + "Đ");


                int finalTong = tong;

                btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(HoaDonActivity.this);
                        progressDialog.setMessage("waiting...");
                        progressDialog.show();
                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.saveDatahoadon(finalTong, id);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response != null) {
                                    progressDialog.dismiss();
                                    String ketqua = response.body();
                                    if (ketqua.equals("Success")) {
                                        Toast.makeText(HoaDonActivity.this, "đã thanh toán", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(HoaDonActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast t1 = Toast.makeText(HoaDonActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                                t1.setGravity(Gravity.CENTER, 0, 0);
                                t1.show();
                                progressDialog.dismiss();
                            }

                        });
                        startActivity(new Intent(HoaDonActivity.this, MainMenuActivity.class));
                        HoaDonActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                });
                btnPaymentMoMo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(HoaDonActivity.this);
                        progressDialog.setMessage("waiting...");
                        progressDialog.show();
                        Intent intent;
                        Bundle data = new Bundle();
                        intent = new Intent(HoaDonActivity.this, PaymentActivity.class);
                        data.putInt(MoMoConstants.KEY_ENVIRONMENT, environment);
                        data.putString(MoMoConstants.TONG_HOA_DON, String.valueOf(finalTong));
                        data.putString(MoMoConstants.ID_USER, sessionManagement.getUser().getIDUser());
                        data.putString(MoMoConstants.ID_HOA_DON, id);
                        data.putParcelableArrayList(MoMoConstants.CTTHD, chiTietHoaDonArrayList);
                        intent.putExtras(data);
                        progressDialog.dismiss();
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
                Toast t1 = Toast.makeText(HoaDonActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                progressDialog.dismiss();
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

    private void setToolbar() {
        setSupportActionBar(toolbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataCTHD();
    }
}
