package com.trantiendat.food_delivery.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.trantiendat.Model.User;
import com.trantiendat.direction.SessionManagement;
import com.trantiendat.food_delivery.LoginActivity;
import com.trantiendat.food_delivery.R;

public class InfoFragment extends Fragment implements View.OnClickListener {

    private MaterialButton btn_LogOut;
    private MaterialButton btn_LogIn;
    private TextView tv_tenUser, tv_Diachi;
    private ImageView imgv_hinhUser, imgv_edit;
    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleApiClient client;
    GoogleSignInOptions googleSignInOptions;
    LinearLayout linearLayout;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    View view;
    SessionManagement sessionManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        sessionManagement = new SessionManagement(getActivity());
        init();
        CheckLogin();
        //getinfoUser();
        setEvent();
        //showAcount();

        return view;

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            updateUI();
//        }
//
//    }

    private void init() {
        btn_LogOut = view.findViewById(R.id.btn_LogOut);
        btn_LogIn = view.findViewById(R.id.btn_LogIn);
        tv_tenUser = view.findViewById(R.id.tv_tenUser);
        tv_Diachi = view.findViewById(R.id.tv_Diachi);
        imgv_hinhUser = view.findViewById(R.id.imgv_hinhUser);
        imgv_edit = view.findViewById(R.id.imgv_edit);
    }

    private void setEvent() {
        btn_LogOut.setOnClickListener(this);
        btn_LogIn.setOnClickListener(this);
        imgv_edit.setOnClickListener(this);

    }

    private void getinfoUser() {
        String ID = sessionManagement.getUser().getIDUser();
        String Ten = sessionManagement.getUser().getTen();
        String Diachi = sessionManagement.getUser().getDiachi();
        String hinh = sessionManagement.getUser().getHinh();

        tv_tenUser.setText(Ten);
        tv_Diachi.setText(Diachi);
        Glide.with(getActivity()).load(hinh).placeholder(R.drawable.loop_black_48x48)
                .error(R.drawable.error_black_48x48)
                .into(imgv_hinhUser);

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

    private void CheckLogin() {
        if (!sessionManagement.Check()) {
            tv_tenUser.setVisibility(View.VISIBLE);
            tv_Diachi.setVisibility(View.VISIBLE);
        } else {
            getinfoUser();
        }
    }
//    private void openDialog(){
//        DialogCustom dialogCustom = new DialogCustom();
//        dialogCustom.show(getActivity().getSupportFragmentManager(),"Dialog");
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LogIn:
                String text = tv_tenUser.getText().toString();
//                openFragment(text);
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_LogOut:
//                mAuth.signOut();
//                LoginManager.getInstance().logOut();
                sessionManagement.logout();
                sessionManagement.saveSession(false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.imgv_edit:
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog_edit);
                dialog.setCanceledOnTouchOutside(false);
                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText edt_ten = dialog.findViewById(R.id.edt_tenEdit);
                final EditText edt_diachi = dialog.findViewById(R.id.edt_diachiEdit);
                final MaterialButton btn_cancelEdit = dialog.findViewById(R.id.btn_cancelEdit);
                final MaterialButton btn_Edit = dialog.findViewById(R.id.btn_Edit);
                edt_ten.setText(sessionManagement.getUser().getTen() + "");
                edt_diachi.setText(sessionManagement.getUser().getDiachi() + "");
                btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_Edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();


                // openDialog();
                break;

        }
    }

//    @Override
//    public void getEdit(String Ten, String Diachi) {
//        tv_tenUser.setText(Ten);
//        tv_Diachi.setText(Diachi);
//    }
}