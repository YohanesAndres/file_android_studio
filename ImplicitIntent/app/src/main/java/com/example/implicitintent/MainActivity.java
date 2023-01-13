package com.example.implicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenWeb, btnOpenLocation, btnOpenNavigation, btnShareText, btnShareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenWeb = findViewById(R.id.btn_open_web);
        btnOpenLocation = findViewById(R.id.btn_open_location);
        btnOpenNavigation = findViewById(R.id.btn_open_navigation);
        btnShareText = findViewById(R.id.btn_share_text);
        btnShareImage = findViewById(R.id.btn_share_image);

        btnOpenWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://developer.android.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("Implicit Intents", "Can't handle this intent! ");
                }

            }
        });

        btnOpenLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri addressUri = Uri.parse("geo:0,0?q=restaurants");
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                intent.setPackage("com.google.android.apps.maps");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("Implicit Intents", "Can't handle this intent! ");
                }

            }
        });

        btnOpenNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri intentUri = Uri.parse("google.navigation:q=Palembang Square, +Palembang+Indonesia");
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                intent.setPackage("com.google.android.apps.maps");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("Implicit Intents", "Can't handle this intent! ");
                }

            }
        });

        btnShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share this text to...");
                startActivity(shareIntent);

            }
        });

        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Uri> imagesUris = new ArrayList<>();
                Uri imageUri1 = Uri.parse("");
                Uri imageUri2 = Uri.parse("");
                imagesUris.add(imageUri1);
                imagesUris.add(imageUri2);

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imagesUris);
                shareIntent.setType("image/*");

                Intent shareImageIntent = Intent.createChooser(shareIntent, "Share images to...");
                startActivity(shareImageIntent);

            }
        });
    }
}