package com.trantiendat.Adapter;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trantiendat.food_delivery.uri.CartFragment;
import com.trantiendat.food_delivery.uri.FavouriteFragment;
import com.trantiendat.food_delivery.uri.HomeFragment;
import com.trantiendat.food_delivery.uri.InfoFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FavouriteFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new InfoFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
