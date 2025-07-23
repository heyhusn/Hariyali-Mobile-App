package com.example.mobileappfinal;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class NotePadActivity extends AppCompatActivity {

    EditText editNote;
    ImageButton bckbtn;
    String tempNote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);

        editNote = findViewById(R.id.editNote);
        bckbtn=findViewById(R.id.backbutton);

        // Restore temporary note if screen rotates
        if (savedInstanceState != null) {
            tempNote = savedInstanceState.getString("note");
            editNote.setText(tempNote);
        }
        bckbtn.setOnClickListener(v -> finish());

    }


    @Override
    protected void onPause() {
        super.onPause();

        tempNote = editNote.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();

        editNote.setText(tempNote);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("note", editNote.getText().toString());
    }
}