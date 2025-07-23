package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Activity_Signup_Java extends AppCompatActivity {

    private AppCompatButton registerBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private EditText registerName, registerEmail, registerPhone, registerPassword, registerAgainPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initComponent();
        setupClickListener();
    }

    private void initComponent() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        registerBtn = findViewById(R.id.registerBtn);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPhone = findViewById(R.id.registerPhone);
        registerPassword = findViewById(R.id.registerPassword);
        registerAgainPassword = findViewById(R.id.registerAgainPassword);
    }

    private void setupClickListener() {
        registerBtn.setOnClickListener(v -> {
            String name = registerName.getText().toString().trim();
            String email = registerEmail.getText().toString().trim();
            String phone = registerPhone.getText().toString().trim();
            String password = registerPassword.getText().toString().trim();
            String confirmPassword = registerAgainPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        DocumentReference reference = firestore.collection("users").document(user.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("email", email);
                        if (!phone.isEmpty()) {
                            map.put("phone", phone);
                        }

                        reference.set(map).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                user.sendEmailVerification().addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        showVerificationDialog(user);
                                    } else {
                                        Toast.makeText(this,
                                                "Failed to send verification email: " + (task2.getException() != null ? task2.getException().getMessage() : "Unknown error"),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                Toast.makeText(this,
                                        "Failed to save user data: " + (task1.getException() != null ? task1.getException().getMessage() : "Unknown error"),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "User creation failed. Please try again.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(this,
                            "Registration failed: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"),
                            Toast.LENGTH_LONG).show();

                }
            });
        });
    }

    private void showVerificationDialog(FirebaseUser user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.activity_dialog_email_verification, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnContinue = dialogView.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            user.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful() && user.isEmailVerified()) {
                    Toast.makeText(this, "Email verified successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(this, Activity_Signin_Java.class));
                    finish();
                } else {
                    Toast.makeText(this, "Email not verified yet. Please verify first from Mailbox and try again.", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

}

