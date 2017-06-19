package com.example.cliff.dietarytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsCardioFragment extends Fragment {
    View fragmentView;
    DatabaseCardio dbCardio;
    ArrayList<String> cardioStatistics;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_statistics_cardio, container, false);
//        dbCardio = new DatabaseCardio(getActivity());
//        cardioStatistics = dbCardio.cardioStatistics();
//        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_calories);
//        textView.setText(cardioStatistics.get(0));

        return fragmentView;
    }
}
