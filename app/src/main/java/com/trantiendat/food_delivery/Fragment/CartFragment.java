package com.trantiendat.food_delivery.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.trantiendat.Adapter.CartViewPagerAdapter;
import com.trantiendat.food_delivery.Fragment.Cart.OrderFragment;
import com.trantiendat.food_delivery.R;


public class CartFragment extends Fragment {

private TabLayout tablayout_Cart;
private ViewPager viewPager_Cart;
private View view;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_cart, container, false);
        tablayout_Cart = view.findViewById(R.id.tablayout_Cart);
        viewPager_Cart = view.findViewById(R.id.viewpager_Cart);
        CartViewPagerAdapter cartViewPagerAdapter = new CartViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager_Cart.setAdapter(cartViewPagerAdapter);

        tablayout_Cart.setupWithViewPager(viewPager_Cart);

        return view;
    }
    public void ReloadView(){
      
        Toast.makeText(getActivity(), "Reload pager Cart", Toast.LENGTH_SHORT).show();
    }
}