//package com.trantiendat.Adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.location.Location;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.squareup.picasso.Picasso;
//import com.trantiendat.Model.LocationF;
//import com.trantiendat.food_delivery.R;
//
//import java.util.ArrayList;
//import java.util.zip.Inflater;
//
//public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
//    ArrayList<LocationF> locationArrayList ;
//    Activity context;
//
//    public LocationAdapter(ArrayList<LocationF> locationArrayList, Activity context) {
//        this.locationArrayList = locationArrayList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        LocationF locationF = locationArrayList.get(position);
//        if (locationF == null) {
//            return;
//        }
//        holder.tv_name.setText(locationF.getmNameLocation());
//        holder.tv_address.setText(locationF.getmAddLocation());
////        holder.Rating.setText(String.valueOf(locationF.getmRatingLocation()));
//        Picasso.get().load(locationF.getmPhotoLocation()).into(holder.imgv_location);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return locationArrayList == null ? 0 : locationArrayList.size() ;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView imgv_location;
//        private TextView tv_name, tv_address, Rating;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgv_location = itemView.findViewById(R.id.imgv_location);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_address = itemView.findViewById(R.id.tv_address);
////            Rating = itemView.findViewById(R.id.Rating);
//        }
//    }
//
//}
package com.trantiendat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.trantiendat.Model.ChiTietDiaDiem;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.MonAn;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaDiemAdapter extends RecyclerView.Adapter<DiaDiemAdapter.ViewHolder> {


    ArrayList<DiaDiem> diaDiemArrayList;
    Context context;



    public static int i = 0;

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
        holder.tv_name.setText(diaDiem.getTenDiaDiem());
        holder.tv_address.setText(diaDiem.getDiaChiDiaDiem());
        holder.tv_rating.setText(diaDiem.getRatingDiaDiem());
        Glide.with(context).load(diaDiem.getHinhDiaDiem()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_location);

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgv_location,imgv_rating;
        private TextView tv_name, tv_address,tv_rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_location = itemView.findViewById(R.id.imgv_location);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            imgv_rating = itemView.findViewById(R.id.imgv_rating);

        }
    }

}
