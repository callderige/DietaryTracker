package com.example.cliff.dietarytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {
    public static final String MY_SETTINGS = "mySettings";
    public static final String MY_ALLOWED_CALORIES = "myMaxCalories";
    SharedPreferences sharedPreferences;
    Button button;
    EditText editText;
    RadioGroup radioGroup;
    String gender;
    int weight;
    int height;
    int age;
    int radioIndex;
    int calorieDeficit;
    int allowedCalories;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        button = (Button)findViewById(R.id.save_settings);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_genders);
        context = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editText = (EditText) findViewById(R.id.edit_text_weight);
                if (!editText.getText().toString().trim().equals("")) {
                    weight = Integer.parseInt(editText.getText().toString().trim());
                } else {
                    weight = 0;
                }

                editText = (EditText) findViewById(R.id.edit_text_height);
                if (!editText.getText().toString().trim().equals("")) {
                    height = Integer.parseInt(editText.getText().toString().trim());
                } else {
                    height = 0;
                }

                editText = (EditText) findViewById(R.id.edit_text_age);
                if (!editText.getText().toString().trim().equals("")) {
                    age = Integer.parseInt(editText.getText().toString().trim());
                } else {
                    age = 0;
                }

                radioIndex = radioGroup.getCheckedRadioButtonId();
                if (radioIndex != -1) {
                    RadioButton selected = (RadioButton) findViewById(radioIndex);
                    gender = selected.getText().toString().toLowerCase();
                }

                allowedCalories = basalMetabolicRate(gender, weight, height, age);

                editText = (EditText) findViewById(R.id.edit_text_calorie_deficit);
                if (!editText.getText().toString().trim().equals("")) {
                    calorieDeficit = Integer.parseInt(editText.getText().toString().trim());
                    allowedCalories -= calorieDeficit;
                } else {
                    allowedCalories = 0;
                }

                editor.putInt(MY_ALLOWED_CALORIES, allowedCalories);
                editor.apply();

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private int basalMetabolicRate (String gender, int pounds, int inches, int age) {
        int BMR = 0;

        switch (gender) {
            case "male":
                BMR = (int) Math.floor(66 + (6.23 * pounds) + (12.7 * inches) - (6.8 * age));
                break;
            case "female":
                BMR = (int) Math.floor(655 + (4.35 * pounds) + (4.7 * inches) - (4.7 * age));
                break;
            default:
                BMR = 1500;
                break;
        }

        return BMR;
    }
}
