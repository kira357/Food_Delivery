package com.trantiendat.food_delivery.uri;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
import com.trantiendat.Adapter.LocationAdapter;

import com.trantiendat.Database.AsynTaskURL;
import com.trantiendat.Model.LocationF;
import com.trantiendat.food_delivery.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView lv_LocationF;

    ProgressBar progressBar_load;
    private ArrayList<LocationF> locationFArrayList;
    private LocationAdapter locationAdapter;
    View view;
    LocationF locationF;

    public HomeFragment() {

    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_LocationF = view.findViewById(R.id.lv_LocationF);
        progressBar_load = view.findViewById(R.id.progressBar_load);
        AsynTaskURL asynTaskURL = new AsynTaskURL(getActivity());
        asynTaskURL.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }


//    public void setData() {
//        locationFArrayList = new ArrayList<>();
//        lv_LocationF.setHasFixedSize(true);
//        lv_LocationF.setLayoutManager(new LinearLayoutManager(getActivity()));
//        locationAdapter = new LocationAdapter(locationFArrayList, getActivity());
//        lv_LocationF.setAdapter(locationAdapter);
//        locationAdapter.notifyDataSetChanged();
//
//    }

    public void ReloadView() {
        Toast.makeText(getActivity(), "Reload pager home", Toast.LENGTH_SHORT).show();
    }



   /* private class FoodTask extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar_load.setVisibility(View.VISIBLE);
            progressBar_load.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            progressBar_load.setVisibility(View.GONE);
            progressBar_load.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            locationAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://food.grab.com/vn/vi/restaurants";
                Document doc = Jsoup.connect(url).timeout(6000).get();
                Elements data = doc.select("div.ant-layout div");
                //Log.d("TAG", "doInBackground: "+data.size());
                for (Element E : data.select("div.ant-col-24.RestaurantListCol___1FZ8V.ant-col-md-12.ant-col-lg-6")) {
                    locationF = new LocationF();
                    String Decs = E.select(" div.basicInfoContainer___1DcNs div.basicInfoRow___UZM8d.cuisine___T2tCh").text();
                    String name = E.select(" div.ant-col-24.colInfo___3iLqj.ant-col-md-24.ant-col-lg-24 h6.name___2epcT").text();
                    // String photo = E.select(" div.ant-col-24.colPhoto___3vb-o.ant-col-md-24.ant-col-lg-24 div.promoTag___IYhfm div.container____Youi div.placeholder___1xbBh restoPhoto___3nncy img.realImage___2TyNE").attr("src");


                    locationF.setmNameLocation(name);
                    locationF.setmAddLocation(Decs);
                    // locationF.setmPhotoLocation(photo);
                    Log.d("TAG", "doInBackground: " + name);
                    locationFArrayList.add(locationF);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }*/


}



