package com.trantiendat.food_delivery.Fragment.Cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.trantiendat.Adapter.GioHangAdapter;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.HoaDonActivity;
import com.trantiendat.food_delivery.MainActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {
    RecyclerView rcv_Order;
    View view;
    Button btn_chot;
    ArrayList<GioHang> gioHangArrayList;
    public static GioHangAdapter gioHangAdapter;

    SharedPreferences sharedPreferences;

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
        setRCV();
        return view;
    }

    private void init() {

        rcv_Order = view.findViewById(R.id.rcv_Order);
        btn_chot = view.findViewById(R.id.btn_chot);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String checkid;
            checkid = gioHangArrayList.get(position).getIDMonAn();
            gioHangArrayList.remove(position);

            DataService dataService = APIService.getService();
            Call<String> callback = dataService.xoaGioHang(checkid);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String ketqua = response.body();
                    if (ketqua.equals("Success")) {
                        Toast.makeText(getActivity(), "đã xoá thành công", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
            gioHangAdapter.notifyDataSetChanged();
        }
    };

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
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcv_Order);
                rcv_Order.setAdapter(gioHangAdapter);


                btn_chot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MainActivity.gioHangArrayList.size() > 0) {
                            String tong = "0";
                            Call<String> callback = dataService.taoHoaDon(tong);
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String id = response.body();
                                    if (Integer.parseInt(id) > 0) {
                                        Call<String> callback = dataService.insertIDhoadon(id);
                                        callback.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                String ketqua = response.body();
                                                Toast.makeText(getActivity(), "đã tạo hoá đơn" + ketqua, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), HoaDonActivity.class);
                                                intent.putExtra("id", ketqua);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        } else {
                            MainActivity.gioHangArrayList.add(new GioHang("0", "", "", "", "", ""));
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }


    public void setRCV() {
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv_Order.addItemDecoration(decoration);
    }

    public void ReloadView() {
        getFragmentManager().beginTransaction().detach(OrderFragment.this).attach(OrderFragment.this).commit();

        Toast.makeText(getActivity(), "Reload pager Order", Toast.LENGTH_SHORT).show();
    }

}
