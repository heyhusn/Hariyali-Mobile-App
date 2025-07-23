package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView nav;
    private ImageButton chatbot, Imagechatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
        nav_button_remove_color();
        clicker();

        if (savedInstanceState == null) {
            loadFragment(new fragement_home());
        }

        nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new fragement_home();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new profile_fragement();
            } else if (itemId == R.id.nav_tutorial) {
                selectedFragment = new book_fragment();
            } else if (itemId == R.id.nav_garden) {
                selectedFragment = new plant_fragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host_fragment, selectedFragment)
                        .commit();
                return true;
            }

            return false;
        });
    }

    private void initComponents() {
        nav = findViewById(R.id.bottom_navigation);
        chatbot = findViewById(R.id.chatbot);
        Imagechatbot = findViewById(R.id.imagechatbot);
    }

    private void nav_button_remove_color() {
        nav.setItemIconTintList(null);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.host_fragment, fragment)
                .commit();
    }

    private void clicker() {
        chatbot.setOnClickListener(view -> {
            Intent intent = new Intent(this, chat_bot_MainActivity.class);
            startActivity(intent);
        });

    }


}
