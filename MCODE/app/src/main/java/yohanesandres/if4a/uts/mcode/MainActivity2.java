package yohanesandres.if4a.uts.mcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private Button btnShareText;
    private TextView tvPesan,tvnama,tvtanggal;
    private String tanggallahir,hasilkode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnShareText = findViewById(R.id.btn_share_text);

            String nama = getIntent().getExtras().getString("NAMA");

            tvPesan = findViewById(R.id.tv_pesan);
            String kalimatPesan = "Selamat datang, " + nama + ".Anda umur " +
                   tanggallahir + " nomor kode: " + hasilkode ;
            tvPesan.setText(kalimatPesan);


        tvnama = findViewById(R.id.tv_namaDetail);
        tvtanggal = findViewById(R.id.tv_TanggalDetail);

        String Nama = getIntent().getExtras().getString("Nama");
        String tanggal = getIntent().getExtras().getString("Tanggal");

        String kalimatNama = "Nama :  " + nama;
        String kalimatTanggal = "Tanggal :  " + tanggal;

        tvnama.setText(kalimatNama);
        tvtanggal.setText(kalimatTanggal);



    }
}