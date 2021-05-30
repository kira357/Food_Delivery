package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trantiendat.Adapter.BannerAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BannerFragment extends Fragment {
    private ArrayList<QuangCao> quangCaoArrayList;
    private BannerAdapter bannerAdapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    View view;
    DiaDiem diaDiem;
    Runnable runnable;
    Handler handler;
    public BannerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        init();
        GetDataQuangCao();
        return view;
    }
    private void init() {
        viewPager = view.findViewById(R.id.vp_QuangCao);
        circleIndicator = view.findViewById(R.id.cid_loadQuangCao);
    }

    int currentItem = 1;

    private void GetDataQuangCao() {
        DataService dataService = APIService.getService();
        Call<List<QuangCao>> callback = dataService.getDataQuangCao();
        callback.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                quangCaoArrayList = (ArrayList<QuangCao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(), quangCaoArrayList);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager.getAdapter().getCount()) {
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4000);
                    }
                };
                handler.postDelayed(runnable, 4000);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {

            }
        });

    }
}