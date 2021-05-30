package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.trantiendat.Adapter.LocationAdapter;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.QuangCao;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiaDiemFragment extends Fragment {

    private RecyclerView rcv_DiaDiem;
    private ArrayList<DiaDiem> diaDiemArrayList;
    private LocationAdapter locationAdapter;
    DiaDiem diaDiem;
    View view;

    public DiaDiemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_dia_diem, container, false);
        init();
         GetDataDiaDiem();
        return view;
    }
    private void init() {
          rcv_DiaDiem = view.findViewById(R.id.rcv_DiaDiem);

    }
    private void GetDataDiaDiem() {
        DataService dataService = APIService.getService();
        Call<List<DiaDiem>> callback = dataService.getDataDiaDiem();
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                //  Log.d("aaa", "onResponse: " + diaDiemArrayList.get(0).getTenDiaDiem())
                rcv_DiaDiem.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcv_DiaDiem.setHasFixedSize(true);
                locationAdapter = new LocationAdapter(diaDiemArrayList, getActivity());
                rcv_DiaDiem.setAdapter(locationAdapter);
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

            }
        });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}