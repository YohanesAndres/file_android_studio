package yohanesandres.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvNews;
    private List<News> mData = new ArrayList<>();
    private NewsViewAdapter newsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNews = findViewById(R.id.rv_news);
        rvNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        newsViewAdapter = new NewsViewAdapter(mData, new NewsViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News item, int position) {
//                Toast.makeText(MainActivity.this, "item : "+ position + "diklik", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Ini adalah berita dengan judul : "+ item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        rvNews.setAdapter(newsViewAdapter);

        dummyNews();

    }
    public void dummyNews() {
        News news = new News();
        news.setId(0);
        news.setTitle("News 12 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 12 november 2020");
        mData.add(news);

        news = new News();
        news.setId(1);
        news.setTitle("News 13 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 13 november 2020");
        mData.add(news);

        news = new News();
        news.setId(2);
        news.setTitle("News 14 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 14 november 2020");
        mData.add(news);

        news = new News();
        news.setId(3);
        news.setTitle("News 15 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 15 november 2020");
        mData.add(news);

        news = new News();
        news.setId(4);
        news.setTitle("News 16 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 16 november 2020");
        mData.add(news);

        news = new News();
        news.setId(5);
        news.setTitle("News 17 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 17 november 2020");
        mData.add(news);

        news = new News();
        news.setId(6);
        news.setTitle("News 18 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 18 november 2020");
        mData.add(news);

        news = new News();
        news.setId(7);
        news.setTitle("News 19 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 19 november 2020");
        mData.add(news);

        news = new News();
        news.setId(8);
        news.setTitle("News 20 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 20 november 2020");
        mData.add(news);

        news = new News();
        news.setId(9);
        news.setTitle("News 21 November 2020");
        news.setDescription("Ini adalah deskripsi news pada tanggal 21 november 2020");
        mData.add(news);

    }
}