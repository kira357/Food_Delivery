package com.trantiendat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trantiendat.Model.LocationF;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class LocationAdapterList extends BaseAdapter {
    Activity context;
    ArrayList<LocationF> locationFArrayList;

    public LocationAdapterList(Activity context, ArrayList<LocationF> locationFArrayList) {
        this.context = context;
        this.locationFArrayList = locationFArrayList;
    }

    @Override
    public int getCount() {
        return locationFArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationFArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class viewHolder {
        TextView tv_name, tv_address;
        ImageView imgv_location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_location, null, false);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_address = convertView.findViewById(R.id.tv_address);
            viewHolder.imgv_location = convertView.findViewById(R.id.imgv_location);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        LocationF locationF = locationFArrayList.get(position);

        viewHolder.tv_name.setText(locationF.getmNameLocation());
        viewHolder.tv_address.setText(locationF.getmAddLocation());
        Picasso.get().load(locationF.getmPhotoLocation()).into(viewHolder.imgv_location);

        return convertView;
    }
}
