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

public class EntryFoodFragment extends Fragment {
    Button submit;
    View fragmentView;
    DatabaseFood dbFood;
    String date;
    String name;
    String calories;
    String fat;
    String carbs;
    String protein;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.activity_entry_food, container, false);

        submit = (Button) fragmentView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> foodList = getSubmittedFood(fragmentView);
                date = Calendar.getInstance().get(Calendar.MONTH)+1 +"/" +
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +"/" +
                        Calendar.getInstance().get(Calendar.YEAR)+"";
                name = foodList.get(0);
                calories = foodList.get(1);
                fat = foodList.get(2);
                carbs = foodList.get(3);
                protein = foodList.get(4);

                dbFood = new DatabaseFood(getContext());
                dbFood.insert(date, name, calories, fat, carbs, protein);
                dbFood.close();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public ArrayList<String> getSubmittedFood(View view) {
        ArrayList<String> arrayList = new ArrayList<String>();
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.activity_food_entry);

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
