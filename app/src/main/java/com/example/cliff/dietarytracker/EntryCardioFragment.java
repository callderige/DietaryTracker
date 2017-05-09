package com.example.cliff.dietarytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class EntryCardioFragment extends Fragment {
    Button submit;
    View fragmentView;
    DatabaseCardio dbCardio;
    String date;
    String name;
    String hours;
    String minutes;
    String distance;
    String laps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.activity_entry_cardio, container, false);

        submit = (Button) fragmentView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> cardioList = getSubmittedCardio(fragmentView);
                date = Calendar.getInstance().get(Calendar.MONTH)+1 +"/" +
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +"/" +
                        Calendar.getInstance().get(Calendar.YEAR)+"";
                name = cardioList.get(0);
                hours = cardioList.get(1);
                minutes = cardioList.get(2);
                distance = cardioList.get(3);
                laps = cardioList.get(4);

                dbCardio = new DatabaseCardio(getContext());
                dbCardio.insert(date, name, hours, minutes, distance, laps);
                dbCardio.close();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public ArrayList<String> getSubmittedCardio(View view) {
        ArrayList<String> arrayList = new ArrayList<String>();
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.activity_cardio_entry);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view2 = layout.getChildAt(i);
            if (view2 instanceof EditText) {
                if (((EditText) view2).getText().toString().trim().equals("")) {
                    arrayList.add("NA");
                } else {
                    arrayList.add(((EditText) view2).getText().toString());
                }
            }
        }

        return arrayList;
    }
}