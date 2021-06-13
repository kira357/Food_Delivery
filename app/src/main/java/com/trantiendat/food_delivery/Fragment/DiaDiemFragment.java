package com.trantiendat.food_delivery.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trantiendat.Adapter.DiaDiemAdapter;
import com.trantiendat.Database.PaginationScrollListener;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.NetworkCheck.ConnectionReceiver;
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
    private DiaDiemAdapter diaDiemAdapter;
    private TextView tvNetworkFailHome;
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
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ConnectionReceiver.isConnected()) {
                    tvNetworkFailHome.setVisibility(View.VISIBLE);
                } else {
                    tvNetworkFailHome.setVisibility(View.GONE);
                }
                handler.postDelayed(this, 2000);
            }
        }, 1000);
        init();
        Threads threads = new Threads();
        threads.execute();
        return view;
    }

    private void init() {
        rcv_DiaDiem = view.findViewById(R.id.rcv_DiaDiem);
        tvNetworkFailHome =view.findViewById(R.id.tvNetworkFailHome);

    }


    public class Threads extends AsyncTask<Void, Void, Void> {
        private void GetDataDiaDiem() {
            DataService dataService = APIService.getService();
            Call<List<DiaDiem>> callback = dataService.getDataDiaDiem();
            callback.enqueue(new Callback<List<DiaDiem>>() {
                @Override
                public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                    diaDiemArrayList = (ArrayList<DiaDiem>) response.body();

                    diaDiemAdapter = new DiaDiemAdapter(diaDiemArrayList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    rcv_DiaDiem.setLayoutManager(linearLayoutManager);
                    rcv_DiaDiem.setHasFixedSize(true);
                    rcv_DiaDiem.setAdapter(diaDiemAdapter);
                    diaDiemAdapter.notifyDataSetChanged();


                }

                @Override
                public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GetDataDiaDiem();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            rcv_DiaDiem.addItemDecoration(decoration);
        }
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

            if (listItem != null) {
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