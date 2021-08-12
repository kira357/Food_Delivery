package com.trantiendat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.ThongTin;
import com.trantiendat.food_delivery.ChiTietHoaDonActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class thongtinCTHDAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongTin> thongTinArrayList;

    public thongtinCTHDAdapter(Context context, ArrayList<ThongTin> thongTinArrayList) {
        this.context = context;
        this.thongTinArrayList = thongTinArrayList;
    }

    @Override
    public int getCount() {
        return thongTinArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

   public class viewHolder {
        TextView tv_tendiachi_thongtin, tv_tenmonan_thongtin, tv_giamonan_thongtin,tv_soluongmonan_thongtin;
        ImageView imgv_hinhmonan_thongtin;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_thongtincthd, null);
            viewHolder.tv_tendiachi_thongtin = convertView.findViewById(R.id.tv_tendiachi_thongtin);
            viewHolder.tv_tenmonan_thongtin = convertView.findViewById(R.id.tv_tenmonan_thongtin);
            viewHolder.tv_giamonan_thongtin = convertView.findViewById(R.id.tv_giamonan_thongtin);
            viewHolder.tv_soluongmonan_thongtin = convertView.findViewById(R.id.tv_soluongmonan_thongtin);
            viewHolder.imgv_hinhmonan_thongtin = convertView.findViewById(R.id.imgv_hinhmonan_thongtin);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        ThongTin ThongTin = thongTinArrayList.get(position);
        viewHolder.tv_tendiachi_thongtin.setText(ThongTin.getTenDiaDiem());
        viewHolder.tv_tenmonan_thongtin.setText(ThongTin.getTenMonAn());
        viewHolder.tv_giamonan_thongtin.setText(ThongTin.getGia());
        viewHolder.tv_soluongmonan_thongtin.setText(ThongTin.getSoLuong());
        Glide.with(context).load(ThongTin.getHinhMonAn()).into(viewHolder.imgv_hinhmonan_thongtin);

        return convertView;
    }
}
