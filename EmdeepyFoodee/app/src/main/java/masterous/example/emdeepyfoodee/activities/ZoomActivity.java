package masterous.example.emdeepyfoodee.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import masterous.example.emdeepyfoodee.R;
import masterous.example.emdeepyfoodee.utils.Constant;

public class ZoomActivity extends AppCompatActivity {
    private ZoomageView zvImage;
    private ImageView ivBack;
    private String fileUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        fileUrl = getIntent().getExtras().getString(Constant.EXTRA_ZOOM_FOTO_URL);

        zvImage = findViewById(R.id.myZoomageView);
        ivBack = findViewById(R.id.iv_back);

        Glide.with(getApplicationContext())
                .load(fileUrl)
                .into(zvImage);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}