package com.if51.kamus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.if51.kamus.R;
import com.if51.kamus.databases.KamusHelper;
import com.if51.kamus.databinding.ActivitySplashBinding;
import com.if51.kamus.models.Kamus;
import com.if51.kamus.utilities.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer,Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashActivity.this);
            appPreference = new AppPreference(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<Kamus> kamusEnglishIndonesia = preLoadRawEnglishIndonesia();

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusEnglishIndonesia.size();

//                for (Kamus kamus : kamusEnglishIndonesia) {
//                    kamusHelper.insertDataEnglishIndonesia(kamus);
//                    progress += progressDiff;
//                    publishProgress((int) progress);
//                }
                kamusHelper.beginTransaction();
                try {
                    for (Kamus kamus : kamusEnglishIndonesia) {
                        kamusHelper.insertTransactionDataEnglishIndonesia(kamus);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    kamusHelper.setTransactionSuccess();
                }catch (Exception e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
                kamusHelper.endTransaction();

                kamusHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            binding.progressBar.setProgress(values[0]);
            binding.tvLoading.setText("Loading " + values[0] + "%...");
        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<Kamus> preLoadRawEnglishIndonesia(){
        ArrayList<Kamus> kamusArrayList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitsr = line.split("\t");

                Kamus kamus;
                kamus = new Kamus(splitsr[0],splitsr[1]);
                kamusArrayList.add(kamus);
                count++;
            }while (line != null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return kamusArrayList;
    }
}