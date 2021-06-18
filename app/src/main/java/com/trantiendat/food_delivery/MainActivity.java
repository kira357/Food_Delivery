package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Timer timer;
    public static ArrayList<GioHang> gioHangArrayList;
    public static ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(gioHangArrayList !=null){

        }else {
            gioHangArrayList = new ArrayList<>();
        }
        if(chiTietHoaDonArrayList != null){

        }

        setChangeView();
    }
    private void setChangeView() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }, 3500);
        }
    }

}