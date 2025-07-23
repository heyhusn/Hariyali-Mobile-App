package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Splash_Java extends AppCompatActivity {

    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.whitelogo);
        appName = findViewById(R.id.appName);
        TextView tagline = findViewById(R.id.tagline);
        Animation bounceIn = AnimationUtils.loadAnimation(this, R.anim.bounce_in);
        Animation slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(bounceIn);
        appName.startAnimation(slideInLeft);
        tagline.startAnimation(fadeIn);

        new Thread(() -> {
            try {
                Thread.sleep(4000);
                Intent intent = new Intent(Activity_Splash_Java.this, Activity_Splash_2_Java.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}