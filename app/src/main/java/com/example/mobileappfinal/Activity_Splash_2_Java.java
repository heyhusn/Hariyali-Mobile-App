package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Activity_Splash_2_Java extends AppCompatActivity {

    private AppCompatButton registerButtonS2, loginButtonS2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        initComponent();
        clicker();
    }

    private void clicker() {
        registerButtonS2.setOnClickListener(v->{
            Intent intent= new Intent(this, Activity_Signup_Java.class);
            startActivity(intent);
        });
        loginButtonS2.setOnClickListener(v->{
            Intent intent = new Intent(this, Activity_Signin_Java.class);
            startActivity(intent);
        });
    }

    private void initComponent() {
        registerButtonS2= findViewById(R.id.registerButtonS2);
        loginButtonS2= findViewById(R.id.loginButtonS2);
    }
}