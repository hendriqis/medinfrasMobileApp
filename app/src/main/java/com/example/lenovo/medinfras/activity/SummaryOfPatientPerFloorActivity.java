package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.medinfras.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryOfPatientPerFloorActivity extends AppCompatActivity {

    @BindView(R.id.totalPasienLayoutButton)
    LinearLayout totalPasienLayoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_of_patient_per_floor);
        ButterKnife.bind(this);

        Intent getIntent = getIntent();
        int intValue = getIntent.getIntExtra("dynamicTitle", 3);

        if (intValue == 0) {
            Toast.makeText(this, "Press the existing button", Toast.LENGTH_SHORT).show();
        } else if (intValue == R.id.btnEW6C1) {
            setTitle("CLASS 1");
        } else if (intValue == R.id.btnEW6C2) {
            setTitle("CLASS 2");
        } else if (intValue == R.id.btnEW6C3) {
            setTitle("CLASS 3");
        } else if (intValue == R.id.btnEW6VIP) {
            setTitle("CLASS VIP");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        } return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.totalPasienLayoutButton)
    public void onViewClicked() {
        startActivity(new Intent(SummaryOfPatientPerFloorActivity.this, PatientRecyclerViewActivity.class));
    }

}
