package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.medinfras.MainActivity;
import com.example.lenovo.medinfras.R;
import com.goodiebag.pinview.Pinview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.pinView)
    Pinview pinView;
    @BindView(R.id.txtPinError)
    TextView txtPinError;
    @BindView(R.id.progressBarId)
    ContentLoadingProgressBar progressBarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Pinview pinview = (Pinview) findViewById(R.id.pinView);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                if (pinview.getValue().equals("0000")) {
                    Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    txtPinError.setVisibility(View.GONE);
                    progressBarId.setVisibility(View.VISIBLE);
                    startActivity(toMainActivity);
                    finish();
                } else {
                    txtPinError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
