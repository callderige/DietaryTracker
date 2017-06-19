package com.example.cliff.dietarytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class StatisticsLiftingFragment extends Fragment {
    View fragmentView;
    DatabaseLifting dbLifting;
    ArrayList<String> liftingStatistics;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_statistics_lifting, container, false);
        dbLifting = new DatabaseLifting(getActivity());
        liftingStatistics = dbLifting.liftingStatistics();
        textView = (TextView) fragmentView.findViewById(R.id.text_view_total_lifting_entries);
        textView.setText(liftingStatistics.get(0));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_lifting_highest_lift);
        textView.setText(liftingStatistics.get(1));
        textView = (TextView) fragmentView.findViewById(R.id.text_view_lifting_lowest_lift);
        textView.setText(liftingStatistics.get(2));

        return fragmentView;
    }
}
