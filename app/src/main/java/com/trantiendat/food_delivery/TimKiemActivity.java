package com.trantiendat.food_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.trantiendat.Adapter.TimKiemAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.NetworkCheck.ConnectionReceiver;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {
    SearchView sv_diadiem;
    ArrayList<DiaDiem> diaDiemArrayList;
    TimKiemAdapter timKiemAdapter;
    ListView lv_diadiem;
    Toolbar toolbar_back;
    TextView tv_mess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        TimkiemDiaDiem();
        setEvent();
        setToolbar();
    }

    private void init() {

        sv_diadiem = findViewById(R.id.sv_diadiem);
        lv_diadiem = findViewById(R.id.lv_diadiem);
        toolbar_back = findViewById(R.id.toolbar_back);
        tv_mess = findViewById(R.id.tv_mess);
    }

    private void TimkiemDiaDiem() {
        sv_diadiem.setIconifiedByDefault(false);

        sv_diadiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DataService dataService = APIService.getService();
                Call<List<DiaDiem>> callbackk = dataService.getDataSearch(query);
                callbackk.enqueue(new Callback<List<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                        diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                        if (diaDiemArrayList.size() > 0) {
                            tv_mess.setVisibility(View.GONE);
                            timKiemAdapter = new TimKiemAdapter(TimKiemActivity.this, diaDiemArrayList);
                            lv_diadiem.setAdapter(timKiemAdapter);
                            timKiemAdapter.notifyDataSetChanged();
                        } else {
                            tv_mess.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DataService dataService = APIService.getService();
                Call<List<DiaDiem>> callbackk = dataService.getDataSearch(newText);
                callbackk.enqueue(new Callback<List<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                        diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                        if (diaDiemArrayList.size() > 0) {
                            timKiemAdapter = new TimKiemAdapter(TimKiemActivity.this, diaDiemArrayList);
                            lv_diadiem.setAdapter(timKiemAdapter);
                            tv_mess.setVisibility(View.GONE);
                            timKiemAdapter.notifyDataSetChanged();
                        } else {
                            tv_mess.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }


    private void setEvent() {
        lv_diadiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaDiem diaDiem = diaDiemArrayList.get(position);
                Intent intent = new Intent(TimKiemActivity.this, ChiTietDiaDiemActivity.class);
                intent.putExtra("DiaDiem", diaDiem);
                startActivity(intent);

            }
        });
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

