package abc.com.test.api;

import com.google.android.material.chip.Chip;

import abc.com.test.services.APIServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String BASE_URLKamus = "https://kbbi-api-amm.herokuapp.com/";

    private static Retrofit retrofit;
    public static APIServices endpointTranslate()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URLKamus)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIServices.class);
    }

//    public static  final String engine = "google";
//    public static  final String text = "kamu siapa";
//    public static  final String to = "en";

//    public static  final String chipIndonesia = "id";
//    public static  final String chipEnglish = "en";

    

}
