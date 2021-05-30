package com.trantiendat.food_delivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainSignInActivity extends AppCompatActivity {
    private TextInputLayout Username_text_input, Email_text_input, Password_text_input, password_again_text_input;
    private TextInputEditText Username_edit_text, Email_edit_text, Password_edit_text, password_again_edit_text;
    private MaterialButton btn_SignIn, btn_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_in);
        init();
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
        btn_Cancel =findViewById(R.id.btn_Cancel);
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private void setEvent(){
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(Password_edit_text.getText())) {
                    Password_text_input.setError(getString(R.string.shr_error_password));
                } else {
                    Password_text_input.setError(null); // Clear the error
                }
                if (!isPasswordValid(Username_edit_text.getText())) {
                    Username_text_input.setError("wrong name");
                } else {
                    Username_text_input.setError(null); // Clear the error
                }
                if (!isPasswordValid(Email_edit_text.getText())) {
                    Email_text_input.setError("wrong Email");
                } else {
                    Email_text_input.setError(null); // Clear the error
                }
                if (!isPasswordValid(password_again_edit_text.getText())) {
                    password_again_text_input.setError("wrong pass");
                } else {
                    password_again_text_input.setError(null); // Clear the error
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
        Email_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(Email_edit_text.getText())) {
                    Email_text_input.setError(null); //Clear the error
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
        password_again_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(password_again_edit_text.getText())) {
                    Password_text_input.setError(null); //Clear the error
                }
                return false;
            }
        });
    }
}