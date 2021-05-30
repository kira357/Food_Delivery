package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.trantiendat.Model.DiaDiem;

public class ChiTietDiaDiemActivity extends AppCompatActivity {
    DiaDiem diaDiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dia_diem);
        DataIntent();
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("dd")) {
                diaDiem = (DiaDiem) intent.getSerializableExtra("dd");
                Log.d("check", "DataIntent: " + diaDiem.getIDDiaDiem());
                //Toast.makeText(this, diaDiem.getIDDiaDiem(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}