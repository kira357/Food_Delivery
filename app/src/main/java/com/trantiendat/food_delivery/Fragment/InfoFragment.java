package com.trantiendat.food_delivery.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trantiendat.food_delivery.LoginActivity;
import com.trantiendat.food_delivery.R;

public class InfoFragment extends Fragment {

    private MaterialButton btn_LogOut;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    View view;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        init();
        setEvent();
        return view;

    }

    private void init() {
        btn_LogOut = view.findViewById(R.id.btn_LogOut);
    }

    private void setEvent() {
        btn_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                LoginManager.getInstance().logOut();
                updateUI();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }

    }

    private void updateUI() {
        Toast.makeText(getActivity(), "Log out Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void ReloadView() {
        Toast.makeText(getActivity(), "Reload pager Info", Toast.LENGTH_SHORT).show();
    }
}