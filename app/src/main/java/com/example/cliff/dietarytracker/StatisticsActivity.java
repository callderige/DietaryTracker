package com.example.cliff.dietarytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {
    DatabaseFood dbFood;
    ArrayList<String> foodStatistics;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dbFood = new DatabaseFood(this);
        foodStatistics = dbFood.foodStatistics();
        textView = (TextView) findViewById(R.id.text_view_total_calories);
        textView.setText(foodStatistics.get(0));
        textView = (TextView) findViewById(R.id.text_view_total_fat);
        textView.setText(foodStatistics.get(1));
        textView = (TextView) findViewById(R.id.text_view_total_carbs);
        textView.setText(foodStatistics.get(2));
        textView = (TextView) findViewById(R.id.text_view_total_protein);
        textView.setText(foodStatistics.get(3));
        textView = (TextView) findViewById(R.id.text_view_total_beer);
        textView.setText(foodStatistics.get(4));
        textView = (TextView) findViewById(R.id.text_view_total_potato);
        textView.setText(foodStatistics.get(5));
    }
}
