package com.example.mobileappfinal;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

public class Activity_Add_Plant_Java extends AppCompatActivity {

    private EditText etPlantName, etDescription;
    private ImageView imageView;
    private Button btnChooseImage, btnSavePlant , btnyourplants;

    private ImageButton bckbtn;
    private Uri imageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        // Initialize views
        etPlantName = findViewById(R.id.etPlantName);
        etDescription = findViewById(R.id.etDescription);
        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSavePlant = findViewById(R.id.btnSavePlant);
        btnyourplants = findViewById(R.id.btnShowAll);
        bckbtn=findViewById(R.id.backbutton);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnSavePlant.setOnClickListener(v -> savePlant());
        btnyourplants.setOnClickListener(v -> yourplants() );
        bckbtn.setOnClickListener(v->finish());
    }

    private void yourplants()
    {
        Intent intent = new Intent(this, Fragment_Plant_Java.class);
        startActivity(intent);
    }
    private void savePlant() {
        String name = etPlantName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String uid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (name.isEmpty() || description.isEmpty() || imageUri == null || uid == null) {
            Toast.makeText(this, "All fields and image are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String base64Image = convertImageToBase64(imageUri);
        if (base64Image == null) {
            Toast.makeText(this, "Failed to encode image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare plant data
        HashMap<String, Object> plant = new HashMap<>();
        plant.put("name", name);
        plant.put("description", description);
        plant.put("imageBase64", base64Image);

        // Save to Firestore: users/{uid}/plants/{randomPlantId}
        db.collection("users")
                .document(uid)
                .collection("plants")
                .document(UUID.randomUUID().toString())
                .set(plant)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Plant added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Convert image to Base64
    private String convertImageToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}