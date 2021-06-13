package com.trantiendat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.GioHang;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.viewHolder> {
    ArrayList<GioHang> gioHangArrayList;
    Context context;



    public GioHangAdapter(ArrayList<GioHang> gioHangArrayList, Context context) {
        this.gioHangArrayList = gioHangArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        GioHang gioHang = gioHangArrayList.get(position);
        if (gioHang != null) {
            holder.tv_ten.setText(gioHang.getTenMonAn());
            holder.tv_gia.setText(gioHang.getGiaMonAn());
            holder.tv_diachi.setText(gioHang.getDiaChiDiaDiem());
            holder.tv_soluong.setText(gioHang.getSoLuong());
            Glide.with(context).load(gioHang.getHinhMonAn()).placeholder(R.drawable.loop_black_48x48)
                    .error(R.drawable.error_black_48x48)
                    .into(holder.imgv_hinh);
        }

        holder.cb_chon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.insertDataCTHD(gioHang.getIDMonAn(), gioHang.getSoLuong(), gioHang.getGiaMonAn(), gioHang.getGiaMonAn());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")) {
                                Toast.makeText(context, "bạn đã chọn món ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    holder.btn_tang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int soluong = Integer.parseInt(holder.tv_soluong.getText().toString());
                            soluong++;
                            holder.tv_soluong.setText(soluong + "");
                            int giaht = Integer.parseInt(gioHang.getGiaMonAn());
                            int thanhtien = giaht * soluong;
                            holder.tv_gia.setText(thanhtien + "");

                            DataService dataService1 = APIService.getService();
                            Call<String> callback = dataService1.updateDatahoadon(gioHang.getIDMonAn(), holder.tv_soluong.getText().toString(), holder.tv_gia.getText().toString());
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String ketqua1 = response.body();
                                    if (ketqua1.equals("Success")) {
                                        Toast.makeText(context, "bạn đã sử số lượng ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Lỗi ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                        }
                    });
                    holder.btn_giam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int soluong = Integer.parseInt(holder.tv_soluong.getText().toString());
                            soluong--;
                            if (soluong > 0) {
                                holder.tv_soluong.setText(soluong + "");
                                int giamon = Integer.parseInt(gioHang.getGiaMonAn());
                                Long thanhtien = Long.parseLong(holder.tv_gia.getText().toString()) - giamon;
                                holder.tv_gia.setText(String.valueOf(thanhtien));

                                DataService dataService1 = APIService.getService();
                                Call<String> callback = dataService1.updateDatahoadon(gioHang.getIDMonAn(), holder.tv_soluong.getText().toString(), holder.tv_gia.getText().toString());
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua1 = response.body();
                                        if (ketqua1.equals("Success")) {
                                            Toast.makeText(context, "Bạn đã sử số lượng ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Lỗi ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                                if (holder.tv_gia.getText().toString().equals(gioHang.getGiaMonAn())) {
                                    holder.btn_giam.setEnabled(false);
                                }
                            } else {
                                holder.tv_soluong.setText("0");
                                Call<String> callback = dataService.deleteDatahoadon(gioHang.getIDMonAn());
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua = response.body();
                                        if (ketqua.equals("Success")) {
                                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Lỗi ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    });

                } else {
                    holder.tv_gia.setText(gioHang.getGiaMonAn());
                    holder.tv_soluong.setText("");
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.deleteDatahoadon(gioHang.getIDMonAn());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")) {
                                Toast.makeText(context, "Bỏ check ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Lỗi ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangArrayList == null ? 0 : gioHangArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten, tv_gia, tv_soluong, tv_diachi;
        ImageButton btn_giam, btn_tang;
        ImageView imgv_hinh;
        CheckBox cb_chon;
        Button btn_chot;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_ten = itemView.findViewById(R.id.tv_ten);
            tv_diachi = itemView.findViewById(R.id.tv_diachi);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            cb_chon = itemView.findViewById(R.id.cb_chon);
            btn_giam = itemView.findViewById(R.id.btn_giam);
            btn_tang = itemView.findViewById(R.id.btn_tang);

            // btn_chot = itemView.findViewById(R.id.btn_chot);
            imgv_hinh = itemView.findViewById(R.id.imgv_hinh);


        }
    }
}
