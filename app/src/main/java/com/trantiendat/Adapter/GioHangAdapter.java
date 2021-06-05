package com.trantiendat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.GioHang;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

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
            holder.tv_mota.setText(gioHang.getDiaChiDiaDiem());
            Glide.with(context).load(gioHang.getHinhMonAn()).placeholder(R.drawable.loop_black_48x48)
                    .error(R.drawable.error_black_48x48)
                    .into(holder.imgv_hinh);
        }
    }

    @Override
    public int getItemCount() {
        return gioHangArrayList == null ? 0 : gioHangArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten, tv_gia, tv_soluong, tv_mota;
        ImageButton btn_giam, btn_tang;
        ImageView imgv_hinh;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_ten = itemView.findViewById(R.id.tv_ten);
            tv_mota = itemView.findViewById(R.id.tv_mota);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            btn_giam = itemView.findViewById(R.id.btn_giam);
            btn_tang = itemView.findViewById(R.id.btn_tang);
            imgv_hinh = itemView.findViewById(R.id.imgv_hinh);

        }
    }
}
