package com.trantiendat.food_delivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSignUpActivity extends AppCompatActivity {
    private TextInputLayout Username_text_input, Email_text_input, Password_text_input, password_again_text_input;
    private TextInputEditText Username_edit_text, Email_edit_text, Password_edit_text, password_again_edit_text;
    private MaterialButton btn_SignIn, btn_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        init();
        checkText();
        setEvent();
    }

    private void init() {
        Username_text_input = findViewById(R.id.Username_text_input);
        Email_text_input = findViewById(R.id.Email_text_input);
        Password_text_input = findViewById(R.id.Password_text_input);
        password_again_text_input = findViewById(R.id.password_again_text_input);

        Username_edit_text = findViewById(R.id.Username_edit_text);
        Email_edit_text = findViewById(R.id.Email_edit_text);
        Password_edit_text = findViewById(R.id.Password_edit_text);
        password_again_edit_text = findViewById(R.id.password_again_edit_text);
        btn_SignIn = findViewById(R.id.btn_SignIn);
        btn_Cancel = findViewById(R.id.btn_Cancel);
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >=5;
    }

    private boolean isUserValid(@Nullable Editable text) {
        return text != null && text.length() >= 4;
    }

    private void setEvent() {
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username_edit_text.getText().toString();
                String password = Password_edit_text.getText().toString();
                String email = Email_edit_text.getText().toString();
                String password2 = password_again_edit_text.getText().toString();
                if (user.equals("") || password.equals("") || email.equals("") || password2.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(MainSignUpActivity.this, "Bạn cần phải nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
                if (password2.equals(password)) {
                    DangKi(user, password, email);
                    Intent intent = new Intent(MainSignUpActivity.this, LoginClickActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainSignUpActivity.this, "Vui lòng xác nhận lại password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                if (isUserValid(Username_edit_text.getText())) {
                    Username_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
        Email_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(Email_edit_text.getText())) {
                    Email_text_input.setError("wrong Email");
                } else {
                    Email_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(Email_edit_text.getText())) {
                    Email_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
        Password_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError(getString(R.string.shr_error_password));
                } else {
                    Password_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
        password_again_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(password_again_edit_text.getText())) {
                    password_again_text_input.setError("wrong pass");
                } else {
                    password_again_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(password_again_edit_text.getText())) {
                    password_again_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
    }

    private void DangKi(String user, String password, String email) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.DangKi(user, password, email);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketqua = response.body();
                if (ketqua.equals("Success")) {
                    Intent intent = new Intent(MainSignUpActivity.this, LoginClickActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainSignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainSignUpActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}