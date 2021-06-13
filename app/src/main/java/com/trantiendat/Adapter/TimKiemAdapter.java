package com.trantiendat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class TimKiemAdapter extends BaseAdapter {
    Context context;
    ArrayList<DiaDiem> diaDiemArrayList;

    public TimKiemAdapter(Context context, ArrayList<DiaDiem> diaDiemArrayList) {
        this.context = context;
        this.diaDiemArrayList = diaDiemArrayList;
    }

    @Override
    public int getCount() {
        return diaDiemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return diaDiemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        private ImageView imgv_location;
        private TextView tv_name, tv_address;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder ;
        if (convertView == null) {
            holder  = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_search, null);
            holder.imgv_location = convertView.findViewById(R.id.imgv_location);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_address = convertView.findViewById(R.id.tv_address);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final DiaDiem diaDiem = diaDiemArrayList.get(position);
        holder.tv_name.setText(diaDiem.getTenDiaDiem());
        holder.tv_address.setText(diaDiem.getDiaChiDiaDiem());
        Glide.with(context).load(diaDiem.getHinhDiaDiem()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(holder.imgv_location);
        return convertView;
    }
}
