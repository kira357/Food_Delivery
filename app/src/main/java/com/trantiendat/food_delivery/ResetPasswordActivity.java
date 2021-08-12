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

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout Username_text_inputReset, Password_text_inputReset, NewPassword_text_input;
    private TextInputEditText Username_edit_textReset, Password_edit_textReset, NewPassword_edit_text;
    private MaterialButton btn_LoginUserReset, btn_CancelReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
        checkText();
        setEvent();
    }

    private void init() {
        btn_LoginUserReset = findViewById(R.id.btn_LoginUserReset);
        btn_CancelReset = findViewById(R.id.btn_CancelReset);
        Username_text_inputReset = findViewById(R.id.Username_text_inputReset);
        Username_edit_textReset = findViewById(R.id.Username_edit_textReset);
        Password_text_inputReset = findViewById(R.id.Password_text_inputReset);
        Password_edit_textReset = findViewById(R.id.Password_edit_textReset);
        NewPassword_text_input = findViewById(R.id.NewPassword_text_input);
        NewPassword_edit_text = findViewById(R.id.NewPassword_edit_text);
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 5;
    }

    private boolean isUserValid(@Nullable Editable text) {
        return text != null && text.length() >= 4;
    }

    private void checkText() {
        Username_edit_textReset.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isUserValid(Username_edit_textReset.getText())) {
                    Username_text_inputReset.setError("user name must be length > 4");
                } else {
                    Username_text_inputReset.setError(null); // Clear the error
                }
                if (isPasswordValid(Username_edit_textReset.getText())) {
                    Username_text_inputReset.setError(null); //Clear the error
                }
                return false;
            }
        });
        Password_edit_textReset.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(Password_edit_textReset.getText())) {
                    Password_text_inputReset.setError("wrong password");
                } else {
                    Password_text_inputReset.setError(null); // Clear the error
                }
                if (isPasswordValid(Password_edit_textReset.getText())) {
                    Password_text_inputReset.setError(null); //Clear the error
                }
                return false;
            }
        });
        NewPassword_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!isPasswordValid(NewPassword_edit_text.getText())) {
                    NewPassword_text_input.setError("wrong password");
                } else {
                    NewPassword_text_input.setError(null); // Clear the error
                }
                if (isPasswordValid(NewPassword_edit_text.getText())) {
                    NewPassword_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
    }

    private void ResetPassword(String user, String newpassword) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.resetDataPassword(user, newpassword);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketqua = response.body();
                if (ketqua.equals("Success")) {
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginClickActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ResetPasswordActivity.this, "reset thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void setEvent() {
        btn_LoginUserReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username_edit_textReset.getText().toString();
                String password = Password_edit_textReset.getText().toString();
                String passwordConfirm = NewPassword_edit_text.getText().toString();

                if (user.equals("") || password.equals("") || passwordConfirm.equals("")) {
                    Toast.makeText(ResetPasswordActivity.this, "Bạn cần phải nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
                if (passwordConfirm.equals(password)) {
                    ResetPassword(user, password);
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng xác nhận lại password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_CancelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}