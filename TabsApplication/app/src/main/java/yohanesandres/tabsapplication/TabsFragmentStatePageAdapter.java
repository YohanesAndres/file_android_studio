package yohanesandres.tabsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsFragmentStatePageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmenListTitle = new ArrayList<>();

    public TabsFragmentStatePageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmenListTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmenListTitle.get(position);
    }
}
