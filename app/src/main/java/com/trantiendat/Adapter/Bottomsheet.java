package com.trantiendat.Adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.food_delivery.R;
import java.lang.Object;
import java.io.IOException;
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
    MarkerOptions place1, place2;
    FusedLocationProviderClient fusedLocationClient;
    Polyline currentPolyline;
    LatLng latitude, longitude;
    LatLng end_latitude, end_longitude;

    LatLng latLng;



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
       // Direction();

        getAddress();
        CreateMaps();

        return bottomSheetDialog;
    }

    private void init(View view) {
        tv_tenbottom = view.findViewById(R.id.tv_tenbottom);
        tv_diachibottom = view.findViewById(R.id.tv_diachibottom);
        imgv_bottom = view.findViewById(R.id.imgv_bottom);
     //   btn_direction = view.findViewById(R.id.btn_direction);
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
                place1 = new MarkerOptions();
                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addresses = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < addresses.size(); i++) {
                        Address myAddress = addresses.get(i);
                         latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        place1.position(latLng);
                        place1.title(tv_diachibottom.getText().toString());
                        maps.addMarker(place1);
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
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
                        place2 = new MarkerOptions();
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                         latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        place2.position(latLng);
                        place2.title("Your place");
                        maps.addMarker(place2);
                        maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

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

    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + latLng.latitude + "," + latLng.longitude);
        googleDirectionsUrl.append("&destination=" + latLng.latitude + "," + latLng.longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyDnixNN5PERph2banplmVWnaupVgWVjYyw");

        return googleDirectionsUrl.toString();
    }

    private void Direction() {
        btn_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object[] data = new Object[3];
                String url = getDirectionsUrl();
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                data[0] = maps;
                data[1] = url;
                data[2] = new LatLng(latLng.latitude, latLng.longitude);
                getDirectionsData.execute(data);
            }
        });

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
            currentPolyline = maps.addPolyline((PolylineOptions) values[0]);
        }
    }
}

