package com.trantiendat.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.viewHolder> {
    Activity context;
    ArrayList<MonAn> monAnArrayList;
    MonAn monAn;

    public MonAnAdapter(Activity context, ArrayList<MonAn> monAnArrayList) {
        this.context = context;
        this.monAnArrayList = monAnArrayList;
    }

    @NonNull
    @Override
    public MonAnAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan, parent, false);
        return new MonAnAdapter.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MonAnAdapter.viewHolder holder, int position) {
         monAn = monAnArrayList.get(position);
        if (monAn == null) {
            return;
        }
        holder.tv_tenMonAn.setText(monAn.getTenMonAn());
        holder.tv_loaiMonAn.setText(monAn.getLoaiMonAn());
        holder.tv_giaMonAn.setText(monAn.getGiaMonAn());
        Glide.with(context).load(monAn.getHinhMonAn()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_hinhMonAn);
    }

    @Override
    public int getItemCount() {
        return monAnArrayList == null ? 0 : monAnArrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenMonAn, tv_loaiMonAn, tv_giaMonAn, tv_soluong;
        ImageView imgv_hinhMonAn;
        ImageButton btn_add;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenMonAn = itemView.findViewById(R.id.tv_tenMonAn);
            tv_loaiMonAn = itemView.findViewById(R.id.tv_loaiMonAn);
            tv_giaMonAn = itemView.findViewById(R.id.tv_giaMonAn);

            imgv_hinhMonAn = itemView.findViewById(R.id.imgv_hinhMonAn);
            btn_add = itemView.findViewById(R.id.btn_add);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String SoLuong = "1";
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.insertDatagiohang(monAnArrayList.get(getPosition()).getIDMonAn(),SoLuong);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")) {
                                Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });

        }
    }
}
