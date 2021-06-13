package com.trantiendat.food_delivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trantiendat.Model.TaiKhoan;
import com.trantiendat.Service.API;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClickActivity extends AppCompatActivity {
    private Button btn_Login, btn_Cancel;
    private TextInputLayout Username_text_input, Password_text_input;
    private TextInputEditText Username_edit_text, Password_edit_text;
    private CheckBox ckb_rememberPass;
    SharedPreferences sharedPreferences;
    String ketqua;
    TaiKhoan taiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_click);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

        getSaveDate();
        init();
        checkText();
        setEvent();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        CheckSession();
//
//    }

//    private void CheckSession() {
//        SessionManagement sessionManagement = new SessionManagement(LoginClickActivity.this);
//        String isLogin = sessionManagement.getSession();
//        if(isLogin != "Fail"){
//            // move to menu
//            moveToMenu();
//        }else {
//            return;
//        }
//    }

    private void init() {
        btn_Login = findViewById(R.id.btn_Login);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        Username_text_input = findViewById(R.id.Username_text_input);
        Username_edit_text = findViewById(R.id.Username_edit_text);
        Password_text_input = findViewById(R.id.Password_text_input);
        Password_edit_text = findViewById(R.id.Password_edit_text);
        ckb_rememberPass = findViewById(R.id.ckb_rememberPass);
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 5;
    }

    private boolean isUserValid(@Nullable Editable text) {
        return text != null && text.length() >= 4;
    }

    private void setEvent() {

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    TaiKhoan taiKhoan = new TaiKhoan(user, password);
//                    SessionManagement sessionManagement = new SessionManagement(LoginClickActivity.this);
//                    sessionManagement.saveSession(taiKhoan);
//                    moveToMenu();
                String user = Username_edit_text.getText().toString();
                String password = Password_edit_text.getText().toString();
                if (user.equals("") || password.equals("")) {
                    Toast.makeText(LoginClickActivity.this, "Vui lòng nhập đầy đủ ", Toast.LENGTH_SHORT).show();
                } else {
                    Login(user, password);
                }
            }
        });

    }

    private void checkText() {
        Username_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isUserValid(Username_edit_text.getText())) {
                    Username_text_input.setError("wrong name");
                } else {
                    Username_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(Username_edit_text.getText())) {
                    Username_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
        Password_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError("wrong password");
                } else {
                    Password_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
    }

    private void Login(String user, String password) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.DangNhap(user, password);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ketqua = response.body();
                if (ketqua.equals(user)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (ckb_rememberPass.isChecked()) {
                        editor.putString(getResources().getString(R.string.SHARE), "log_in");


                    } else {
                        editor.putString(getResources().getString(R.string.SHARE), "log_out");

                    }
                    editor.commit();
                    Intent intent = new Intent(LoginClickActivity.this, HoaDonActivity.class);
                    intent.putExtra("use",ketqua);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginClickActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getSaveDate() {
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.SHARE), "");

        if (loginStatus.equals("log_in")) {
            startActivity(new Intent(LoginClickActivity.this, MainMenuActivity.class));
        }
    }
}