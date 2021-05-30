package com.trantiendat.food_delivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginClickActivity extends AppCompatActivity {
    private Button btn_Login, btn_Cancel;
    private TextInputLayout Username_text_input, Password_text_input;
    private TextInputEditText Username_edit_text, Password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_click);
        init();
    }

    private void init() {
        btn_Login = findViewById(R.id.btn_Login);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        Username_text_input = findViewById(R.id.Username_text_input);
        Username_edit_text = findViewById(R.id.Username_edit_text);
        Password_text_input = findViewById(R.id.Password_text_input);
        Password_edit_text = findViewById(R.id.Password_edit_text);
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
    private boolean isUserValid(@Nullable Editable text) {
        return text != null && text.length() >= 10;
    }
    private void setEvent(){
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError("wrong password");
                } else {
                    Password_text_input.setError(null); // Clear the error
                }
                if (!isUserValid(Username_edit_text.getText())) {
                    Username_text_input.setError("wrong name");
                } else {
                    Username_text_input.setError(null); // Clear the error
                }
            }
        });
        Username_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(Username_edit_text.getText())) {
                    Username_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
        Password_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
    }

}