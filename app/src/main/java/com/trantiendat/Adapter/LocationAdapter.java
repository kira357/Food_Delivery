package com.trantiendat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trantiendat.Model.LocationF;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    ArrayList<LocationF> locationArrayList ;
    Activity context;

    public LocationAdapter(ArrayList<LocationF> locationArrayList, Activity context) {
        this.locationArrayList = locationArrayList;
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
        LocationF locationF = locationArrayList.get(position);
        if (locationF == null) {
            return;
        }
        holder.tv_name.setText(locationF.getmNameLocation());
        holder.tv_address.setText(locationF.getmAddLocation());
//        holder.Rating.setText(String.valueOf(locationF.getmRatingLocation()));
        Picasso.get().load(locationF.getmPhotoLocation()).into(holder.imgv_location);

    }

    @Override
    public int getItemCount() {
        return locationArrayList == null ? 0 : locationArrayList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgv_location;
        private TextView tv_name, tv_address, Rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_location = itemView.findViewById(R.id.imgv_location);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
//            Rating = itemView.findViewById(R.id.Rating);
        }
    }

}
