package com.example.mobileappfinal;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

public class Fragment_Home_Java extends Fragment {

    ImageView learningmodule, healingmodule,caremodule;

    private ActivityResultLauncher<Uri> cameraLauncher;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        learningmodule = view.findViewById(R.id.learningmodule);
        healingmodule = view.findViewById(R.id.healingmodule);
        caremodule = view.findViewById(R.id.carecardmodule);

        clicker();
        cameralauncher();

        return view;
    }

    private void clicker() {
        learningmodule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), activity_youtube_card_1_Home_Java.class);
            startActivity(intent);
        });

        healingmodule.setOnClickListener(v -> {
            openCamera();
        });

        caremodule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotePadActivity.class);
            startActivity(intent);
        });

        }

    private void openCamera() {
        File imageFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myImage.jpg");
        imageUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", imageFile);
        cameraLauncher.launch(imageUri);
    }
    private void cameralauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        Intent intent = new Intent(getActivity(), Activity_Image_Chatbot_Main_Java.class);
                        intent.putExtra("imageUri", imageUri.toString());
                        String p = "Check the uploaded plant or flower image and reply in this exact format using simple English. The headings should be in bold, but do not use any asterisks or symbols. Rest of the text should be in regular font.\n\n" +
                                "Plant Name:\n" +
                                "[Plant's real name]\n\n" +
                                "Plant Description:\n" +
                                "[Write a detailed simple explanation of the plant in 2–3 lines]\n\n" +
                                "Plant Status:\n" +
                                "[Fresh / Old / Healthy / Dull]\n\n" +
                                "Disease:\n" +
                                "[Write the disease name or say: This plant is healthy.]\n\n" +
                                "Cause:\n" +
                                "[What causes the disease, if any]\n\n" +
                                "Fix:\n" +
                                "[Simple steps to fix or prevent the disease]\n\n";
                        intent.putExtra("prompt",p);
                        startActivity(intent);
                    }
                }
        );





    }
}