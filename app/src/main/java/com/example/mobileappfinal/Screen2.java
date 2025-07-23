package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Screen2 extends AppCompatActivity {

    private AppCompatButton registerButtonS2, loginButtonS2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        initComponent();
        clicker();
    }

    private void clicker() {
        registerButtonS2.setOnClickListener(v->{
            Intent intent= new Intent(this, RegistrationForm.class);
            startActivity(intent);
        });
        loginButtonS2.setOnClickListener(v->{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }

    private void initComponent() {
        registerButtonS2= findViewById(R.id.registerButtonS2);
        loginButtonS2= findViewById(R.id.loginButtonS2);
    }
}