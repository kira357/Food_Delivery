package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trantiendat.Adapter.PhanLoaiAdapter;
import com.trantiendat.Model.PhanLoai;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhanLoaiFragment extends Fragment {
    View view;
    ArrayList<PhanLoai> phanLoaiArrayList;
    PhanLoaiAdapter phanLoaiAdapter;
    RecyclerView rcv_phanloai;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phan_loai, container, false);
        init();
        getData();
        return view;
    }
    private void init(){
        rcv_phanloai = view.findViewById(R.id.rcv_phanloai);
    }
    private void getData(){
        DataService dataService = APIService.getService();
        Call<List<PhanLoai>> callback = dataService.getDataPhanLoai();
        callback.enqueue(new Callback<List<PhanLoai>>() {
            @Override
            public void onResponse(Call<List<PhanLoai>> call, Response<List<PhanLoai>> response) {
                phanLoaiArrayList = (ArrayList<PhanLoai>) response.body();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
                phanLoaiAdapter = new PhanLoaiAdapter(phanLoaiArrayList,getActivity());
                rcv_phanloai.setLayoutManager(gridLayoutManager);
                rcv_phanloai.setHasFixedSize(true);
                rcv_phanloai.setAdapter(phanLoaiAdapter);
                phanLoaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PhanLoai>> call, Throwable t) {

            }
        });
    }
}