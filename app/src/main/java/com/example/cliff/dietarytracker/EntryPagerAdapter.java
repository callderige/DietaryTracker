package com.example.cliff.dietarytracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class EntryPagerAdapter extends FragmentStatePagerAdapter{
    int numOfTabs;

    public EntryPagerAdapter(FragmentManager fm, int n) {
        super(fm);
        this.numOfTabs = n;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EntryFoodFragment entryFoodTab = new EntryFoodFragment();
                return entryFoodTab;
            case 1:
                EntryCardioFragment entryCardioTab = new EntryCardioFragment();
                return entryCardioTab;
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
