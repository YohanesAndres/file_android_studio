package com.example.userinteractionwithdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etNama, etAlamat, etNomorTelepon, etEmail, etPassword;
    private RadioGroup rgJK;
    private Spinner spJurusan;
    private Button btnDaftar, btnDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etNomorTelepon = findViewById(R.id.et_notel);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        rgJK = findViewById(R.id.rg_jk);
        spJurusan = findViewById(R.id.spin_jurusan);

        btnDaftar = findViewById(R.id.btn_daftar);
        btnDialog = findViewById(R.id.btn_dialog);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity.this, "Ini Pesan Toast", Toast.LENGTH_SHORT).show();
                String nama = etNama.getText().toString();

                RadioButton selectedRadioButton = findViewById(rgJK.getCheckedRadioButtonId());
                String jenisKelamin = selectedRadioButton.getText().toString();

                String alamat = etAlamat.getText().toString();
                String nomorTelepon = etNomorTelepon.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                String jurusan = spJurusan.getSelectedItem().toString();

                boolean daftar = true;

                if(TextUtils.isEmpty(nama)){
                    etNama.setError("Nama Tidak boleh Kosong !");
                    daftar = false;
                }

                if(TextUtils.isEmpty(alamat)){
                    etAlamat.setError("Alamat Tidak boleh Kosong !");
                    daftar = false;
                }

                if(TextUtils.isEmpty(nomorTelepon)){
                    etNomorTelepon.setError("Nomor Telepon Tidak boleh Kosong !");
                    daftar = false;
                }

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email Tidak boleh Kosong !");
                    daftar = false;
                }

                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password Tidak boleh Kosong !");
                    daftar = false;
                }

                if (daftar){
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("NAMA", nama);
                    intent.putExtra("JENIS_KELAMIN", jenisKelamin);
                    intent.putExtra("ALAMAT", alamat);
                    intent.putExtra("NOMOR_TELEPON", nomorTelepon);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("JURUSAN", jurusan);
                    startActivity(intent);
                }
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                setTitle("Contoh Dialog");
                builder.setMessage("ini adalah contoh dialog");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Anda Menekan Tombol Ya", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}