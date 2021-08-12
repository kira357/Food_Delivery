package com.trantiendat.Adapter;

import android.app.Activity;
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
import com.trantiendat.Model.PhanLoai;
import com.trantiendat.food_delivery.ChiTietPhanLoaiActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhanLoaiAdapter extends RecyclerView.Adapter<PhanLoaiAdapter.viewHolder> {
    ArrayList<PhanLoai> phanLoaiArrayList;
    Context context;

    public PhanLoaiAdapter(ArrayList<PhanLoai> phanLoaiArrayList, Context context) {
        this.phanLoaiArrayList = phanLoaiArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phanloai, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PhanLoai phanLoai = phanLoaiArrayList.get(position);
        if (phanLoai == null) {
            return;
        }
        holder.tv_phanloai.setText(phanLoai.getPhanLoai());
        Glide.with(context).load(phanLoai.getHinh()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48).into(holder.imgv_phanloai);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietPhanLoaiActivity.class);
                intent.putExtra("PhanLoai",phanLoaiArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phanLoaiArrayList == null ? 0 : phanLoaiArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_phanloai;
        ImageView imgv_phanloai;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_phanloai = itemView.findViewById(R.id.tv_phanloai);
            imgv_phanloai = itemView.findViewById(R.id.imgv_phanloai);

        }
    }
}
