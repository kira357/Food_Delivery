package com.trantiendat.food_delivery.Fragment.Cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Adapter.GioHangAdapter;
import com.trantiendat.Adapter.MonAnAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {
    RecyclerView rcv_Order;
    View view;
    ArrayList<GioHang> gioHangArrayList;
   public static GioHangAdapter gioHangAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        init();
        getDataGoiHang();
        return view;
    }

    private void init() {

        rcv_Order = view.findViewById(R.id.rcv_Order);
    }

    private void getDataGoiHang() {
        DataService dataService = APIService.getService();
        Call<List<GioHang>> callback = dataService.getDataGioHang();
        callback.enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                gioHangArrayList = new ArrayList<>();
                gioHangArrayList = (ArrayList<GioHang>) response.body();
                gioHangAdapter = new GioHangAdapter(gioHangArrayList, getActivity());
                rcv_Order.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcv_Order.setHasFixedSize(true);
                rcv_Order.setAdapter(gioHangAdapter);
                gioHangAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }


}