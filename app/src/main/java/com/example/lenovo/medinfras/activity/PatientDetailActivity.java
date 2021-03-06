package com.example.lenovo.medinfras.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.medinfras.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lenovo.medinfras.activity.PatientRecyclerViewActivity.EXTRA_GENDER;
import static com.example.lenovo.medinfras.activity.PatientRecyclerViewActivity.EXTRA_MRN;
import static com.example.lenovo.medinfras.activity.PatientRecyclerViewActivity.EXTRA_NAME;

public class PatientDetailActivity extends AppCompatActivity {

    @BindView(R.id.tabView)
    TabLayout tabView;
    @BindView(R.id.viewPagerId)
    ViewPager viewPagerId;
    @BindView(R.id.imgFotoPasien)
    ImageView imgFotoPasien;
    @BindView(R.id.txtNamaPasien)
    TextView txtNamaPasien;
    @BindView(R.id.txtMRN)
    TextView txtMRN;
    @BindView(R.id.txtGender)
    TextView txtGender;

    PatientDetailActivity activity;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        ButterKnife.bind(this);
        /*this.setTitle(PatientRecyclerViewActivity.EXTRA_NAME);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);

        TextView actionBarTitle = (TextView) findViewById(R.id.custom_action_bar_title);
        actionBarTitle.setText(getIntent().getStringExtra(EXTRA_NAME));

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        //region Intent getIntent = getIntent();
        Intent getIntent = getIntent();
        txtNamaPasien.setText(getIntent.getStringExtra(EXTRA_NAME));
        txtMRN.setText(getIntent.getStringExtra(EXTRA_MRN));
        txtGender.setText(getIntent.getStringExtra(EXTRA_GENDER));

        /*imgFotoPasien.setImageResource(getIntent.getIntExtra("image", 1));*/

        /*activity.setTitle(String.valueOf(model.getNameCard()));*/
        //endregion

        //region TabLayout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabView);
        tabLayout.addTab(tabLayout.newTab().setText("Catatan Perawat").setIcon(R.drawable.ic_note));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2").setIcon(R.drawable.ic_note));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3").setIcon(R.drawable.ic_note));
        //endregion

        //region ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        PagerAdapter pagerAdapter = new com.example.lenovo.medinfras.adapter.PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //endregion
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        } return super.onOptionsItemSelected(item);
    }
}
