package com.If5a.booksdictionary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.If5a.booksdictionary.R;
import com.If5a.booksdictionary.databinding.ActivityDetailBinding;
import com.If5a.booksdictionary.models.Book;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Book book = getIntent().getParcelableExtra("EXTRA_BOOKS");
        Toast.makeText(this, book.getTitle(), Toast.LENGTH_SHORT).show();

        binding.tvTitle.setText(book.getTitle());
        binding.tvIsbn.setText("ISBN        : "+ book.getISBN());
        binding.tvAuthor.setText("Author     : "+book.getAuthor());
        binding.tvYear.setText("Year         : "+book.getYear());
        binding.tvPublisher.setText("Publish   : "+book.getPublisher());

        Glide.with(DetailActivity.this)
                .load(book.getS())
                .placeholder(R.drawable.ic__menu_book_24)
                .into(binding.ivGambar);
    }
}