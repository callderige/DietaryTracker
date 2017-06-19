package com.example.cliff.dietarytracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public StatisticsPagerAdapter(FragmentManager fm, int n) {
        super(fm);
        this.numOfTabs = n;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StatisticsFoodFragment statisticsFoodTab = new StatisticsFoodFragment();
                return statisticsFoodTab;
            case 1:
                StatisticsCardioFragment statisticsCardioTab = new StatisticsCardioFragment();
                return statisticsCardioTab;
            case 2:
                EntryLiftingFragment entryLiftingTab = new EntryLiftingFragment();
                return entryLiftingTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
