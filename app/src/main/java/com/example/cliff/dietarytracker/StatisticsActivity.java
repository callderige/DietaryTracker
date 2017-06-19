package com.example.cliff.dietarytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {
    DatabaseFood dbFood;
    ArrayList<String> foodStatistics;
    TextView textView;
    ViewPager statisticsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.statistics_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Food statistics"));
        tabLayout.addTab(tabLayout.newTab().setText("Cardio statistics"));
        tabLayout.addTab(tabLayout.newTab().setText("Lifting statistics"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        statisticsPagerAdapter = (ViewPager) findViewById(R.id.statistics_pager);
        StatisticsPagerAdapter adapter = new StatisticsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        statisticsPagerAdapter.setAdapter(adapter);
        statisticsPagerAdapter.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                statisticsPagerAdapter.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.newEntry:
                intent = new Intent(this, EntryActivity.class);
                this.startActivity(intent);
                break;

            case R.id.openStatistics:
                intent = new Intent(this, StatisticsActivity.class);
                this.startActivity(intent);
                break;

            case R.id.openSettings:
                intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;

            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                finish();
                this.startActivity(intent);
                return true;
        }

        return true;
    }
}
