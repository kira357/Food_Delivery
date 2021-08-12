package com.trantiendat.food_delivery;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.trantiendat.Model.ChiTietHoaDon;
import com.trantiendat.Service.APIService;
import com.trantiendat.Service.DataService;
import com.trantiendat.direction.MoMoConstants;
import com.trantiendat.direction.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class PaymentActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "2";
    TextView tvEnvironment;
    TextView tvMerchantCode;
    TextView tvMerchantName;
    TextView tvMessage;
    TextView tvTong, tvtenmon;
    Button btnPayMoMo;
    private String tong = "0";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Yummy yummy";
    private String merchantCode = "MOMOQYDW20210621";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "";
    private String id_user, id_hoandon;
    private String tenMonAn;
    ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList;
    SessionManagement sessionManagement;
    Toolbar toolbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        sessionManagement = new SessionManagement(this);

        tvEnvironment = findViewById(R.id.tvEnvironment);
        tvMerchantCode = findViewById(R.id.tvMerchantCode);
        tvMerchantName = findViewById(R.id.tvMerchantName);
        tvTong = findViewById(R.id.tvTong);
        btnPayMoMo = findViewById(R.id.btnPayMoMo);
        tvMessage = findViewById(R.id.tvMessage);
        tvtenmon = findViewById(R.id.tvtenmon);
        toolbar_back = findViewById(R.id.toolbar_back);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            environment = data.getInt(MoMoConstants.KEY_ENVIRONMENT);
            tong = data.getString(MoMoConstants.TONG_HOA_DON);
            id_user = data.getString(MoMoConstants.ID_USER);
            id_hoandon = data.getString(MoMoConstants.ID_HOA_DON);
            chiTietHoaDonArrayList = data.getParcelableArrayList(MoMoConstants.CTTHD);
        }
        if (environment == 0) {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEBUG);
            tvEnvironment.setText("Development Environment");
        } else if (environment == 1) {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
            tvEnvironment.setText("Development Environment");
        } else if (environment == 2) {
            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.PRODUCTION);
            tvEnvironment.setText("PRODUCTION Environment");
        }

        tvMerchantCode.setText("Merchant Code: " + merchantCode);
        tvMerchantName.setText("Merchant Name: " + merchantName);
        tvTong.setText(tong);
        tvtenmon.setText(getInfo());

        btnPayMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment();
                setEvent();
            }
        });
        description = tvtenmon.getText().toString().trim();

        setToolbar();
        createNotificationChannel();

    }


    private String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chiTietHoaDonArrayList.size(); i++) {
            ChiTietHoaDon chiTietHoaDon = chiTietHoaDonArrayList.get(i);
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\r\n");
            }
            stringBuilder.append(chiTietHoaDon.getTenMonAn() + " ");
            stringBuilder.append(chiTietHoaDon.getGia() + " ");
            stringBuilder.append(chiTietHoaDon.getSoLuong());
        }
        return stringBuilder.toString();
    }

    //example payment
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        if (tvTong.getText().toString() != null && tvTong.getText().toString().trim().length() != 0)
            tong = tvTong.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, tong);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, description);
        //client Optional
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);

        eventValue.put(MoMoParameterNamePayment.REQUEST_ID, merchantCode + "-" + UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "MOMOQYDW20210621");

        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
            //  objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));

                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        addNotification(tvMessage.getText().toString(), tong, chiTietHoaDonArrayList,id_hoandon,id_user);
                        Intent intent = new Intent(PaymentActivity.this, MainMenuActivity.class);
                        startActivity(intent);

                    } else {
                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
                    tvMessage.setText("message: " + message);
                } else if (data.getIntExtra("status", -1) == 2) {
                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                } else {
                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                }
            } else {
                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
            }
        } else {
            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
        }
    }

    private void addNotification(String message, String tien, ArrayList<ChiTietHoaDon> cthd,String id_hd,String id_user) {
        Intent notificationIntent = new Intent(this, NotificationDetailActivity.class);
        Bundle data = new Bundle();
        data.putString("message", message);
        data.putString("tien", tien);
        data.putParcelableArrayList("cthd", cthd);
        data.putString("id_hoandon",id_hd);
        data.putString("id_user",id_user);

        notificationIntent.putExtras(data);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.momologo)
                .setContentTitle(message)
                .setContentText(tien)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void setToolbar() {

        setSupportActionBar(toolbar_back);
        getSupportActionBar().setTitle("Xác nhận lại đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }
    private void setEvent(){
        ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.saveDatahoadon(Integer.valueOf(tong), id_hoandon);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    progressDialog.dismiss();
                    String ketqua = response.body();
                    if (ketqua.equals("Success")) {
                        Toast.makeText(PaymentActivity.this, "đã thanh toán", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PaymentActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast t1 = Toast.makeText(PaymentActivity.this, "Kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                progressDialog.dismiss();
            }

        });
    }

}
