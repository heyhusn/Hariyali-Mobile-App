package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class fragement_home extends Fragment {

    ImageView learningmodule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        learningmodule = view.findViewById(R.id.learningmodule);

        clicker();

        return view;
    }

    private void clicker() {
        learningmodule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VideoSearchActivity.class);
            startActivity(intent);
        });
    }
}