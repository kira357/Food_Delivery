package com.trantiendat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trantiendat.food_delivery.Fragment.Cart.OrderFragment;
import com.trantiendat.food_delivery.Fragment.Cart.PayFragment;
import com.trantiendat.food_delivery.Fragment.CartFragment;
import com.trantiendat.food_delivery.Fragment.FavouriteFragment;
import com.trantiendat.food_delivery.Fragment.HomeFragment;
import com.trantiendat.food_delivery.Fragment.InfoFragment;

public class CartViewPagerAdapter extends FragmentStatePagerAdapter {

    public CartViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OrderFragment();
            case 1:
                return new PayFragment();
            default:
                return new OrderFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Order";
            case  1:
                return "Pay";
            default:
                return "Order";
        }
    }
}
