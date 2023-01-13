package com.if5a.notifikasiku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.if5a.notifikasiku.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });


    }

    private void showNotification(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "notifikasiku_default")
                .setContentTitle("Reminder")
                .setContentText("Jangan Lupa tugas PAB2")
                .setAutoCancel(true)
                .setContentInfo("Notifikasiku")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(ContextCompat.getColor(this, android.R.color.transparent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(3)
                .setSmallIcon(R.drawable.ic_notification);

        NotificationManager notificationManager = (NotificationManager)  getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notifikasiku_default", "Notifikasiku", NotificationManager.IMPORTANCE_DEFAULT );
            channel.setDescription("");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {100, 200, 300, 400, 500});

            if(notificationManager != null)
            {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if(notificationManager != null)
        {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}