package abc.com.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import abc.com.test.R;
import abc.com.test.api.API;
import abc.com.test.model.KkbiModel;
import abc.com.test.model.ResultList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public EditText etInputKkbi;

    public String textInputKkbi;

    public Button btnArti;
    public TextView tvTampilKkbi;
    private final String TAG = "TranslateMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputKkbi = findViewById(R.id.et_inputKkbi);

        //String strValue = textTo.getText().toString();
        btnArti = findViewById(R.id.BtnArti);

//        engine = getString(API.engine);
//        text = getIntent().getExtras().getString(API.text);
//        to = getIntent().getExtras().getString(API.to);

        tvTampilKkbi = findViewById(R.id.tv_tampilKkbi);

        btnArti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInputKkbi = etInputKkbi.getText().toString();

                getTampilArti(textInputKkbi);

            }
        });
    }

    private void getTampilArti(String textInputKkbi) {
        API.endpointTranslate().getTampilArti(textInputKkbi).enqueue(new Callback<KkbiModel>() {
            @Override
            public void onResponse(Call<KkbiModel> call, Response<KkbiModel> response) {
                if (response.code() == 200) {
                    //TranslateModel.Data data = response.body().getData();
                    //DataKamus dataList = response.body().getData();
                    //DataKamus dataKamus = (DataKamus) response.body().getData();
                    ResultList dataList = (ResultList) response.body().getData().getResultLists();

//                    //for (TranslateModel.Data data1 : dataList){
                    Log.e(TAG, "onResponse: Translate : " + dataList.getArti());
                    tvTampilKkbi.setText(dataList.getArti());
                    //}

                    //List<KkbiModel.DataKamus>

                    Toast.makeText(MainActivity.this, "Response Success!", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(MainActivity.this, "Response Code : " + response.code(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<KkbiModel> call, Throwable t) {
                System.out.println("Retrofit Error: " + t.getMessage());
                Log.d(TAG, t.toString());
            }
        });
    }
}