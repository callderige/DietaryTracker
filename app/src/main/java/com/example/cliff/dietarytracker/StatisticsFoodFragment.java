package com.example.cliff.dietarytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsFoodFragment extends Fragment {
    View fragmentView;
    DatabaseFood dbFood;
    ArrayList<String> foodStatistics;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_statistics_food, container, false);
        dbFood = new DatabaseFood(getActivity());
        foodStatistics = dbFood.foodStatistics();
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_calories);
        textView.setText(foodStatistics.get(0));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_fat);
        textView.setText(foodStatistics.get(1));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_carbs);
        textView.setText(foodStatistics.get(2));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_protein);
        textView.setText(foodStatistics.get(3));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_beer);
        textView.setText(foodStatistics.get(4));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_potato);
        textView.setText(foodStatistics.get(5));
        return fragmentView;
    }
}
