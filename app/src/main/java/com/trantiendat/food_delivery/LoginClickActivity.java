package com.trantiendat.food_delivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trantiendat.Model.User;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClickActivity extends AppCompatActivity {
    private static final String LIST = "CTHD";
    private MaterialButton btn_LoginUser, btn_Cancel;
    private TextInputLayout Username_text_input, Password_text_input;
    private TextInputEditText Username_edit_text, Password_edit_text;
    private CheckBox ckb_rememberPass;
    private TextView tv_SignUp;
    private TextView tv_resetPassword;
    SessionManagement sessionManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_click);

        sessionManagement = new SessionManagement(getApplication());
        CheckLogin();
        init();
        Asyn asyn = new Asyn();
        asyn.execute();
        setEvent();
    }

    private void init() {
        btn_LoginUser = findViewById(R.id.btn_LoginUser);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        Username_text_input = findViewById(R.id.Username_text_input);
        Username_edit_text = findViewById(R.id.Username_edit_text);
        Password_text_input = findViewById(R.id.Password_text_input);
        Password_edit_text = findViewById(R.id.Password_edit_text);
        ckb_rememberPass = findViewById(R.id.ckb_rememberPass);
        tv_SignUp = findViewById(R.id.tv_SignUp);
        tv_resetPassword = findViewById(R.id.tv_resetPassword);
    }


    private void setEvent() {
        btn_LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username_edit_text.getText().toString();
                String password = Password_edit_text.getText().toString();
                if (user.equals("") || password.equals("")) {
                    Toast.makeText(LoginClickActivity.this, "Vui lòng nhập đầy đủ ", Toast.LENGTH_SHORT).show();
                } else {
                    Login(user, password);

                }
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginClickActivity.this, MainSignUpActivity.class);
                startActivity(intent);
            }
        });
        tv_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginClickActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void Login(String user, String password) {
        DataService dataService = APIService.getService();
        Call<ArrayList<User>> callback = dataService.DangNhap(user, password);
        callback.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> userArrayList = response.body();
                if (userArrayList != null) {
                    if (ckb_rememberPass.isChecked()) {
                        for (int i = 0; i < userArrayList.size(); i++) {
                            User userInfo = userArrayList.get(i);
                            sessionManagement.saveUser(userInfo);
                        }
                        sessionManagement.saveSession(true);
                        Intent intent = new Intent(LoginClickActivity.this, MainMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        for (int i = 0; i < userArrayList.size(); i++) {
                            User userInfo = userArrayList.get(i);
                            sessionManagement.saveUser(userInfo);
                        }
                        sessionManagement.saveSession(false);
                        Intent intent = new Intent(LoginClickActivity.this, MainMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    Intent intent = new Intent(LoginClickActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(LoginClickActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginClickActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast t1 = Toast.makeText(LoginClickActivity.this, "Vui lòng kiểm tra mật khẩu", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
            }
        });
    }

    private void CheckLogin() {
        if (!sessionManagement.CheckLogin()) {
            Toast.makeText(this, "vui Long dang nhap", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginClickActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public class Asyn extends AsyncTask {
        private boolean isPasswordValid(@Nullable Editable text) {
            return text != null && text.length() >= 5;
        }

        private boolean isUserValid(@Nullable Editable text) {
            return text != null && text.length() >= 4;
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            checkText();
            return null;
        }
    }

}