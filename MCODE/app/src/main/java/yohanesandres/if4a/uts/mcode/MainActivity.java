package yohanesandres.if4a.uts.mcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView tvTanggal, tvKode;
    private Button btnGenerate, btnRegister;
    private Button btnDaftar;
    private EditText etNama,etTanggalLahir, etHasilKode;

    private int mYear, mMonth, mDay, mHour, mMinute;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNama = findViewById(R.id.et_nama);


        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvTanggal = findViewById(R.id.tv_tanggal);
        btnGenerate = findViewById(R.id.btn_generate);
        btnRegister = findViewById(R.id.btn_register);

        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvTanggal.setText( String.format("%02d-%02d-%04d", day, month, year));
                        mDay = day;
                        mMonth = month;
                        mYear = year;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnRegister.setEnabled(true);
                String nama = etNama.getText().toString();
                String tanggal = tvTanggal.getText().toString();

                String[] split =  nama.split(" ");
                char a=split[0].charAt(0);
                char b=split[1].charAt(0);

                String pertama = String.valueOf(a);
                String kedua = String.valueOf(b);

                String kodeNama= pertama+kedua;
                tvKode.setText(String.format(kodeNama));

            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString();
                String tanggal = tvTanggal.getText().toString();

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Nama", nama);
                intent.putExtra("Tanggal", tanggal);
                startActivity(intent);




            }
        });




    }
}