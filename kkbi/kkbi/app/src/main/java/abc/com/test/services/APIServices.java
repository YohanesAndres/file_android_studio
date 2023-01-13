package abc.com.test.services;

import abc.com.test.model.KkbiModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    @GET("search")
    Call<KkbiModel> getTampilArti (
            @Query("q") String q);
}
