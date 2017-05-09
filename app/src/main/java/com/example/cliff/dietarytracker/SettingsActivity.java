package com.example.cliff.dietarytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    public static final String MY_SETTINGS = "mySettings";
    public static final String MY_WEIGHT = "myWeight";
    public static final String MY_ALLOWED_CALORIES = "myMaxCalories";
    SharedPreferences sharedPreferences;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        button = (Button)findViewById(R.id.save_settings);
        final Context context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int weight;
                int maxCalories;

                editText = (EditText) findViewById(R.id.edit_text_weight);
                if (!editText.getText().toString().trim().equals("")) {
                    weight = Integer.parseInt(editText.getText().toString().trim());
                    editor.putInt(MY_WEIGHT, weight);
                } else {
                    editor.putInt(MY_WEIGHT, 100);
                }

                editText = (EditText) findViewById(R.id.edit_text_max_calories);
                if (!editText.getText().toString().trim().equals("")) {
                    maxCalories = Integer.parseInt(editText.getText().toString().trim());
                    editor.putInt(MY_ALLOWED_CALORIES, maxCalories);
                } else {
                    editor.putInt(MY_ALLOWED_CALORIES, 1500);
                }

                editor.apply();

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
