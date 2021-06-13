package com.trantiendat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.MainActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuangCaoAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> quangCaoArrayList;

    public QuangCaoAdapter(Context context, ArrayList<QuangCao> quangCaoArrayList) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_quangcao, null);
        QuangCao quangCao = quangCaoArrayList.get(position);
        ImageView img_backgroundQuangCao = view.findViewById(R.id.img_backgroundQuangCao);
        TextView tv_noidungQuangCao = view.findViewById(R.id.tv_noidungQuangCao);

        Glide.with(context).load(quangCao.getHinhQuangCao()).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(img_backgroundQuangCao);
        tv_noidungQuangCao.setText(quangCao.getNoiDungQuangCao());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChiTietDiaDiemActivity.class);
                intent.putExtra("QuangCao", quangCaoArrayList.get(position));

                context.startActivity(intent);

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
