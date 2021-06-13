package com.trantiendat.food_delivery.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trantiendat.Adapter.YeuThichApdapter;
import com.trantiendat.Database.Database;
import com.trantiendat.Model.YeuThich;
import com.trantiendat.food_delivery.R;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    RecyclerView rcv_yeuthich;
    ArrayList<YeuThich> yeuThichArrayList;
    YeuThich yeuThich;
    View view;
    YeuThichApdapter yeuThichAdapter;
    Database database;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        init();
        //GetDataDiaDiem();
        PrepareDB();
        addViews();
        loadData();
        return view;
    }

    private void init() {
        rcv_yeuthich = view.findViewById(R.id.rcv_yeuthich);
    }




    private void loadData() {

        Cursor c = database.GetData("SELECT * FROM YeuThich");
        yeuThichArrayList.clear();
        while (c.moveToNext()) {
            String ID = c.getString(0);
            String TenMon = c.getString(1);
            String diachi = c.getString(2);
            String hinh = c.getString(3);
            int fav = c.getInt(4);
            yeuThichArrayList.add(new YeuThich(ID, TenMon, diachi, hinh, fav));
        }
        yeuThichAdapter.notifyDataSetChanged();
    }


    private void addViews() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int checkid ;
                checkid = Integer.parseInt(yeuThichArrayList.get(position).getId());
                yeuThichArrayList.remove(position);
                database.QueryData("DELETE FROM YeuThich WHERE ID= "+ checkid);
                    Toast.makeText(getActivity(), "đã bỏ lưu", Toast.LENGTH_SHORT).show();
                    loadData();

                yeuThichAdapter.notifyDataSetChanged();
            }
        };

        yeuThichArrayList = new ArrayList<>();
        yeuThichAdapter = new YeuThichApdapter(getActivity(), yeuThichArrayList);
        rcv_yeuthich.setLayoutManager( new LinearLayoutManager(getActivity()));
        rcv_yeuthich.setHasFixedSize(true);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcv_yeuthich);
        rcv_yeuthich.setAdapter(yeuThichAdapter);

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