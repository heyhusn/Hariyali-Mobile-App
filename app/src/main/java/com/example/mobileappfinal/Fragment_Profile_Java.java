package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Fragment_Profile_Java extends Fragment {

    private TextView profileUser, profilePhone, profileEmail;
    private LinearLayout logoutLayout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        profileUser = view.findViewById(R.id.profileUser);
        profilePhone = view.findViewById(R.id.profilePhone);
        profileEmail = view.findViewById(R.id.profileEmail);
        logoutLayout = view.findViewById(R.id.logoutLayout);

        fetchUserInfo();

        logoutLayout.setOnClickListener(v -> {
            try {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(requireActivity(), Activity_Splash_2_Java.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error during logout: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LogoutError", "Exception: ", e);
            }
        });


        return view;
    }

    private void fetchUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String email = documentSnapshot.getString("email");
                        String name = documentSnapshot.getString("name");
                        String phone = documentSnapshot.getString("phone");

                        profileEmail.setText(email != null ? email : "No Email");
                        profileUser.setText(name != null ? name : "No Name");
                        profilePhone.setText(phone != null ? phone : "No Phone");
                    } else {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to load profile: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
