package com.example.mobileappfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Fragment_Plant_Java extends Fragment {

    private FloatingActionButton addButton;
    private GridView plantGridView;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private ArrayList<Class_Plant> plantList;
    private Class_Plant_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        initViews(view);
        setupFirebase();
        setupAdapter();
        loadUserPlants();

        addButton.setOnClickListener(v -> startActivity(new Intent(getContext(), Activity_Add_Plant_Java.class)));

        plantGridView.setOnItemLongClickListener((parent, view1, position, id) -> {
            showPlantOptionsDialog(position);
            return true;
        });

        return view;
    }

    private void initViews(View view) {
        addButton = view.findViewById(R.id.fab_add);
        plantGridView = view.findViewById(R.id.plantGridView);
    }

    private void setupFirebase() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void setupAdapter() {
        plantList = new ArrayList<>();
        adapter = new Class_Plant_Adapter(getContext(), plantList);
        plantGridView.setAdapter(adapter);
    }

    private void loadUserPlants() {
        String uid = auth.getUid();
        CollectionReference plantRef = db.collection("users").document(uid).collection("plants");

        plantRef.get().addOnSuccessListener(querySnapshot -> {
            plantList.clear();
            for (DocumentSnapshot doc : querySnapshot) {
                Class_Plant plant = doc.toObject(Class_Plant.class);
                if (plant != null) {
                    plantList.add(plant);
                }
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e ->
                showToast("Failed to load plants: " + e.getMessage())
        );
    }

    private void showPlantOptionsDialog(int position) {
        Class_Plant selectedPlant = plantList.get(position);

        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.activity_popup_plant_option, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.show();

        LinearLayout optionDelete = popupView.findViewById(R.id.option_delete);
        LinearLayout optionUpdate = popupView.findViewById(R.id.option_update);
        LinearLayout optionReminder = popupView.findViewById(R.id.option_reminder);

        optionDelete.setOnClickListener(v -> {
            dialog.dismiss();
            deletePlantByName(selectedPlant.getName());
        });

        optionUpdate.setOnClickListener(v -> {
            dialog.dismiss();

            Intent updateIntent = new Intent(getContext(), Activity_Update_Plant_Java.class);
            updateIntent.putExtra("plantName", selectedPlant.getName());
            updateIntent.putExtra("plantDescription", selectedPlant.getDescription());
            startActivity(updateIntent);
        });
        optionReminder.setOnClickListener(v -> {
            dialog.dismiss();
            Class_Plant plant = selectedPlant;
            Intent intent = new Intent(getContext(), Activity_SetReminderActivity.class);
            intent.putExtra("plantName", plant.getName());
            intent.putExtra("plantDescription", plant.getDescription());
            startActivity(intent);
        });


    }

    private void deletePlantByName(String plantName) {
        String uid = auth.getUid();
        CollectionReference plantRef = db.collection("users").document(uid).collection("plants");

        plantRef.get().addOnSuccessListener(querySnapshot -> {
            boolean found = false;
            for (DocumentSnapshot doc : querySnapshot) {
                Class_Plant plant = doc.toObject(Class_Plant.class);
                if (plant != null && plantName.equals(plant.getName())) {
                    doc.getReference().delete().addOnSuccessListener(unused -> {
                        showToast("Plant deleted");
                        loadUserPlants();
                    }).addOnFailureListener(e ->
                            showToast("Delete failed: " + e.getMessage()));
                    found = true;
                    break;
                }
            }
            if (!found) showToast("No matching plant found");
        }).addOnFailureListener(e ->
                showToast("Error checking plants: " + e.getMessage())
        );
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
