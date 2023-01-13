package com.If5a.booksdictionary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.If5a.booksdictionary.R;
import com.If5a.booksdictionary.databases.BookHelper;
import com.If5a.booksdictionary.databinding.ActivitySplashBinding;
import com.If5a.booksdictionary.models.Book;
import com.If5a.booksdictionary.utilities.AppPreference;

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

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        BookHelper bookHelper;
        AppPreference appPreference;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            bookHelper = new BookHelper(SplashActivity.this);
            appPreference = new AppPreference(SplashActivity.this);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            binding.progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                ArrayList<Book> book = preLoadRawBooks();
                bookHelper.open();

                double progressMaxInsert = 80.0;
                double progressDiff = (progressMaxInsert - progress) / book.size();
                progress = 30;
                publishProgress((int) progress);

                bookHelper.beginTransaction();

                try {
                    for (Book books : book) {
                        bookHelper.insertDataBook(books);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    bookHelper.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bookHelper.endTransaction();

                bookHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        public ArrayList<Book> preLoadRawBooks() {
            ArrayList<Book> booksArrayList = new ArrayList<>();
            String line = null;

            BufferedReader bufferedReader;

            try {
                Resources resources = getResources();
                InputStream raw_dictionary = resources.openRawResource(R.raw.books);

                bufferedReader = new BufferedReader(new InputStreamReader(raw_dictionary));

                int count = 0;
                do {
                    line = bufferedReader.readLine();
                    String[] splitted = line.split(",");

                    Book books = new Book(splitted[0], splitted[1], splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], splitted[7]);

                    booksArrayList.add(books);
                    count++;
                } while (line != null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return booksArrayList;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}