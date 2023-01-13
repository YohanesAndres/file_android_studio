package proyek.akhir.weatherapp;


import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class WeatherAsyncTaskLoader extends AsyncTaskLoader<ArrayList<WeatherItems>> {
    private ArrayList<WeatherItems> mData;
    private boolean mHasResult = false;
    private String mCities;

    public WeatherAsyncTaskLoader(@NonNull Context context, String mCities) {
        super(context);

        onContentChanged();
        this.mCities = mCities;
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged())
        {
            forceLoad();
        }
        else if(mHasResult)
        {
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(@Nullable ArrayList<WeatherItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResult)
        {
            mData = null;
            mHasResult = false;
        }
    }

    @Nullable
    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        ArrayList<WeatherItems> weatherItemses = new ArrayList<>();

        String url = "https://api.openweathermap.org/data/2.5/group?id=" +mCities+ "&appid=" + BuildConfig.ApiKey;

        if(Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems(weather);
                        weatherItemses.add(weatherItems);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return weatherItemses;
    }
}
