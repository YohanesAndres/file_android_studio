package com.if5a.chattogether;

import com.if5a.chattogether.models.Sender;
import com.if5a.chattogether.models.ViewData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAkKKhKgg:APA91bHoc2G1T4rokthrTa9rLGhdHnk5tnNwXDdOsF5pqKZ-mhuEj1PRajsxojtHDovW2FDaDjpk_iM0A3vht5lDQqEh5rNhJNukOdX7uyWdI15fM0e6qWoHyqWAQqjcAHvtUQKdw5yP"
            }
    )
    @POST("fcm/send")
    Call<ViewData> sendNotification(@Body Sender body);
}
