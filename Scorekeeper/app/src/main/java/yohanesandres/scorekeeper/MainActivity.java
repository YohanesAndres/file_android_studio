package yohanesandres.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mScore1 = 0, mScore2 = 0;
    private TextView tvScore1,tvScore2;
    private ImageButton btnPlusTeam1,btnPlusTeam2, btnMinusTeam1, btnMinusTeam2;

    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore1 = findViewById(R.id.tv_score1);
        tvScore2 = findViewById(R.id.tv_score2);

        btnPlusTeam1 = findViewById(R.id.btn_increaseTeam1);
        btnPlusTeam2 = findViewById(R.id.btn_increaseTeam2);
        btnMinusTeam1 = findViewById(R.id.btn_decreaseTeam1);
        btnMinusTeam2 = findViewById(R.id.btn_decreaseTeam2);

        if (savedInstanceState != null){
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            tvScore1.setText(String.valueOf(mScore1));
            tvScore2.setText(String.valueOf(mScore2));
        }

        btnPlusTeam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScore1++;
                tvScore1.setText(String.valueOf(mScore1));

            }
        });

        btnMinusTeam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScore1--;
                tvScore1.setText(String.valueOf(mScore1));
            }
        });
        btnPlusTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScore2++;
                tvScore2.setText(String.valueOf(mScore2));
            }
        });

        btnMinusTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScore2--;
                tvScore2.setText(String.valueOf(mScore2));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }else {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            recreate();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
        super.onSaveInstanceState(outState);
    }
}