package proyek.akhir.sportcardexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView tvSportTitleDetail;
    private ImageView ivSportDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSportTitleDetail = findViewById(R.id.tv_title_detail);
        ivSportDetail = findViewById(R.id.iv_sport_detail);

        tvSportTitleDetail.setText(getIntent().getStringExtra("TITLE"));
        Glide.with(this).load(getIntent().getIntExtra("IMAGE_RESOURCE", 0)).into(ivSportDetail);
    }
}