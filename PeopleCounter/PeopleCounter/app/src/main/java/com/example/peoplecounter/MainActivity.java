package com.example.peoplecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvNumber;
    private Button btnUp, btnDown, btnReset;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = findViewById(R.id.tvNumber);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnReset = findViewById(R.id.btnReset);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                tvNumber.setText(String.valueOf(number));
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                tvNumber.setText(String.valueOf(number));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = 0;
                tvNumber.setText(String.valueOf(number));
            }
        });

    }
}