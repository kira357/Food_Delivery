package com.trantiendat.food_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trantiendat.Adapter.ViewPagerAdapter;
import com.trantiendat.food_delivery.uri.CartFragment;
import com.trantiendat.food_delivery.uri.FavouriteFragment;
import com.trantiendat.food_delivery.uri.HomeFragment;
import com.trantiendat.food_delivery.uri.InfoFragment;

public class MainMenu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_LogOut;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
//        //btn_LogOut = findViewById(R.id.btn_LogOut);
//        btn_LogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                LoginManager.getInstance().logOut();
//                updateUI();
//            }
//        });
//
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
                switch (item.getItemId()){
                    case R.id.actionHome:
                        mViewPager.setCurrentItem(0);
                        HomeFragment homeFragment = (HomeFragment) mViewPager.getAdapter().instantiateItem(mViewPager,0);
                        homeFragment.ReloadView();
                        break;
                    case R.id.actionFavourite:
                        mViewPager.setCurrentItem(1);
                        FavouriteFragment favouriteFragment = (FavouriteFragment) mViewPager.getAdapter().instantiateItem(mViewPager,1);
                        favouriteFragment.ReloadView();
                        break;
                    case R.id.actionCart:
                        mViewPager.setCurrentItem(2);
                        CartFragment  cartFragment = (CartFragment) mViewPager.getAdapter().instantiateItem(mViewPager,2);
                        cartFragment.ReloadView();
                        break;
                    case R.id.actionInfo:
                        mViewPager.setCurrentItem(3);
                        InfoFragment infoFragment = (InfoFragment) mViewPager.getAdapter().instantiateItem(mViewPager,3);
                        infoFragment.ReloadView();
                        break;
                }
                return false;
            }
        });
    }

//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            updateUI();
//        }
//
//    }
//
//    private void updateUI() {
//        Toast.makeText(this, "Loggin Success", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainMenu.this, MainLogin.class);
//        startActivity(intent);
//        finish();
//    }
}