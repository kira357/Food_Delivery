package com.trantiendat.food_delivery.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trantiendat.Adapter.GioHangAdapter;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;
import com.trantiendat.food_delivery.HoaDonActivity;
import com.trantiendat.food_delivery.LoginClickActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    RecyclerView rcv_Order;
    View view;
    Button btn_chot;
    ArrayList<GioHang> gioHangArrayList;
    public static GioHangAdapter gioHangAdapter;
    SessionManagement sessionManagement;
    ProgressBar progress_barCart;
    TextView tv_mess;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        sessionManagement = new SessionManagement(getActivity());
        init();
        getDataGoiHang();
        setEvent();
        setRCV();


        return view;
    }

    private void init() {

        rcv_Order = view.findViewById(R.id.rcv_Order);
        btn_chot = view.findViewById(R.id.btn_chot);
        progress_barCart = view.findViewById(R.id.progress_barCart);
        tv_mess = view.findViewById(R.id.tv_mess);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Bạn có chắc là muốn xoá !!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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
                                    Toast t1 = Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                                    t1.setGravity(Gravity.CENTER, 0, 0);
                                    t1.show();
                                }
                            });
                            gioHangAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            gioHangAdapter.notifyDataSetChanged();
                        }
                    });
            builder.create().show();

        }
    };

    private void getDataGoiHang() {
        DataService dataService = APIService.getService();
        progress_barCart.setVisibility(View.VISIBLE);
        Call<List<GioHang>> callback = dataService.getDataGioHang();
        callback.enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                gioHangArrayList = new ArrayList<>();
                gioHangArrayList = (ArrayList<GioHang>) response.body();
                if (gioHangArrayList.size() > 0) {
                    gioHangAdapter = new GioHangAdapter(gioHangArrayList, getActivity());
                    rcv_Order.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rcv_Order.setHasFixedSize(true);
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcv_Order);
                    rcv_Order.setAdapter(gioHangAdapter);

                    tv_mess.setVisibility(View.GONE);
                    progress_barCart.setVisibility(View.GONE);
                } else {
                    tv_mess.setVisibility(View.VISIBLE);
                    progress_barCart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {

            }
        });
    }

    private void setEvent() {
        btn_chot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManagement.CheckLogin()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("bạn có muốn chốt đơn hàng này không")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                    progressDialog.setMessage("Uploading...");
                                    progressDialog.show();
                                    String tong = "0";
                                    String id_user = sessionManagement.getUser().getIDUser();
                                    DataService dataService = APIService.getService();
                                    Call<String> callback = dataService.taoHoaDon(tong, id_user);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String id = response.body();
                                            if (Integer.parseInt(id) > 0) {
                                                Call<String> callback = dataService.insertIDhoadon(id);
                                                callback.enqueue(new Callback<String>() {
                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                        if (response != null) {
                                                            progressDialog.dismiss();
                                                            String ketqua = response.body();
                                                            Toast.makeText(getActivity(), "đã tạo hoá đơn" + ketqua, Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getActivity(), HoaDonActivity.class);
                                                            intent.putExtra("id_User", sessionManagement.getUser().getIDUser());
                                                            intent.putExtra("id", ketqua);
                                                            startActivity(intent);
                                                            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                                                        }
                                                    }


                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {
                                                        Toast t1 = Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                                                        t1.setGravity(Gravity.CENTER, 0, 0);
                                                        t1.show();
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast t1 = Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                                            t1.setGravity(Gravity.CENTER, 0, 0);
                                            t1.show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Vui lòng đăng nhập trước khi thanh toán")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent = new Intent(getActivity(), LoginClickActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();

                }
            }
        });
    }


    public void setRCV() {
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv_Order.addItemDecoration(decoration);
    }

    public void ReloadView() {
        getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();

        Toast.makeText(getActivity(), "Reload pager Cart", Toast.LENGTH_SHORT).show();
    }

}