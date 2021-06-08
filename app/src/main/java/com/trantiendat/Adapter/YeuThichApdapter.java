package com.trantiendat.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class YeuThichApdapter extends RecyclerView.Adapter<YeuThichApdapter.viewHolder> {

    Activity context;
    ArrayList<YeuThich> yeuThichArrayList;

    public YeuThichApdapter(Activity context, ArrayList<YeuThich> yeuThichArrayList) {
        this.context = context;
        this.yeuThichArrayList = yeuThichArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeuthich, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        YeuThich yeuThich = yeuThichArrayList.get(position);
        if (yeuThich == null) {
            return;
        }
        holder.tv_tenyeuthich.setText(yeuThich.getTen());
        holder.tv_diachiyeuthich.setText(yeuThich.getDiachi());
        Glide.with(context).load(yeuThich.getHinh()).into(holder.imgv_yeuthich);
    }

    @Override
    public int getItemCount() {
        return yeuThichArrayList == null ? 0 : yeuThichArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenyeuthich, tv_diachiyeuthich;
        ImageView imgv_yeuthich;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenyeuthich = itemView.findViewById(R.id.tv_tenyeuthich);
            tv_diachiyeuthich = itemView.findViewById(R.id.tv_diachiyeuthich);
            imgv_yeuthich = itemView.findViewById(R.id.imgv_yeuthich);
        }
    }
}
