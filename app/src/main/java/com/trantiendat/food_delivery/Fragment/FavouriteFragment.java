package com.trantiendat.food_delivery.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trantiendat.Adapter.YeuThichApdapter;
import com.trantiendat.Database.Database;
import com.trantiendat.Model.DiaDiem;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.food_delivery.LoginClickActivity;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteFragment extends Fragment {

    private RecyclerView rcv_yeuthich;

    private ArrayList<DiaDiem> diaDiemArrayList;
    private View view;
    private YeuThichApdapter yeuThichAdapter;
    private Database database;
    private ProgressBar progress_barYeuThich;
    private TextView tv_mess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        init();
        PrepareDB();
        addViews();

        return view;
    }

    private void init() {
        rcv_yeuthich = view.findViewById(R.id.rcv_yeuthich);
        progress_barYeuThich = view.findViewById(R.id.progress_barYeuThich);
        tv_mess = view.findViewById(R.id.tv_mess);
    }


    private void addViews() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có chắc là muốn bỏ yêu thích !!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int position = viewHolder.getAdapterPosition();
                                int checkid;
                                checkid = Integer.parseInt(diaDiemArrayList.get(position).getIDDiaDiem());
                                database.QueryData("DELETE FROM YeuThich WHERE ID= " + checkid);

                                DataService dataService = APIService.getService();
                                Call<String> callback = dataService.suaYeuThich(diaDiemArrayList.get(position).getIDDiaDiem(), -1);
                                callback.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua = response.body();
                                        if (ketqua.equals("Success")) {
                                            Toast.makeText(getActivity(), "đã bỏ lưu", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                                diaDiemArrayList.remove(position);
                                yeuThichAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                yeuThichAdapter.notifyDataSetChanged();
                            }
                        });
                builder.create().show();


            }
        };
        DataService dataService = APIService.getService();
        progress_barYeuThich.setVisibility(View.VISIBLE);
        Call<List<DiaDiem>> callback = dataService.getDataYeuThich();
        callback.enqueue(new Callback<List<DiaDiem>>() {
            @Override
            public void onResponse(Call<List<DiaDiem>> call, Response<List<DiaDiem>> response) {
                diaDiemArrayList = (ArrayList<DiaDiem>) response.body();
                if (diaDiemArrayList.size() > 0) {
                    yeuThichAdapter = new YeuThichApdapter(getActivity(), diaDiemArrayList);
                    rcv_yeuthich.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rcv_yeuthich.setHasFixedSize(true);
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcv_yeuthich);
                    rcv_yeuthich.setAdapter(yeuThichAdapter);
                    tv_mess.setVisibility(View.GONE);
                    progress_barYeuThich.setVisibility(View.GONE);
                } else {
                    tv_mess.setVisibility(View.VISIBLE);
                    progress_barYeuThich.setVisibility(View.GONE);
                }

                RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                rcv_yeuthich.addItemDecoration(decoration);
            }

            @Override
            public void onFailure(Call<List<DiaDiem>> call, Throwable t) {

            }

        });

    }

    private void PrepareDB() {

        database = new Database(getActivity(), "YeuThich.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS YeuThich(ID INTEGER PRIMARY KEY, " +
                "Ten TEXT, " + "diachi TEXT, " + "hinh TEXT," + "trangthai INTEGER)");
    }


    public void ReloadView() {
        getFragmentManager().beginTransaction().detach(FavouriteFragment.this).attach(FavouriteFragment.this).commit();
        Toast.makeText(getActivity(), "Reload pager Favourite", Toast.LENGTH_SHORT).show();
    }

}