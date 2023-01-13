package masterous.example.emdeepyfoodee.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import masterous.example.emdeepyfoodee.R;
import masterous.example.emdeepyfoodee.adapters.IngredientViewAdapter;
import masterous.example.emdeepyfoodee.models.Ingredient;
import masterous.example.emdeepyfoodee.models.ResponseData;
import masterous.example.emdeepyfoodee.models.ResponseMeal;
import masterous.example.emdeepyfoodee.services.APIServices;
import masterous.example.emdeepyfoodee.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    private ResponseMeal food;
    private String idMeal;
    private ImageView ivThumbnail;
    private TextView tvFoodName, tvCategory, tvArea, tvReleaseDate, tvOverview;
    private ChipGroup cgTags;
    private YouTubePlayerView youTubePlayerView;
    private FloatingActionButton fabShare;
    private RecyclerView rvIngredient;
    private IngredientViewAdapter ingredientViewAdapter;
    private List<Ingredient> ingredientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idMeal = getIntent().getExtras().getString(Constant.EXTRA_MEALS_ID);

        ivThumbnail = findViewById(R.id.iv_photo_detail);
        tvFoodName = findViewById(R.id.tv_title_detail);
        tvCategory = findViewById(R.id.tv_category_detail);
        tvArea = findViewById(R.id.tv_area_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_detail);
        tvOverview = findViewById(R.id.tv_description);
        cgTags = findViewById(R.id.cg_tags);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        fabShare = findViewById(R.id.fab_share);
        rvIngredient = findViewById(R.id.rv_ingredient);

        rvIngredient.setLayoutManager(new GridLayoutManager(this, 3));
        ingredientViewAdapter = new IngredientViewAdapter(this::onIngredientItemClick);
        rvIngredient.setAdapter(ingredientViewAdapter);

        getLifecycle().addObserver(youTubePlayerView);

        getMealsDetail(idMeal);
    }

    private void onIngredientItemClick(Ingredient ingredient, int i) {
        Intent intent = new Intent(this, ZoomActivity.class);
        intent.putExtra(Constant.EXTRA_ZOOM_FOTO_URL, Constant.THEMEALDB_INGREDIENT_PATH_URL + ingredient.getThumbnail());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getMealsDetail(String idMeal) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.THEMEALDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServices api = retrofit.create(APIServices.class);
        api.getMealDetail(idMeal).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.code() == 200) {
                    food = response.body().getMeals().get(0);

                    Glide.with(getApplicationContext())
                            .load(food.getStrMealThumb())
                            .placeholder(R.drawable.ic_broken_image_24)
                            .into(ivThumbnail);
                    tvFoodName.setText(food.getStrMeal());
                    tvCategory.setText(String.valueOf(food.getStrCategory()));
                    tvArea.setText(String.valueOf(food.getStrArea()));
                    tvReleaseDate.setText(food.getIdMeal());
                    tvOverview.setText(food.getStrInstructions());

                    ivThumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DetailActivity.this, ZoomActivity.class);
                            intent.putExtra(Constant.EXTRA_ZOOM_FOTO_URL, food.getStrMealThumb());
                            startActivity(intent);
                        }
                    });

                    String tags = food.getStrTags();
                    if (tags != null) {
                        String[] tag = tags.split(",");
                        for (int i = 0; i < tag.length; i++) {
                            Chip chip = new Chip(DetailActivity.this);
                            chip.setText(tag[i]);
                            chip.setChipBackgroundColorResource(R.color.purple_500);
                            chip.setTextColor(getResources().getColor(R.color.white));
                            cgTags.addView(chip);
                        }
                    }

                    for (int i = 1; i <= 20; i++) {
                        try {
                            Method getStrIngredient = ResponseMeal.class.getMethod("getStrIngredient" + i);
                            Method getStrMeasure = ResponseMeal.class.getMethod("getStrMeasure" + i);
                            String strIngredient = (String) getStrIngredient.invoke(food);
                            String strMeasure = (String) getStrMeasure.invoke(food);

                            if (strIngredient == null || strIngredient.equals("")) {
                                break;
                            }

                            Ingredient ingredient = new Ingredient();
                            ingredient.setIngredient(strIngredient);
                            ingredient.setMeasure(strMeasure);
                            ingredient.setThumbnail(strIngredient + ".png");
                            ingredientList.add(ingredient);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

//                    if (food.getStrIngredient1() != null || !food.getStrIngredient1().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient1());
//                        ingredient.setMeasure(food.getStrMeasure1());
//                        ingredient.setThumbnail(food.getStrIngredient1() + ".png");
//                        ingredientList.add(ingredient);
//                    }
//                    if (food.getStrIngredient2() != null || !food.getStrIngredient2().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient2());
//                        ingredient.setMeasure(food.getStrMeasure2());
//                        ingredient.setThumbnail(food.getStrIngredient2() + ".png");
//                        ingredientList.add(ingredient);
//                    }
//                    if (food.getStrIngredient3() != null || !food.getStrIngredient3().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient3());
//                        ingredient.setMeasure(food.getStrMeasure3());
//                        ingredient.setThumbnail(food.getStrIngredient3() + ".png");
//                        ingredientList.add(ingredient);
//                    }
//                    if (food.getStrIngredient4() != null || !food.getStrIngredient4().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient4());
//                        ingredient.setMeasure(food.getStrMeasure4());
//                        ingredient.setThumbnail(food.getStrIngredient4() + ".png");
//                        ingredientList.add(ingredient);
//                    }
//                    if (food.getStrIngredient5() != null || !food.getStrIngredient5().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient5());
//                        ingredient.setMeasure(food.getStrMeasure5());
//                        ingredient.setThumbnail(food.getStrIngredient5() + ".png");
//                        ingredientList.add(ingredient);
//                    }
//                    if (food.getStrIngredient6() != null || !food.getStrIngredient6().equals("")) {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setIngredient(food.getStrIngredient6());
//                        ingredient.setMeasure(food.getStrMeasure6());
//                        ingredient.setThumbnail(food.getStrIngredient6() + ".png");
//                        ingredientList.add(ingredient);
//                    }

                    ingredientViewAdapter.setData(ingredientList);

                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
                            String videoUrl = food.getStrYoutube();
                            String[] strVideo = videoUrl.split("v=");
                            String videoId = strVideo[1];
                            youTubePlayer.cueVideo(videoId, 0);
                        }
                    });

                    fabShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, Constant.THEMEALDB_SITE_URL + food.getIdMeal());
                            sendIntent.setType("text/plain");

                            Intent shareIntent = Intent.createChooser(sendIntent, "Share this text with:");
                            startActivity(shareIntent);
                        }
                    });

                    Toast.makeText(DetailActivity.this, "Lookup success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                System.out.println("Retrofit Error: " + t.getMessage());
                Toast.makeText(DetailActivity.this, "Retrofit Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}