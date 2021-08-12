package com.trantiendat.food_delivery.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.trantiendat.Adapter.DanhSachHoaDonAdapter;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.User;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;
import com.trantiendat.food_delivery.DanhSachHoaDonActivity;
import com.trantiendat.food_delivery.LoginActivity;
import com.trantiendat.food_delivery.R;
import com.trantiendat.getRealPathImage.RealPathUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoFragment extends Fragment implements View.OnClickListener {

    private static final int MY_REQUEST_CODE = 123;
    private static final int RESULT_OK = -1;
    private MaterialButton btn_LogOut;
    private MaterialButton btn_LogIn;
    private EditText tv_tenUser, tv_Diachi;
    private ImageView imgv_edit, imgv_save;
    private ImageView imgv_hinhUser;
    private GoogleSignInAccount mGoogleSignInAccount;
    TextView tv_soluongdon;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private LinearLayout linearLayout, linearLayoutSetting, linearlayoutAll,layout_info;
    private ArrayList<HoaDon> hoaDonArrayList;
    View view;
    SessionManagement sessionManagement;
    ArrayList<User> userArrayList;
    ProgressBar progress_bar;
    private Bitmap bitmap;
    private Activity mActivity;
    String id;
    String realPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        sessionManagement = new SessionManagement(getActivity());
        mAuth = FirebaseAuth.getInstance();
        init();
        CheckLogin();
        //GetLoginFB();
        setSoLuong();
        setEvent();

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void init() {
        btn_LogOut = view.findViewById(R.id.btn_LogOut);
        btn_LogIn = view.findViewById(R.id.btn_LogIn);
        tv_tenUser = view.findViewById(R.id.tv_tenUser);
        tv_Diachi = view.findViewById(R.id.tv_Diachi);
        imgv_hinhUser = view.findViewById(R.id.imgv_hinhUser);
        imgv_edit = view.findViewById(R.id.imgv_edit);
        imgv_save = view.findViewById(R.id.imgv_save);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearlayoutAll = view.findViewById(R.id.linearlayoutAll);
        tv_soluongdon = view.findViewById(R.id.tv_soluongdon);
        linearLayoutSetting = view.findViewById(R.id.linearLayoutSetting);
        layout_info = view.findViewById(R.id.layout_info);
        progress_bar = view.findViewById(R.id.progress_bar);

        id = sessionManagement.getUser().getIDUser();

    }

    private void setEvent() {
        btn_LogOut.setOnClickListener(this);
        btn_LogIn.setOnClickListener(this);
        imgv_edit.setOnClickListener(this);
        imgv_save.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        linearLayoutSetting.setOnClickListener(this);
        imgv_hinhUser.setEnabled(false);

    }

    private void getinfoUser() {
        String ID = sessionManagement.getUser().getIDUser();
        if (getActivity() == null) {
            return;
        }
        progress_bar.setVisibility(View.VISIBLE);
        linearlayoutAll.setVisibility(View.GONE);
        DataService dataService = APIService.getService();
        Call<List<User>> callback = dataService.getThongtinUser(ID);
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userArrayList = (ArrayList<User>) response.body();
                if (isAdded()) {
                    if (response != null) {
                        for (int i = 0; i < userArrayList.size(); i++) {
                            User user = userArrayList.get(i);
                            String Ten_user = user.getTen();
                            String diachi = user.getDiachi();
                            tv_tenUser.setText(Ten_user);
                            tv_Diachi.setText(diachi);
                            Glide.with(mActivity).load(user.getHinh()).placeholder(R.drawable.loop_black_48x48)
                                    .error(R.drawable.error_black_48x48)
                                    .into(imgv_hinhUser);
                            linearlayoutAll.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);
                            //progressDialog.dismiss();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // progressDialog.dismiss();
            }
        });

    }


    public void ReloadView() {
        getFragmentManager().beginTransaction().detach(InfoFragment.this).attach(InfoFragment.this).commit();
        Toast.makeText(getActivity(), "Reload pager Info", Toast.LENGTH_SHORT).show();
    }

    private void CheckLogin() {
        if (!sessionManagement.CheckLogin()) {
            tv_tenUser.setVisibility(View.VISIBLE);
            tv_Diachi.setVisibility(View.VISIBLE);
           // getinfoUser();
        } else {
            imgv_edit.setVisibility(View.VISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            getinfoUser();
        }


        if (AccessToken.getCurrentAccessToken() != null) {
            imgv_edit.setVisibility(View.INVISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        else {

        }
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (mGoogleSignInAccount != null) {
          //  imgv_edit.setVisibility(View.INVISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            String email = mGoogleSignInAccount.getEmail();
            String ten = mGoogleSignInAccount.getDisplayName();
            String id = mGoogleSignInAccount.getId();
            tv_tenUser.setText(ten);
            tv_Diachi.setText(email);
            Glide.with(getActivity()).load(mGoogleSignInAccount.getPhotoUrl()).placeholder(R.drawable.loop_black_48x48)
                    .error(R.drawable.error_black_48x48).into(imgv_hinhUser);
        } else {


        }
    }

    private void GetLoginFB() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    LoginManager.getInstance().logOut();
                    tv_tenUser.setText("");
                    tv_Diachi.setText("");
                    imgv_hinhUser.setImageResource(0);
                    Toast.makeText(getActivity(), "User logged out ", Toast.LENGTH_SHORT).show();
                } else {
                    sessionManagement.getIdFb();
                    loadUserProfile(currentAccessToken);
                }
            }
        };
    }


    private void loadUserProfile(AccessToken mAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(mAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String id = object.getString("id");
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    tv_tenUser.setText(first_name + "" + last_name);
                    tv_Diachi.setText(email);
                    Glide.with(getActivity()).load(image_url).placeholder(R.drawable.loop_black_48x48)
                            .error(R.drawable.error_black_48x48).into(imgv_hinhUser);


                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "first_name,last_name,email,id");
        request.setParameters(bundle);
        request.executeAsync();
    }

    private void setSoLuong() {
        String id_user = sessionManagement.getUser().getIDUser();
        DataService dataService = APIService.getService();
        Call<List<HoaDon>> callback = dataService.getDataDanhsachhoadon(id_user);
        callback.enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                hoaDonArrayList = (ArrayList<HoaDon>) response.body();
                tv_soluongdon.setText(hoaDonArrayList.size() + "");

            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
            }
        });
    }

    private void permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, MY_REQUEST_CODE);
            } else {
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }


    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), 1);
    }

    private void configImage() {
        File file = new File(realPath);
        String filePath = file.getAbsolutePath();
        String[] mangTenfile = filePath.split("\\.");
        filePath = mangTenfile[0] + System.currentTimeMillis() + "." + mangTenfile[1];
        Log.d("TAG", "configImage: " + filePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadfile", filePath, requestBody);
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.getHinhUser(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String message = response.body();
                    if (message.length() > 0) {
                        DataService dataServiceUser = APIService.getService();
                        Call<String> callback = dataServiceUser.updateHinhUser(id, APIService.url + "imageUser/" + message);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("Success")) {
                                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Log.d("TAG", "onFailure: "+ t.getMessage());
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "permission denied ", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            realPath = RealPathUtil.getRealPath(getActivity(), filePath);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                imgv_hinhUser.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LogIn:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_LogOut:
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                sessionManagement.logout();
                sessionManagement.saveSession(false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //getActivity().finish();
                break;

            case R.id.imgv_edit:
                tv_tenUser.setEnabled(true);
                tv_Diachi.setEnabled(true);
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(tv_tenUser, inputMethodManager.SHOW_IMPLICIT);
                imgv_edit.setVisibility(View.INVISIBLE);
                imgv_save.setVisibility(View.VISIBLE);
                imgv_hinhUser.setEnabled(true);
                imgv_hinhUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission();
                    }
                });

                break;
            case R.id.imgv_save:
                saveInfo();
                configImage();
                imgv_edit.setVisibility(View.VISIBLE);
                imgv_save.setVisibility(View.INVISIBLE);
                imgv_hinhUser.setEnabled(false);
                tv_tenUser.setEnabled(false);
                tv_Diachi.setEnabled(false);
                tv_tenUser.setFocusable(false);
                tv_Diachi.setFocusable(false);
                break;

            case R.id.linearLayout:
                Intent intent2 = new Intent(getActivity(), DanhSachHoaDonActivity.class);
                startActivity(intent2);
                break;
            case R.id.linearLayoutSetting:
                openSetting();
                break;
        }

    }

    private void saveInfo() {
        String id_user = sessionManagement.getUser().getIDUser();
        String ten = tv_tenUser.getText().toString();
        String diachi = tv_Diachi.getText().toString();
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.updateDataUser(id_user, ten, diachi);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketqua = response.body();
                if (ketqua.equals("Success")) {
                    Toast.makeText(getActivity(), "cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast t1 = Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
            }
        });
    }

}