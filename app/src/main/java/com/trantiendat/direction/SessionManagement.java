package com.trantiendat.direction;

import android.content.Context;
import android.content.SharedPreferences;

import com.trantiendat.Model.TaiKhoan;
import com.trantiendat.Model.User;

import java.util.ArrayList;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String MANE = "save";
    String KEY_LOGIN = "islogin";
    String KEY_USER = "user_name";
    String KEY_ID = "user_id";
    String KEY_DIACHI = "user_diachi";
    String KEY_HINH = "user_hinh";

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MANE, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveSession(boolean isLogin) {
        // save user when user is login
        editor.putBoolean(KEY_LOGIN, isLogin);
        editor.commit();
    }

    public boolean Check() {
        // return user is saved
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public void saveUser(User user) {
            editor.putString(KEY_USER, user.getTen());
            editor.putString(KEY_ID, user.getIDUser());
            editor.putString(KEY_DIACHI, user.getDiachi());
            editor.putString(KEY_HINH, user.getHinh());
            editor.commit();
    }

    public User getUser() {
        return new User(sharedPreferences.getString(KEY_ID, null), sharedPreferences.getString(KEY_USER, null), sharedPreferences.getString(KEY_DIACHI, null), sharedPreferences.getString(KEY_HINH, null));
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

}
