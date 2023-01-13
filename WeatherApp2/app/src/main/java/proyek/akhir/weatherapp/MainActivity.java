package proyek.akhir.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>> {

    private RecyclerView rvWeather;
    private WeatherViewAdapter weatherViewAdapter;
    //private EditText etCityId;
    private Chip chipMedan, chipPalembang, chipBandarLampung, chipBandung, chipSemarang, chipSurabaya, chipJakarta;
    private Button btnSearch;
    private ProgressBar progressBar;

    private List<String> mCityId = new ArrayList<>();

    public static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        weatherViewAdapter = new WeatherViewAdapter();
        weatherViewAdapter.notifyDataSetChanged();

        rvWeather = findViewById(R.id.rv_weather);
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setItemAnimator(new DefaultItemAnimator());
        rvWeather.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rvWeather.setAdapter(weatherViewAdapter);

       // etCityId = findViewById(R.id.et_city);
        chipMedan = findViewById(R.id.chip_medan);
        chipBandarLampung = findViewById(R.id.chip_lampung);
        chipBandung = findViewById(R.id.chip_bandung);
        chipJakarta = findViewById(R.id.chip_jakarta);
        chipPalembang = findViewById(R.id.chip_palembang);
        chipSemarang = findViewById(R.id.chip_semarang);
        chipSurabaya = findViewById(R.id.chip_surabaya);

        btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(myListener);

        String city = "";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, city);

        LoaderManager.getInstance(this).initLoader(0, bundle, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        String cities ="";
        if(args != null)
        {
            cities =args.getString(EXTRAS_CITY);
        }
        return new WeatherAsyncTaskLoader(this, cities);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<WeatherItems>> loader, ArrayList<WeatherItems> data) {
        weatherViewAdapter.setData(data);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<WeatherItems>> loader) {
        weatherViewAdapter.setData(null);
        progressBar.setVisibility(View.GONE);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//        String city = etCityId.getText().toString();

        mCityId.clear();

        if(chipMedan.isChecked())
        {
            mCityId.add("1214520");
        }
        if(chipBandarLampung.isChecked())
        {
            mCityId.add("1624917");
        }
        if(chipBandung.isChecked())
        {
            mCityId.add("1650357");
        }
        if(chipJakarta.isChecked())
        {
            mCityId.add("1642911");
        }
        if(chipPalembang.isChecked())
        {
            mCityId.add("1633070");
        }
        if(chipSemarang.isChecked())
        {
            mCityId.add("1627896");
        }
        if(chipSurabaya.isChecked())
        {
            mCityId.add("1625822");
        }
        String city = mCityId.toString().replaceAll("\\[|\\]|\\s", "");
        if(TextUtils.isEmpty(city)) return;;

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, city);
        LoaderManager.getInstance(MainActivity.this).restartLoader(0, bundle, MainActivity.this);
        }
    };
}