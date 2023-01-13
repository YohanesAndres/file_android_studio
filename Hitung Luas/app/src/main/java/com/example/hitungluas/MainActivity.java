package com.example.hitungluas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etPanjang, etLebar;
    private Button btnHitungLuas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPanjang = findViewById(R.id.et_panjang);
        etLebar = findViewById(R.id.et_lebar);
        btnHitungLuas = findViewById(R.id.btn_hitung);

        btnHitungLuas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPanjang = etPanjang.getText().toString();
                String strLebar = etLebar.getText().toString();

                if (TextUtils.isEmpty(strPanjang) || TextUtils.isEmpty(strPanjang)){
                    Toast.makeText(MainActivity.this, "Panjang dan Lebar Harus Diisi", Toast.LENGTH_SHORT).show();
                }else{
                    float panjang = Float.parseFloat(strPanjang);
                    float lebar = Float.parseFloat(strLebar);

                    float luas = panjang * lebar;

                    Toast.makeText(MainActivity.this,"Luas Persegi panjang : "+ String.format("%.2f", luas), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}