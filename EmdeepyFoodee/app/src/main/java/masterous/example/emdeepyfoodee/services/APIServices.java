package masterous.example.emdeepyfoodee.services;

import masterous.example.emdeepyfoodee.models.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    @GET("filter.php")
    Call<ResponseData> getMeals(@Query("c") String category);

    @GET("lookup.php")
    Call<ResponseData> getMealDetail(@Query("i") String idMeal);
}
