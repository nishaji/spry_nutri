package com.Spry.nutritionix;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;


import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Spry.dev5magic.NoBoringActionBarActivity;
import com.myapplication.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sprydev5 on 27/11/15.
 */
public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */

        View x = inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);

        ViewPager viewPager = (ViewPager) x.findViewById(R.id.viewpager);


        //Add tabs icon with setIcon() or simple text with .setText()
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.my_meals));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.daily_goal));

        //Add fragments
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NutritionSearch());
        adapter.addFragment(new My_Daily_Plans());
        adapter.addFragment(new Daily_Goal());

        //Setting adapter
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return x;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Fragment1";
                case 1:
                    return "Fragment2";
                case 2:
                    return "Fragment3";
            }

            return null;
        }
    }
}