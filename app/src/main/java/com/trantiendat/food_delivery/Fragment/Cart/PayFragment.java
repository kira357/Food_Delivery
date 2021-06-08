package com.trantiendat.food_delivery.Fragment.Cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trantiendat.Adapter.ChiTietHoaDonAdapter;
import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayFragment extends Fragment {

    View view;
    TextView tv_tong;
    Button btn_thanhtoan;
    RecyclerView rcv_cthd;
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;

    public PayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pay, container, false);
        init();
       // getDataCTHD();
      //  saveBill();
        return view;
    }

    private void init() {
        rcv_cthd = view.findViewById(R.id.rcv_cthd);
        tv_tong = view.findViewById(R.id.tv_tong);
        btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
    }

//    private void getDataCTHD() {
//        DataService dataService = APIService.getService();
//        Call<List<ChiTietHoaDon>> callback = dataService.getDataCTHD();
//        callback.enqueue(new Callback<List<ChiTietHoaDon>>() {
//            @Override
//            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
//                chiTietHoaDonArrayList = (ArrayList<ChiTietHoaDon>) response.body();
//                chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(chiTietHoaDonArrayList, getActivity());
//
//                rcv_cthd.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rcv_cthd.setHasFixedSize(true);
//                rcv_cthd.setAdapter(chiTietHoaDonAdapter);
//                chiTietHoaDonAdapter.notifyDataSetChanged();
//
//                long tong = 0;
//                for (int i = 0; i < chiTietHoaDonArrayList.size(); i++) {
//                    tong += (Long.parseLong(chiTietHoaDonArrayList.get(i).getThanhTien()));
//                }
//                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//                tv_tong.setText(decimalFormat.format(tong) + "Đ");
//
//                long finalTong = tong;
//                btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DataService dataService = APIService.getService();
//                        Call<String> callback = dataService.saveDatahoadon(finalTong);
//                        callback.enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                String ketqua = response.body();
//                                if(ketqua.equals("Success")){
//                                    Toast.makeText(getActivity(), "đã thanh toán", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void saveBill() {
//
//    }

}