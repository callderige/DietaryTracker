package com.example.cliff.dietarytracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    Button submitEdit;
    TextView editTitle;
    DatabaseFood dbFood;
    DatabaseCardio dbCardio;
    DatabaseLifting dbLifting;
    int _id;
    String name, calories, fat, carbs, protein, hours, minutes, distance, laps, sets, reps, weight, id, decideDb, message;
    String[][] columnHints = {
            {"Food namne", "Calories", "Fat", "Carbs", "Protein"},
            {"Cardio namne", "Hours", "Minutes", "Distance", "Laps"},
            {"Lifting namne", "Sets", "Reps", "Weight"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        id = message.split("\\|")[0];
        decideDb = message.split("\\|")[1];
        _id = Integer.parseInt(id);

        submitEdit = (Button) findViewById(R.id.submit_edit);
        editText = (EditText) findViewById(R.id.edit_text5);
        editTitle = (TextView) findViewById(R.id.edit_title);

        dbFood = new DatabaseFood(this);
        dbCardio = new DatabaseCardio(this);
        dbLifting = new DatabaseLifting(this);

        switch (decideDb) {
            case "food":
                editTitle.setText("Editing food entry");
                generateEditTextFields(dbFood.findOne(_id), "food");
                break;

            case "cardio":
                editTitle.setText("Editing cardio entry");
                generateEditTextFields(dbCardio.findOne(_id), "cardio");
                break;

            case "lifting":
                editTitle.setText("Editing lifting entry");
                editText.setVisibility(View.INVISIBLE);
                editText.setFocusable(false);
                generateEditTextFields(dbLifting.findOne(_id), "lifting");
                break;

            default:
                break;
        }

        submitEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ArrayList<String> arrayList = getEditTextFields();
                Intent intent = new Intent(view.getContext(), MainActivity.class);

                switch (decideDb) {
                    case "food":
                        name = arrayList.get(0);
                        calories = arrayList.get(1);
                        fat = arrayList.get(2);
                        carbs = arrayList.get(3);
                        protein = arrayList.get(4);

                        dbFood.update(_id, name, calories, fat, carbs, protein);

                        finish();
                        startActivity(intent);
                        break;

                    case "cardio":
                        name = arrayList.get(0);
                        hours = arrayList.get(1);
                        minutes = arrayList.get(2);
                        distance = arrayList.get(3);
                        laps = arrayList.get(4);

                        dbCardio.update(_id, name, hours, minutes, distance, laps);

                        finish();
                        startActivity(intent);
                        break;

                    case "lifting":
                        name = arrayList.get(0);
                        sets = arrayList.get(1);
                        reps = arrayList.get(2);
                        weight = arrayList.get(3);

                        dbLifting.update(_id, name, sets, reps, weight);

                        finish();
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public void generateEditTextFields (String row, String hints) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_edit);
        int count = row.split("\\|").length;
        String[][] columnHints = {
                {"Food name", "Calories", "Fat", "Carbs", "Protein"},
                {"Cardio name", "Hours", "Minutes", "Distance", "Laps"},
                {"Lifting name", "Sets", "Reps", "Weight"}
        };
        for (int i = 0; i < count; i++) {
            View view2 = layout.getChildAt(i);
            if (view2 instanceof EditText) {
                switch (hints) {
                    case "food":
                        ((EditText) view2).setText(row.split("\\|")[i]);
                        ((EditText) view2).setHint(columnHints[0][i-1]+" : "+row.split("\\|")[i]);
                        break;


                    case "cardio":
                        ((EditText) view2).setText(row.split("\\|")[i]);
                        ((EditText) view2).setHint(columnHints[1][i-1]+" : "+row.split("\\|")[i]);
                        break;

                    case "lifting":
                        ((EditText) view2).setText(row.split("\\|")[i]);
                        ((EditText) view2).setHint(columnHints[2][i-1]+" : "+row.split("\\|")[i]);
                        break;

                    default:
                        break;
                }

            }
        }
    }

    public ArrayList<String> getEditTextFields () {
        ArrayList<String> listArray = new ArrayList<String>();
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_edit);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof EditText) {
                if (((EditText) view).getText().toString().trim().equals("")) {
                    listArray.add("NA");
                } else {
                    listArray.add(((EditText) view).getText().toString());
                }
            }
        }

        return listArray;
    }
}