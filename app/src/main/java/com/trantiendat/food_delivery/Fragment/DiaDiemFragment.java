package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.NetworkCheck.ConnectionReceiver;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiaDiemFragment extends Fragment {

    private RecyclerView rcv_DiaDiem;
    private ArrayList<DiaDiem> diaDiemArrayList;
    private DiaDiemAdapter diaDiemAdapter;
    private TextView tvNetworkFailHome;

    ProgressBar progressBar;

    LinearLayoutManager linearLayoutManager;
    View view;
//    boolean isLoading = false, limitData = false;
//
//    myHandler handler;
//
//
//    int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dia_diem, container, false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ConnectionReceiver.isConnected()) {
                    tvNetworkFailHome.setVisibility(View.VISIBLE);
                } else {
                    tvNetworkFailHome.setVisibility(View.GONE);
                }
                handler.postDelayed(this, 2000);
            }
        }, 1000);
        init();
        GetDataDiaDiem();
        setView();
        return view;
    }

    private void init() {
        rcv_DiaDiem = view.findViewById(R.id.rcv_DiaDiem);
        tvNetworkFailHome = view.findViewById(R.id.tvNetworkFailHome);
        progressBar = view.findViewById(R.id.progress_bar);


    }

    private void GetDataDiaDiem() {
        DataService dataService = APIService.getService();
        progressBar.setVisibility(View.VISIBLE);
        Call<List<DiaDiem>> callback = dataService.getDataDiaDiem();
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                if(response !=null && response.body().size()>0){
                    diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                    progressBar.setVisibility(View.GONE);
                    diaDiemAdapter = new DiaDiemAdapter(diaDiemArrayList, getActivity());
                    linearLayoutManager = new LinearLayoutManager(getActivity());
                    rcv_DiaDiem.setLayoutManager(linearLayoutManager);
                    rcv_DiaDiem.setHasFixedSize(true);
                    rcv_DiaDiem.setAdapter(diaDiemAdapter);
                    diaDiemAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

            }
        });
    }
    private void setView(){
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv_DiaDiem.addItemDecoration(decoration);
    }


}
