package com.trantiendat.food_delivery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.trantiendat.Adapter.ViewPagerAdapter;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.food_delivery.Fragment.CartFragment;
import com.trantiendat.food_delivery.Fragment.FavouriteFragment;
import com.trantiendat.food_delivery.Fragment.HomeFragment;
import com.trantiendat.food_delivery.Fragment.InfoFragment;

public class MainMenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION = 1;
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    private Menu action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
        // requestPermissionLocation();
        init();
        setAdapter();
        actionViewPager();
    }

    private void init() {
        mViewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void actionViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.actionHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.actionFavourite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.actionCart).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.actionInfo).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        mViewPager.setCurrentItem(0);
                        HomeFragment homeFragment = (HomeFragment) mViewPager.getAdapter().instantiateItem(mViewPager, 0);
                        homeFragment.ReloadView();
                        break;
                    case R.id.actionFavourite:
                        mViewPager.setCurrentItem(1);
                        FavouriteFragment favouriteFragment = (FavouriteFragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1);
                        favouriteFragment.ReloadView();
                        break;
                    case R.id.actionCart:
                        mViewPager.setCurrentItem(2);
                        CartFragment cartFragment = (CartFragment) mViewPager.getAdapter().instantiateItem(mViewPager, 2);
                        cartFragment.ReloadView();
                        break;
                    case R.id.actionInfo:
                        mViewPager.setCurrentItem(3);
                        InfoFragment infoFragment = (InfoFragment) mViewPager.getAdapter().instantiateItem(mViewPager, 3);
                        infoFragment.ReloadView();
                        break;
                }
                return false;
            }
        });
    }


}