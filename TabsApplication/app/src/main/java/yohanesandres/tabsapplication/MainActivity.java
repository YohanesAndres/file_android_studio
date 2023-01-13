package yohanesandres.tabsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
    private TabsFragmentStatePageAdapter tabsFragmentStatePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        floatingActionButton = findViewById(R.id.fab);

        tabsFragmentStatePageAdapter = new TabsFragmentStatePageAdapter(getSupportFragmentManager());
        tabsFragmentStatePageAdapter.addFragment(new HomeFragment(),"Home");
        tabsFragmentStatePageAdapter.addFragment(new HomeFragment(),"News");
        tabsFragmentStatePageAdapter.addFragment(new HomeFragment(),"Profile");

        viewPager.setAdapter(tabsFragmentStatePageAdapter);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);


    }
}