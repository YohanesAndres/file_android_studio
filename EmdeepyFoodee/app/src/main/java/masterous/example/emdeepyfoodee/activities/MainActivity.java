package masterous.example.emdeepyfoodee.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import masterous.example.emdeepyfoodee.R;
import masterous.example.emdeepyfoodee.adapters.FoodViewAdapter;
import masterous.example.emdeepyfoodee.models.Food;
import masterous.example.emdeepyfoodee.models.ResponseData;
import masterous.example.emdeepyfoodee.models.ResponseMeal;
import masterous.example.emdeepyfoodee.services.APIServices;
import masterous.example.emdeepyfoodee.utils.Constant;
import masterous.example.emdeepyfoodee.utils.ItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFood;
    private List<ResponseMeal> foodList = new ArrayList<>();
    private FoodViewAdapter foodViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFood = findViewById(R.id.rv_food);

//        foodList = getDummyData();
        foodViewAdapter = new FoodViewAdapter((ItemClickListener<ResponseMeal>) this::onFoodItemClick);
//        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setLayoutManager(new GridLayoutManager(this, 2));
        rvFood.setAdapter(foodViewAdapter);

//        foodViewAdapter.setData(foodList);

        getChickenFood();
    }

    private void onFoodItemClick(ResponseMeal food, int i) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constant.EXTRA_MEALS_ID, food.getIdMeal());
        startActivity(intent);
    }

    private List<Food> getDummyData() {
        List<Food> data = new ArrayList<>();
        Food food = new Food();
        food.setId(0);
        food.setNama("Teriyaki Chicken Casserole");
        food.setThumbnail(R.drawable.teriyaki_chicken_casserole);
        food.setDeskripsi("Ini adalah menu terbaik tahun ini");
        food.setVote(125);
        food.setRate(4.5f);
        food.setTanggalRilis("21-04-2022");
        data.add(food);

        food = new Food();
        food.setId(1);
        food.setNama("Noodle Premium A");
        food.setThumbnail(R.drawable.noodle);
        food.setDeskripsi("Ini adalah menu terbaik noodle A");
        food.setVote(250);
        food.setRate(4.1f);
        food.setTanggalRilis("25-04-2022");
        data.add(food);

        food = new Food();
        food.setId(2);
        food.setNama("Noodle Premium B");
        food.setThumbnail(R.drawable.noodle);
        food.setDeskripsi("Ini adalah menu terbaik noodle B");
        food.setVote(90);
        food.setRate(4.0f);
        food.setTanggalRilis("30-04-2022");
        data.add(food);

        return data;
    }

    private void getChickenFood() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.THEMEALDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServices api = retrofit.create(APIServices.class);
        api.getMeals("Seafood").enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.code() == 200) {
                    foodList = response.body().getMeals();
                    foodViewAdapter.setData(foodList);
                    Toast.makeText(MainActivity.this, "Response Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                System.out.println("Retrofit Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}