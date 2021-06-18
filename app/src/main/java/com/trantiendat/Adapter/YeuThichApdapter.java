package com.trantiendat.Adapter;

import android.app.Activity;
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
import com.trantiendat.Model.YeuThich;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class YeuThichApdapter extends RecyclerView.Adapter<YeuThichApdapter.viewHolder> {

    Activity context;
    ArrayList<DiaDiem> diaDiemArrayList;

    public YeuThichApdapter(Activity context, ArrayList<DiaDiem> diaDiemArrayList) {
        this.context = context;
        this.diaDiemArrayList = diaDiemArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeuthich, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DiaDiem diaDiem = diaDiemArrayList.get(position);
        if (diaDiem == null) {
            return;
        }
        holder.tv_tenyeuthich.setText(diaDiem.getTenDiaDiem());
       // holder.tv_address.setText(diaDiem.getDiaChiDiaDiem());
        holder.tv_rating.setText(diaDiem.getRatingDiaDiem());
        Glide.with(context).load(diaDiem.getHinhDiaDiem()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_yeuthich);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(diaDiemArrayList.get(position).getIDDiaDiem());
                Intent intent = new Intent(context, ChiTietDiaDiemActivity.class);
                intent.putExtra("DiaDiem", diaDiemArrayList.get(position));
                intent.putExtra("pos", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaDiemArrayList == null ? 0 : diaDiemArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenyeuthich, tv_address, tv_rating;
        ImageView imgv_yeuthich, imgv_rating;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenyeuthich = itemView.findViewById(R.id.tv_tenyeuthich);
            tv_address = itemView.findViewById(R.id.tv_address);
            imgv_yeuthich = itemView.findViewById(R.id.imgv_yeuthich);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            imgv_rating = itemView.findViewById(R.id.imgv_rating);
        }
    }
}
