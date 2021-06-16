//package com.trantiendat.food_delivery.Fragment;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.trantiendat.Adapter.LocationAdapter;
//
//import com.trantiendat.Database.AsynTaskURL;
//import com.trantiendat.Model.DiaDiem;
//import com.trantiendat.Model.LocationF;
//import com.trantiendat.Service.APIService;
//import com.trantiendat.Service.DataService;
//import com.trantiendat.food_delivery.R;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class HomeFragment extends Fragment {
//    private RecyclerView lv_LocationF;
//
//    private ProgressBar progressBar_load;
//    private ArrayList<LocationF> locationFArrayList;
//    private ArrayList<DiaDiem> diaDiemArrayList;
//    private LocationAdapter locationAdapter;
//    View view;
//    LocationF locationF;
//
//    public HomeFragment() {
//
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        lv_LocationF = view.findViewById(R.id.lv_LocationF);
//        progressBar_load = view.findViewById(R.id.progressBar_load);
//        setData();
//        AsynTaskURL asynTaskURL = new AsynTaskURL(getActivity());
//        asynTaskURL.execute();
//        GetData();
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_home, container, false);
//       // GetData();
//        return view;
//    }
//
//    public void setData() {
//        locationFArrayList = new ArrayList<>();
//        lv_LocationF.setHasFixedSize(true);
//        lv_LocationF.setLayoutManager(new LinearLayoutManager(getActivity()));
//        locationAdapter = new LocationAdapter(locationFArrayList, getActivity());
//        lv_LocationF.setAdapter(locationAdapter);
//        locationAdapter.notifyDataSetChanged();
//
//    }
////    public void setData() {
////        locationFArrayList = new ArrayList<>();
////        lv_LocationF.setHasFixedSize(true);
////        lv_LocationF.setLayoutManager(new LinearLayoutManager(getActivity()));
////        locationAdapter = new LocationAdapter(locationFArrayList, getActivity());
////        lv_LocationF.setAdapter(locationAdapter);
////        locationAdapter.notifyDataSetChanged();
////
////    }
//
//
//
//    public void ReloadView() {
//        Toast.makeText(getActivity(), "Reload pager home", Toast.LENGTH_SHORT).show();
//    }
//
//
//    private void GetData() {
//        DataService dataService = APIService.getService();
//        Call<List<DiaDiem>> callback = dataService.getDataDiaDiem();
//        callback.enqueue(new Callback<List<DiaDiem>>() {
//            @Override
//            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
//                ArrayList<DiaDiem> diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
//                Log.d("aaa", "onResponse: " + diaDiemArrayList.get(0).getTenDiaDiem());
//
//            }
//
//            @Override
//            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {
//
//            }
//        });
//    }
//
////    private class FoodTask extends android.os.AsyncTask<Void, Void, Void> {
////
////        @Override
////        protected void onPreExecute() {
////            super.onPreExecute();
////            progressBar_load.setVisibility(View.VISIBLE);
////            progressBar_load.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
////        }
////
////        @Override
////        protected void onPostExecute(Void avoid) {
////            super.onPostExecute(avoid);
////            progressBar_load.setVisibility(View.GONE);
////            progressBar_load.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
////            locationAdapter.notifyDataSetChanged();
////        }
////
////        @Override
////        protected Void doInBackground(Void... voids) {
////
////            try {
////                String url = "https://food.grab.com/vn/vi/restaurants";
////                Document doc = Jsoup.connect(url).timeout(6000).get();
////                Elements data = doc.select("div.ant-layout div");
////             //Log.d("TAG", "doInBackground: "+data.size());
////                for (Element E : data.select("div.ant-col-24.RestaurantListCol___1FZ8V.ant-col-md-12.ant-col-lg-6")) {
////                    locationF = new LocationF();
////                    String Decs = E.select(" div.basicInfoContainer___1DcNs div.basicInfoRow___UZM8d.cuisine___T2tCh").text();
////                    String name = E.select(" div.ant-col-24.colInfo___3iLqj.ant-col-md-24.ant-col-lg-24 h6.name___2epcT").text();
////                    // String photo = E.select(" div.ant-col-24.colPhoto___3vb-o.ant-col-md-24.ant-col-lg-24 div.promoTag___IYhfm div.container____Youi div.placeholder___1xbBh restoPhoto___3nncy img.realImage___2TyNE").attr("src");
////
////
////                    locationF.setmNameLocation(name);
////                    locationF.setmAddLocation(Decs);
////                    // locationF.setmPhotoLocation(photo);
////                    Log.d("TAG", "doInBackground: " + name);
////                    locationFArrayList.add(locationF);
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            return null;
////        }
////
////    }
//
//
//}
//
//
//
package com.trantiendat.food_delivery.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.HoaDonActivity;
import com.trantiendat.food_delivery.MainActivity;
import com.trantiendat.food_delivery.MainMenuActivity;
import com.trantiendat.food_delivery.R;
import com.trantiendat.food_delivery.TimKiemActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION = 1;
    ViewPager viewPager;
    View view;
    TextView tv_vitri;
    SearchView sv_TimKiem;
    DiaDiem diaDiem;
    FusedLocationProviderClient fusedLocationProviderClient;


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        requestPermissionLocation();
        init();
        TimKiem();
        return view;
    }

    private void init() {
        sv_TimKiem = view.findViewById(R.id.sv_TimKiem);
        tv_vitri = view.findViewById(R.id.tv_vitri);
    }

    private void TimKiem() {
        sv_TimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimKiemActivity.class);
                startActivity(intent);
            }
        });

    }

    public void ReloadView() {

        getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
        Toast.makeText(getActivity(), "Reload pager home", Toast.LENGTH_SHORT).show();

    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    public void requestPermissionLocation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           // Toast.makeText(getActivity(), "Premission granted", Toast.LENGTH_SHORT).show();
            getLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Premission grandted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Premission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        tv_vitri.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Location null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}



