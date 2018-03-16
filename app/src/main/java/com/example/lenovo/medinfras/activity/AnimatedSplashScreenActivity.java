package com.example.lenovo.medinfras.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.lenovo.medinfras.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimatedSplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.medinfrasLogoID)
    ImageView medinfrasLogoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        medinfrasLogoID = (ImageView) findViewById(R.id.medinfrasLogoID);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_move);
        medinfrasLogoID.startAnimation(animation);

        final Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
