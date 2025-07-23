package com.example.mobileappfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class Activity_Host_Java extends AppCompatActivity {

    private BottomNavigationView nav;
    private ImageButton chatbot, Imagechatbot;

    // For image Chat bot
    private ActivityResultLauncher<Uri> cameraLauncher;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        initComponents();
        nav_button_remove_color();
        cameralauncher();
        clicker();

        if (savedInstanceState == null) {
            loadFragment(new Fragment_Home_Java());
        }

        nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new Fragment_Home_Java();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new Fragment_Profile_Java();
            } else if (itemId == R.id.nav_tutorial) {
                selectedFragment = new Fragment_Book_Java();
            } else if (itemId == R.id.nav_garden) {
                selectedFragment = new Fragment_Plant_Java();
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
            Intent intent = new Intent(this, Activity_Chat_Bot_Main_Java.class);
            startActivity(intent);
        });


        Imagechatbot.setOnClickListener(view -> {
            openCamera();
        });

    }
    private void openCamera() {
        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myImage.jpg");
        imageUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", imageFile);
        cameraLauncher.launch(imageUri);
    }
    private void cameralauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        Intent intent = new Intent(this, Activity_Image_Chatbot_Main_Java.class);
                        intent.putExtra("imageUri", imageUri.toString());
                        String p = "Check the uploaded plant or flower image and reply in this exact format using simple English. The headings should be in bold, but do not use any asterisks or symbols. Rest of the text should be in regular font.\n\n" +
                                "Plant Name:\n" +
                                "[Plant's real name]\n\n" +
                                "Plant Description:\n" +
                                "[Write a detailed simple explanation of the plant in 2–3 lines]\n\n" +
                                "Plant Status:\n" +
                                "[Fresh / Old / Healthy / Dull]\n\n" +
                                "Benefits:\n" +
                                "1. [Benefit one]\n" +
                                "2. [Benefit two]\n" +
                                "3. [Benefit three]\n\n" +
                                "Suited Countries:\n" +
                                "1. [Country one]\n" +
                                "2. [Country two]\n" +
                                "3. [Country three]";
                            intent.putExtra("prompt",p);
                        startActivity(intent);
                    }
                }
        );
    }

}
