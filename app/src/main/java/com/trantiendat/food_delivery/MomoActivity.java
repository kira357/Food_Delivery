package com.trantiendat.food_delivery;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.trantiendat.direction.MoMoConstants;

public class MomoActivity extends AppCompatActivity {

    int environment = 1;//developer default - Production environment = 2

    RadioButton rdEnvironmentProduction;
    RadioGroup rdGroupEnvironment;
    Button btnPaymentMoMo;
    Button btnMappingMoMo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momo);
        //ButterKnife.bind(this);
        rdEnvironmentProduction = findViewById(R.id.rdEnvironmentProduction);
        rdGroupEnvironment = findViewById(R.id.rdGroupEnvironment);
        btnPaymentMoMo = findViewById(R.id.btnPaymentMoMo);
        btnMappingMoMo = findViewById(R.id.btnMappingMoMo);

        rdGroupEnvironment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
//                if (checkedId == R.id.rdEnvironmentDebug) {
//                    environment = 0;
//                }else if (checkedId == R.id.rdEnvironmentDeveloper) {
//                    environment = 1;
//                }else if (checkedId == R.id.rdEnvironmentProduction) {
//                    environment = 2;
//                }
            }
        });
        btnPaymentMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle data = new Bundle();
                intent = new Intent(MomoActivity.this, PaymentActivity.class);
                data.putInt(MoMoConstants.KEY_ENVIRONMENT, environment);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }
}