package com.example.mobileappfinal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "plant_reminder_channel";
    public static final int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        String plantName = intent.getStringExtra("plantName");
        String plantDescription = intent.getStringExtra("plantDescription");

        createNotificationChannel(context);

        // Intent for "Okay" button
        Intent okIntent = new Intent(context, NotificationActionReceiver.class);
        okIntent.setAction("OK_ACTION");
        PendingIntent okPendingIntent = PendingIntent.getBroadcast(
                context, 2001, okIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Intent for "Cancel" button
        Intent cancelIntent = new Intent(context, NotificationActionReceiver.class);
        cancelIntent.setAction("CANCEL_ACTION");
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(
                context, 2002, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.growth) // Add this icon in drawable
                .setContentTitle("Plant Reminder: " + plantName)
                .setContentText(plantDescription)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(plantDescription))
                .setLargeIcon(android.graphics.BitmapFactory.decodeResource(context.getResources(), R.drawable.plant)) // Add this icon
                .setSound(soundUri)
                .setAutoCancel(false)
                .setOngoing(true) // Makes it sticky
                .addAction(R.drawable.checkimage, "Okay", okPendingIntent)
                .addAction(R.drawable.cancelimage, "Cancel", cancelPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Plant Reminder Channel";
            String description = "Channel for plant reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
    }
}
