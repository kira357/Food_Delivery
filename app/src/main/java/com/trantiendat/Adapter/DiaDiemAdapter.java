
package com.trantiendat.Adapter;


import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.ChiTietDiaDiem;
import com.trantiendat.Model.DiaDiem;

import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;


public class DiaDiemAdapter extends RecyclerView.Adapter<DiaDiemAdapter.ViewHolder> {

    ArrayList<DiaDiem> diaDiemArrayList;
    Context context;


    public DiaDiemAdapter(ArrayList<DiaDiem> diaDiemArrayList, Context context) {
        this.diaDiemArrayList = diaDiemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaDiem diaDiem = diaDiemArrayList.get(position);
        if (diaDiem == null) {
            return;
        }
        holder.linearLayout_Uudai.setVisibility(View.VISIBLE);
        holder.tv_name.setText(diaDiem.getTenDiaDiem());
        holder.tv_address.setText(diaDiem.getDiaChiDiaDiem());
        holder.tv_rating.setText(diaDiem.getRatingDiaDiem());
        Glide.with(context).load(diaDiem.getHinhDiaDiem()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_location);
        holder.tv_uuDai.setText(diaDiem.getUuDai());

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
        if (holder.tv_uuDai.getText().equals("")) {
            holder.linearLayout_Uudai.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return diaDiemArrayList == null ? 0 : diaDiemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgv_location, imgv_rating;
        private TextView tv_name, tv_address, tv_rating, tv_uuDai;
        private LinearLayout linearLayout_Uudai;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_location = itemView.findViewById(R.id.imgv_location);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_uuDai = itemView.findViewById(R.id.tv_uuDai);
            linearLayout_Uudai = itemView.findViewById(R.id.linearLayout_Uudai);

        }
    }


}
