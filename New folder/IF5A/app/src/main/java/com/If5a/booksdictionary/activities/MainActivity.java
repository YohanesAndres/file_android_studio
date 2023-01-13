package com.If5a.booksdictionary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.If5a.booksdictionary.R;
import com.If5a.booksdictionary.adapters.BookViewAdapter;
import com.If5a.booksdictionary.databases.BookHelper;
import com.If5a.booksdictionary.databinding.ActivityMainBinding;
import com.If5a.booksdictionary.models.Book;
import com.If5a.booksdictionary.utilities.ItemClicklistener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BookHelper bookHelper;
    private BookViewAdapter bookViewAdapter;
    private ItemClicklistener<Book> itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemClickListener = new ItemClicklistener<Book>() {
            @Override
            public void onItemClick(Book data, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("EXTRA_BOOKS", data);
                startActivity(intent);
            }
       };

        bookHelper = new BookHelper(this);
        bookViewAdapter = new BookViewAdapter(itemClickListener);
        binding.rvBuku.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBuku.setAdapter(bookViewAdapter);

        getAllData();

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSearch = binding.etSearch.getText().toString();

                if(TextUtils.isEmpty(strSearch)){
                    getAllData();
                } else {
                    bookHelper.open();
                    ArrayList<Book> book = bookHelper.getAllDataBookByTitle(strSearch);
                    bookHelper.close();
                    bookViewAdapter.setData(book);
                }
            }
        });
    }

    private void getAllData() {
        bookHelper.open();
        ArrayList<Book> booksDictionary = bookHelper.getAllDataBook();
        bookHelper.close();
        bookViewAdapter.setData(booksDictionary);
    }

}