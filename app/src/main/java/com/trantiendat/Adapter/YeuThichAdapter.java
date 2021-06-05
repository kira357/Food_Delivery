package com.trantiendat.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class YeuThichAdapter extends BaseAdapter {
    Activity context;
    ArrayList<YeuThich> yeuThichArrayList;

    public YeuThichAdapter(Activity context, ArrayList<YeuThich> yeuThichArrayList) {
        this.context = context;
        this.yeuThichArrayList = yeuThichArrayList;
    }

    @Override
    public int getCount() {
        return yeuThichArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return yeuThichArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class viewHolder {
        TextView tv_tenyeuthich, tv_diachiyeuthich;
        ImageView imgv_yeuthich;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_yeuthich, null, false);
            viewHolder.tv_tenyeuthich = convertView.findViewById(R.id.tv_tenyeuthich);
            viewHolder.tv_diachiyeuthich = convertView.findViewById(R.id.tv_diachiyeuthich);
            viewHolder.imgv_yeuthich = convertView.findViewById(R.id.imgv_yeuthich);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        YeuThich yeuThich = yeuThichArrayList.get(position);

        viewHolder.tv_tenyeuthich.setText(yeuThich.getTenDiaDiem());
        viewHolder.tv_diachiyeuthich.setText(yeuThich.getTenDiaDiem());
        Picasso.get().load(yeuThich.getHinhDiaDiem()).into(viewHolder.imgv_yeuthich);

        return convertView;
    }
}
