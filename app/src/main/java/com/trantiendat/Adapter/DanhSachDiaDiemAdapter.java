package com.trantiendat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class DanhSachDiaDiemAdapter extends RecyclerView.Adapter<DanhSachDiaDiemAdapter.viewHolder> {
    Context context;
    ArrayList<DiaDiem> diaDiemArrayList;

    public DanhSachDiaDiemAdapter(Context context, ArrayList<DiaDiem> diaDiemArrayList) {
        this.context = context;
        this.diaDiemArrayList = diaDiemArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachdiadiem, parent, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DiaDiem diaDiem = diaDiemArrayList.get(position);
        if (diaDiem == null) {
            return;
        }
        holder.tv_tenDiaDiem.setText(diaDiem.getTenDiaDiem());
        holder.tv_diachiDiaDiem.setText(diaDiem.getDiaChiDiaDiem());
        Glide.with(context).load(diaDiem.getHinhDiaDiem()).into(holder.imgv_hinhDiaDiem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietDiaDiemActivity.class);
                intent.putExtra("DiaDiem", diaDiemArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaDiemArrayList == null ? 0 : diaDiemArrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenDiaDiem, tv_diachiDiaDiem;
        ImageView imgv_hinhDiaDiem;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDiaDiem = itemView.findViewById(R.id.tv_tenDiaDiem);
            tv_diachiDiaDiem = itemView.findViewById(R.id.tv_diachiDiaDiem);
            imgv_hinhDiaDiem = itemView.findViewById(R.id.imgv_hinhDiaDiem);

        }
    }
}
