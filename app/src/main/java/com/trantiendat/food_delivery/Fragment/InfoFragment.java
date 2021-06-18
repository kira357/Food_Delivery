package com.trantiendat.food_delivery.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.trantiendat.Adapter.DanhSachHoaDonAdapter;
import com.trantiendat.Model.HoaDon;
import com.trantiendat.Model.User;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.SessionManagement;
import com.trantiendat.food_delivery.ChiTietDiaDiemActivity;
import com.trantiendat.food_delivery.DanhSachHoaDonActivity;
import com.trantiendat.food_delivery.LoginActivity;
import com.trantiendat.food_delivery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoFragment extends Fragment implements View.OnClickListener {

    private static final int MY_REQUEST_CODE = 123;
    private static final int PICK_IMAGE_CODE = 0;
    private MaterialButton btn_LogOut;
    private MaterialButton btn_LogIn;
    private TextView tv_tenUser, tv_Diachi;
    private ImageView imgv_hinhUser, imgv_edit;
    private GoogleSignInAccount mGoogleSignInAccount;
    GoogleSignInOptions googleSignInOptions;
    TextView tv_soluongdon;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private LinearLayout linearLayout;
    private DanhSachHoaDonAdapter danhSachHoaDonAdapter;
    private ArrayList<HoaDon> hoaDonArrayList;
    View view;
    SessionManagement sessionManagement;

//    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        if (data == null) {
//                            return;
//                        }
//                        Uri uri = data.getData();
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                            imgv_edit.setImageBitmap(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });

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
        currentUser = mAuth.getCurrentUser();
        init();
        GetLoginFB();
        CheckLogin();
        setSoLuong();
        setEvent();
        return view;

    }


    private void init() {
        btn_LogOut = view.findViewById(R.id.btn_LogOut);
        btn_LogIn = view.findViewById(R.id.btn_LogIn);
        tv_tenUser = view.findViewById(R.id.tv_tenUser);
        tv_Diachi = view.findViewById(R.id.tv_Diachi);
        imgv_hinhUser = view.findViewById(R.id.imgv_hinhUser);
        imgv_edit = view.findViewById(R.id.imgv_edit);
        linearLayout = view.findViewById(R.id.linearLayout);
        tv_soluongdon = view.findViewById(R.id.tv_soluongdon);
    }

    private void setEvent() {
        btn_LogOut.setOnClickListener(this);
        btn_LogIn.setOnClickListener(this);
        imgv_edit.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

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
        getFragmentManager().beginTransaction().detach(InfoFragment.this).attach(InfoFragment.this).commit();

        Toast.makeText(getActivity(), "Reload pager Info", Toast.LENGTH_SHORT).show();
    }

    private void CheckLogin() {
        if (!sessionManagement.Check()) {
            tv_tenUser.setVisibility(View.VISIBLE);
            tv_Diachi.setVisibility(View.VISIBLE);
        } else {
            imgv_edit.setVisibility(View.VISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            getinfoUser();
        }
        if (AccessToken.getCurrentAccessToken() != null) {
            imgv_edit.setVisibility(View.INVISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            loadUserProfile(AccessToken.getCurrentAccessToken());
        } else {

        }
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (mGoogleSignInAccount != null) {
            imgv_edit.setVisibility(View.INVISIBLE);
            btn_LogIn.setVisibility(View.INVISIBLE);
            String email = mGoogleSignInAccount.getEmail();
            String ten = mGoogleSignInAccount.getDisplayName();
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
//    private void onClickRequestPermission() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            openGallery();
//            return;
//        }
//        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            openGallery();
//        } else {
//            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            requestPermissions(permission,MY_REQUEST_CODE);
//
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_REQUEST_CODE) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            }
//        }
//    }
//
//        private void openGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,"select picture"),MY_REQUEST_CODE);
//
//       // someActivityResultLauncher.launch(Intent.createChooser(intent, "select picture "));
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == MY_REQUEST_CODE ){
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                imgv_hinhUser.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

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
                Toast t1 = Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
            }
        });
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
                startActivity(intent);
                break;
            case R.id.imgv_edit:
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog_edit);
                dialog.setCanceledOnTouchOutside(false);
                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText edt_ten = dialog.findViewById(R.id.edt_tenEdit);
                final EditText edt_diachi = dialog.findViewById(R.id.edt_diachiEdit);
                final MaterialButton btn_cancelEdit = dialog.findViewById(R.id.btn_cancelEdit);
                final MaterialButton btn_Edit = dialog.findViewById(R.id.btn_Edit);
                final ImageView imgv_anhEdit = dialog.findViewById(R.id.imgv_anhEdit);
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
                        String id_user = sessionManagement.getUser().getIDUser();
                        String ten = edt_ten.getText().toString();
                        String diachi = edt_diachi.getText().toString();
                        tv_tenUser.setText(ten);
                        tv_Diachi.setText(diachi);

                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.updateDataUser(id_user, ten, diachi);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if (ketqua.equals("Success")) {
                                    Toast.makeText(getActivity(), "cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }else {

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

                        dialog.dismiss();
                    }
                });
                dialog.show();
                imgv_anhEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // onClickRequestPermission();
                    }
                });
                break;
            case R.id.linearLayout:
                Intent intent2 = new Intent(getActivity(), DanhSachHoaDonActivity.class);
                startActivity(intent2);
                break;

        }
    }

}