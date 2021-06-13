//package com.trantiendat.direction;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.trantiendat.Model.TaiKhoan;
//
//public class SessionManagement {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    String SHARE = "key";
//    String SESSION = "key_user";
//
//    public SessionManagement(Context context) {
//        sharedPreferences = context.getSharedPreferences(SHARE, context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//
//    }
//
//    public void saveSession(TaiKhoan taiKhoan) {
//// save user when user is login
//        String user = taiKhoan.getUser();
//        editor.putString(SESSION, user).commit();
//    }
//
//    public String getSession() {
//        // return user is saved
//        return sharedPreferences.getString(SESSION,"Fail");
//    }
//    public void removeSession(){
//        editor.putString(SESSION,"Fail").commit();
//    }
//}
