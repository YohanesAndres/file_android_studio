package com.example.userinteractionwithdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView tvPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String nama = getIntent().getExtras().getString("NAMA");
        String jk = getIntent().getExtras().getString("JENIS_KELAMIN");
        String alamat = getIntent().getExtras().getString("ALAMAT");
        String notel = getIntent().getExtras().getString("NOMOR_TELEPON");
        String email = getIntent().getExtras().getString("EMAIL");
        String jurusan = getIntent().getExtras().getString("JURUSAN");

        tvPesan = findViewById(R.id.tv_code);

        String kalimatPesan = "Selamat Datang, " + nama + ". Anda berjenis kelamin " + jk +
                " yang tinggal di " + alamat + " dengan nomor telepon " + notel + " dengan email : " + email + " dan jurusan : " + jurusan;


        tvPesan.setText(kalimatPesan);
    }
}