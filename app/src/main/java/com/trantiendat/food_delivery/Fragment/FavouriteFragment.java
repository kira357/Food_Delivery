package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Adapter.YeuThichAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteFragment extends Fragment {
    ListView lv_yeuthich;
    ArrayList<YeuThich> yeuThichArrayList;
    YeuThich yeuThich;
    View view;
    YeuThichAdapter yeuThichAdapter;
//    Databases databases;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        init();
        GetDataDiaDiem();
        return view;
    }

    private void init() {
        lv_yeuthich = view.findViewById(R.id.lv_yeuthich);
    }

    private void GetDataDiaDiem() {
        DataService dataService = APIService.getService();
        Call<List<YeuThich>> callback = dataService.getDataDanhsachyeuthich();
        callback.enqueue(new Callback<List<YeuThich>>() {
            @Override
            public void onResponse(Call<List<YeuThich>> call, Response<List<YeuThich>> response) {
                yeuThichArrayList = (ArrayList<YeuThich>) response.body();
                 yeuThichAdapter = new YeuThichAdapter(getActivity(), yeuThichArrayList);
                lv_yeuthich.setAdapter(yeuThichAdapter);
                yeuThichAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<YeuThich>> call, Throwable t) {

            }
        });
    }

    public void ReloadView() {
        yeuThichAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Reload pager Favourite", Toast.LENGTH_SHORT).show();
    }

}