package com.example.mobileappfinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Update_Plant_Java extends AppCompatActivity {

    private EditText editName, editDescription;
    private Button btnUpdate, btnCancel;

    private ImageButton bckbtn;
    private String oldPlantName;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plant);

        editName = findViewById(R.id.editPlantName);
        editDescription = findViewById(R.id.editPlantDescription);
        btnUpdate = findViewById(R.id.btnConfirmUpdate);
        btnCancel = findViewById(R.id.btnCancelUpdate);
        bckbtn = findViewById(R.id.backbutton);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get data from intent
        oldPlantName = getIntent().getStringExtra("plantName");
        String description = getIntent().getStringExtra("plantDescription");

        editName.setText(oldPlantName);
        editDescription.setText(description);

        btnCancel.setOnClickListener(v -> finish());
        bckbtn.setOnClickListener(v -> finish());

        btnUpdate.setOnClickListener(v -> {
            String newName = editName.getText().toString().trim();
            String newDesc = editDescription.getText().toString().trim();

            if (newName.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            updatePlantInFirebase(oldPlantName, newName, newDesc);
        });
    }

    private void updatePlantInFirebase(String oldName, String newName, String newDesc) {
        String uid = auth.getUid();

        db.collection("users").document(uid).collection("plants")
                .whereEqualTo("name", oldName)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        query.getDocuments().get(0).getReference()
                                .update("name", newName, "description", newDesc)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Plant updated!", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(this, "Plant not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
