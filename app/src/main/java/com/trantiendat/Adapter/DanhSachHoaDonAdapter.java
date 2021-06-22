package com.trantiendat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trantiendat.Model.HoaDon;
import com.trantiendat.food_delivery.ChiTietHoaDonActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class DanhSachHoaDonAdapter extends BaseAdapter {
    Context context;
    ArrayList<HoaDon> hoaDonArrayList;

    public DanhSachHoaDonAdapter(Context context, ArrayList<HoaDon> hoaDonArrayList) {
        this.context = context;
        this.hoaDonArrayList = hoaDonArrayList;
    }

    @Override
    public int getCount() {
        return hoaDonArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class viewHolder {
        TextView tv_tendon, tv_tongtien, tv_ngaydat,tv_xemthem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_danhsachhoadon, null);
            viewHolder.tv_tendon = convertView.findViewById(R.id.tv_tendon);
            viewHolder.tv_tongtien = convertView.findViewById(R.id.tv_tongtien);
            viewHolder.tv_ngaydat = convertView.findViewById(R.id.tv_ngaydat);
            viewHolder.tv_xemthem = convertView.findViewById(R.id.tv_xemthem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        HoaDon hoaDon = hoaDonArrayList.get(position);
        viewHolder.tv_tendon.setText(hoaDon.getIDHoaDon());
        viewHolder.tv_ngaydat.setText(hoaDon.getNgayMua());
        viewHolder.tv_tongtien.setText(hoaDon.getTongHoaDon());


        return convertView;
    }
}
