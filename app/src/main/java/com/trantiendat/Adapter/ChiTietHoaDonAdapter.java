package com.trantiendat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.viewHolder> {
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    Context context;

    public ChiTietHoaDonAdapter(ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList, Context context) {
        this.chiTietHoaDonArrayList = chiTietHoaDonArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = chiTietHoaDonArrayList.get(position);
        if (chiTietHoaDon == null )
        {
            return;
        }
        holder.tv_tendiachi.setText(chiTietHoaDon.getTenDiaDiem());
        holder.tv_diachidiadiem.setText(chiTietHoaDon.getDiaChiDiaDiem());
        holder.tv_tenmonan.setText(chiTietHoaDon.getTenMonAn());
        holder.tv_giamonan.setText(chiTietHoaDon.getGia());
        holder.tv_soluongmonan.setText(chiTietHoaDon.getSoLuong());
        Glide.with(context).load(chiTietHoaDon.getHinhMonAn()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_hinhmonan);
    }

    @Override
    public int getItemCount() {
        return chiTietHoaDonArrayList == null ? 0 : chiTietHoaDonArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tendiachi, tv_diachidiadiem, tv_tenmonan, tv_giamonan, tv_soluongmonan;
        ImageView imgv_hinhmonan;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_hinhmonan = itemView.findViewById(R.id.imgv_hinhmonan);
            tv_tendiachi = itemView.findViewById(R.id.tv_tendiachi);
            tv_diachidiadiem = itemView.findViewById(R.id.tv_diachidiadiem);
            tv_tenmonan = itemView.findViewById(R.id.tv_tenmonan);
            tv_giamonan = itemView.findViewById(R.id.tv_giamonan);
            tv_soluongmonan = itemView.findViewById(R.id.tv_soluongmonan);
        }
    }
}
