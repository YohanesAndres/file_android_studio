package proyek.akhir.sportcardexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSport;
    private FloatingActionButton fabSport;
    private List<Sport> mSportData = new ArrayList<>();
    private SportViewAdapter sportViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSport = findViewById(R.id.rvSport);
        fabSport = findViewById(R.id.fabSport);

        fabSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDummyData();
            }
        });

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        //rvSport.setLayoutManager(new LinearLayoutManager(this));
        rvSport.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        sportViewAdapter = new SportViewAdapter(mSportData, this);
        rvSport.setAdapter(sportViewAdapter);

        insertDummyData();

        int swipeDirs;
        if(gridColumnCount > 1)
        {
            swipeDirs = 0;
        }
        else
        {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                Collections.swap(mSportData, from, to);
                sportViewAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mSportData.remove(viewHolder.getAdapterPosition());
                sportViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(rvSport);
    }

    private void insertDummyData()
    {
        String[] sportList = getResources().getStringArray(R.array.sports_titles);
        String[] sportInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportImageResources = getResources().obtainTypedArray(R.array.sports_image);

        mSportData.clear();

        for (int i = 0; i<sportList.length; i++)
        {
            mSportData.add(new Sport(sportList[i], sportInfo[i], sportImageResources.getResourceId(i, 0)));
        }

        sportImageResources.recycle();

        sportViewAdapter.notifyDataSetChanged();
    }
}