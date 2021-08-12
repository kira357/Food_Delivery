package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.direction.MoMoConstants;

import java.util.ArrayList;

public class NotificationDetailActivity extends AppCompatActivity {
    TextView tvEnvironment;
    TextView tvMerchantCode;
    TextView tvMerchantName, tvmessages;
    TextView tvMessage;
    TextView tvTong, tvtenmon;
    String message;
    String tien;
    String id_hoandon, id_user;
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    Toolbar toolbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        Bundle data = getIntent().getExtras();
        if (data != null) {

            message = data.getString("message");
            tien = data.getString("tien");
            id_hoandon = data.getString("id_hoandon");
            id_user = data.getString("id_user");
            chiTietHoaDonArrayList = data.getParcelableArrayList("cthd");
        }
        init();
        setToolbar();
        tvMerchantCode.setText(id_hoandon);
        tvMerchantName.setText(id_user);
        tvTong.setText(tien);
        tvtenmon.setText(getInfo());
        tvmessages.setText(message);

    }

    private void init() {
        tvEnvironment = findViewById(R.id.tvEnvironment);
        tvMerchantCode = findViewById(R.id.tvMerchantCode);
        tvMerchantName = findViewById(R.id.tvMerchantName);
        tvTong = findViewById(R.id.tvTong);
        tvMessage = findViewById(R.id.tvMessage);
        tvtenmon = findViewById(R.id.tvtenmon);
        tvmessages = findViewById(R.id.tvmessages);
        toolbar_back = findViewById(R.id.toolbar_back);
    }

    private String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chiTietHoaDonArrayList.size(); i++) {
            ChiTietHoaDon chiTietHoaDon = chiTietHoaDonArrayList.get(i);
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\r\n");
            }
            stringBuilder.append(chiTietHoaDon.getTenMonAn() + " ");
            stringBuilder.append(chiTietHoaDon.getGia() + " ");
            stringBuilder.append(chiTietHoaDon.getSoLuong());
        }
        return stringBuilder.toString();
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
}