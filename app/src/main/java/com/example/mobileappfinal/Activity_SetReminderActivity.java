package com.example.mobileappfinal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Activity_SetReminderActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button setReminderButton;
    private ImageButton bckbutton;
    private TextView plantNameTextView, plantDescTextView;

    private String plantName, plantDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        timePicker = findViewById(R.id.timePicker);
        setReminderButton = findViewById(R.id.setReminderButton);
        bckbutton = findViewById(R.id.backbutton);
        plantNameTextView = findViewById(R.id.plantNameTextView);
        plantDescTextView = findViewById(R.id.plantDescTextView);

        plantName = getIntent().getStringExtra("plantName");
        plantDescription = getIntent().getStringExtra("plantDescription");

        plantNameTextView.setText(plantName);
        plantDescTextView.setText(plantDescription);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        setReminderButton.setOnClickListener(v -> setReminder());
        bckbutton.setOnClickListener(v -> finish());
    }

    private void setReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Permission denied for exact alarms", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("plantName", plantName);
        intent.putExtra("plantDescription", plantDescription);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 1001, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Reminder set for " + plantName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to set reminder: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
