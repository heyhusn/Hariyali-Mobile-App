package com.example.mobileappfinal;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class plant_fragment extends Fragment {

    GridView plantGridView;
    ArrayList<PlantClass> plantList;
    PlantCustomAdapter adapter;
    FloatingActionButton fabAdd, fabDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plant_fragment, container, false);

        plantGridView = view.findViewById(R.id.plantGridView);
        fabAdd = view.findViewById(R.id.fab_add);
        fabDelete = view.findViewById(R.id.fab_delete);

        plantList = new ArrayList<>();

        Uri imageUri0 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.drawable.aloevera);
        Uri imageUri1 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.drawable.apple);
        Uri imageUri2 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.drawable.lemon);
        Uri imageUri3 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.drawable.sunflower);
        plantList.add(new PlantClass(imageUri0, "aloe vera", "Medicinal Plant", 20.0, 15.0, 25.0, true, "Medicinal"));
        plantList.add(new PlantClass(imageUri1, "apple", "Fruit Plant", 30.0, 100.0, 22.0, true, "Fruit"));
        plantList.add(new PlantClass(imageUri2, "lemon", "Fruit Plant", 25.0, 50.0, 28.0, true, "Fruit"));
        plantList.add(new PlantClass(imageUri3, "sunflower", "Flower Plant", 15.0, 80.0, 30.0, true, "Flower"));
        adapter = new PlantCustomAdapter(getContext(), plantList);
        plantGridView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {

        });

        fabDelete.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "Delete button clicked!", Toast.LENGTH_SHORT).show();
            
            if (getContext() == null) {
                Toast.makeText(requireActivity(), "Context not available", Toast.LENGTH_SHORT).show();
                return;
            }
            
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            View dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.delete_plant_dialog_box, null);
            builder.setView(dialogView);
            builder.setCancelable(false);
            
            EditText plantTobeSearch = dialogView.findViewById(R.id.plantTobeSearch);
            Button btnDialogDelete = dialogView.findViewById(R.id.btnDialogDelete);
            
            AlertDialog dialog = builder.create();
            dialog.show();
            
            btnDialogDelete.setOnClickListener(nv -> {
                String p_name = plantTobeSearch.getText().toString().trim().toLowerCase();
                boolean found = false;

                for (int i = 0; i < plantList.size(); i++) {
                    String plantName = plantList.get(i).getPlantName().trim().toLowerCase();
                    if (plantName.equals(p_name)) {
                        plantList.remove(i);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(requireActivity(), p_name + " deleted successfully", Toast.LENGTH_SHORT).show();
                        found = true;
                        dialog.dismiss();
                        break;
                    }
                }
                if (!found) {
                    Toast.makeText(requireActivity(), "Plant not found", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}