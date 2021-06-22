package com.trantiendat.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.direction.DirectionParser;
import com.trantiendat.direction.FetchURL;
import com.trantiendat.direction.TaskLoadedCallback;
import com.trantiendat.food_delivery.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Object;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Bottomsheet extends BottomSheetDialogFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, TaskLoadedCallback {
    private static final String KEY = "DiaDiem";
    private DiaDiem diaDiem;
    private TextView tv_tenbottom, tv_diachibottom;
    private ImageView imgv_bottom;
    private static View view;
    private Button btn_direction;
    GoogleMap maps;
    FusedLocationProviderClient fusedLocationClient;
    LatLng latLng1;
    LatLng latLng2;
    Polyline currentPolyline;


    public static Bottomsheet getInstance(DiaDiem diaDiem) {
        Bottomsheet bottomsheet = new Bottomsheet();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, diaDiem);
        bottomsheet.setArguments(bundle);
        return bottomsheet;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            diaDiem = (DiaDiem) bundle.getSerializable(KEY);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.item_bottomsheet, null);
        bottomSheetDialog.setContentView(viewDialog);
        init(viewDialog);
        setData();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) viewDialog.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        imgv_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        CreateMaps();
        getAddress();
        // getLocation2();


        return bottomSheetDialog;
    }

    private void init(View view) {
        tv_tenbottom = view.findViewById(R.id.tv_tenbottom);
        tv_diachibottom = view.findViewById(R.id.tv_diachibottom);
        imgv_bottom = view.findViewById(R.id.imgv_bottom);
    }

    private void setData() {
        if (diaDiem == null) {
            return;
        }
        tv_tenbottom.setText(diaDiem.getTenDiaDiem());
        tv_diachibottom.setText(diaDiem.getDiaChiDiaDiem());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        maps = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getLocation();

        maps.setMyLocationEnabled(true);
        maps.setOnMarkerClickListener(this);

    }

    private void CreateMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    private void getAddress() {
        tv_diachibottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = tv_diachibottom.getText().toString();
                List<Address> addresses = null;
                MarkerOptions place1 = new MarkerOptions();
                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addresses = geocoder.getFromLocationName(location, 5);
                        Address myAddress = addresses.get(0);
                        latLng1 = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        place1.position(latLng1);
                        place1.title(tv_diachibottom.getText().toString());
                        place1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        maps.addMarker(place1);
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 18));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        MarkerOptions place2 = new MarkerOptions();
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        latLng2 = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        place2.position(latLng2);
                        place2.title("Your place");
                        place2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 18));
                        maps.addMarker(place2);
                        addresses.get(0).getAddressLine(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Location null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLocation2() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                String locationDD = tv_diachibottom.getText().toString();
                List<Address> addresses1 = null;
                List<Address> addresses2 = null;
                MarkerOptions place1 = new MarkerOptions();
                MarkerOptions place2 = new MarkerOptions();
                LatLng latLng1;
                LatLng latLng2;
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        addresses1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                        addresses2 = geocoder.getFromLocationName(locationDD, 5);

                        latLng1 = new LatLng(addresses1.get(0).getLatitude(), addresses1.get(0).getLongitude());
                        place1.position(latLng1);
                        place1.title("Your place");
                        place1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 18));
                        maps.addMarker(place1);

                        latLng2 = new LatLng(addresses2.get(0).getLatitude(), addresses2.get(0).getLongitude());
                        place2.position(latLng2);
                        place2.title(tv_diachibottom.getText().toString());
                        place2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        maps.addMarker(place1);
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 18));
                        new FetchURL(getActivity()).execute(getDirectionsUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest, String directionmode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String sensor = "sensor=false";

        //String mode = "mode=driving";
        String mode = "mode=" + directionmode;

        String param = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"
                + param + "&key=" + R.string.google_api_key;
        return url;

    }

//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.connect();
//
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
            currentPolyline = maps.addPolyline((PolylineOptions) values[0]);
        }
    }

//    private class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            String data = "";
//
//            try {
//                data = downloadUrl(url[0]);
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            ParserTask parserTask = new ParserTask();
//            parserTask.execute(result);
//        }
//    }
//
//
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
//
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//            try {
//                jObject = new JSONObject(jsonData[0]);
//                DirectionParser parser = new DirectionParser();
//
//                routes = parser.parse(jObject);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList points = new ArrayList();
//            PolylineOptions lineOptions = new PolylineOptions();
//
//            for (int i = 0; i < result.size(); i++) {
//
//                List<HashMap<String, String>> path = result.get(i);
//
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap<String, String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                lineOptions.addAll(points);
//                lineOptions.width(12);
//                lineOptions.color(Color.RED);
//                lineOptions.geodesic(true);
//
//            }
//
//            // Drawing polyline in the Google Map
//
//            if (points.size() != 0)
//                maps.addPolyline(lineOptions);
//        }
//    }

}

