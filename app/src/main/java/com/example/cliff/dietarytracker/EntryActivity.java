package com.example.cliff.dietarytracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class EntryActivity extends AppCompatActivity {
    ViewPager entryViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.entry_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("New food entry"));
        tabLayout.addTab(tabLayout.newTab().setText("New cardio entry"));
        tabLayout.addTab(tabLayout.newTab().setText("New lifting entry"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        entryViewPager = (ViewPager) findViewById(R.id.entry_pager);
        EntryPagerAdapter adapter = new EntryPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        entryViewPager.setAdapter(adapter);
        entryViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                entryViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
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
                intent = new Intent(this, EntryActivity.class);
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
