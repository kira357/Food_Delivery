package com.trantiendat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> quangCaoArrayList;

    public BannerAdapter(Context context, ArrayList<QuangCao> quangCaoArrayList) {
        this.context = context;
        this.quangCaoArrayList = quangCaoArrayList;
    }

    @Override
    public int getCount() {
        return quangCaoArrayList == null ? 0 : quangCaoArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    // định hình object và gán dữ liệu cho mỗi object
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        QuangCao quangCao = quangCaoArrayList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_quangcao, null);
        ImageView img_backgroundQuangCao = view.findViewById(R.id.img_backgroundQuangCao);
        TextView tv_noidungQuangCao = view.findViewById(R.id.tv_noidungQuangCao);

        Picasso.get().load(quangCao.getHInhQuangCao()).into(img_backgroundQuangCao);
        tv_noidungQuangCao.setText(quangCao.getNoiDungQuangCao());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
