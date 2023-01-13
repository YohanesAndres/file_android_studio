package com.if51.kamus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.if51.kamus.databinding.ActivityDetailBinding;
import com.if51.kamus.databinding.ActivityMainBinding;
import com.if51.kamus.models.Kamus;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Kamus kamus = getIntent().getParcelableExtra("EXTRA_KAMUS");
        binding.tvTitle.setText(kamus.getTitle());
        binding.tvDescription.setText(kamus.getDescription());




    }
}