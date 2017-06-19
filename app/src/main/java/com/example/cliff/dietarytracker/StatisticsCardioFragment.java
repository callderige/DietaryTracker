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
        dbCardio = new DatabaseCardio(getActivity());
        cardioStatistics = dbCardio.cardioStatistics();
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_entries);
        textView.setText(cardioStatistics.get(0));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_hours);
        textView.setText(cardioStatistics.get(1));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_minutes);
        textView.setText(cardioStatistics.get(2));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_distance);
        textView.setText(cardioStatistics.get(3));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_laps);
        textView.setText(cardioStatistics.get(4));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_cardio_hikes);
        textView.setText(cardioStatistics.get(5));

        return fragmentView;
    }
}
