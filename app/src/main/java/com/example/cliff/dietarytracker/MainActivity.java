package com.example.cliff.dietarytracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView textViewCaloriesToday;
    CheckBox checkBoxCardioToday, checkBoxLiftingToday;
    DatabaseFood dbFood;
    DatabaseCardio dbCardio;
    DatabaseLifting dbLifting;
    ArrayList<String> arrayList;
    Context context;
    String date;
    int _id;
    public final static String EXTRA_MESSAGE = "com.example.cliff.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        date = Calendar.getInstance().get(Calendar.MONTH)+1 +"/" +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +"/" +
                Calendar.getInstance().get(Calendar.YEAR)+"";

        textViewCaloriesToday = (TextView) findViewById(R.id.text_view_calories_today);
        checkBoxCardioToday = (CheckBox) findViewById(R.id.check_box_cardio_today);
        checkBoxLiftingToday = (CheckBox) findViewById(R.id.check_box_lifting_today);

        dbFood = new DatabaseFood(this);
        dbCardio = new DatabaseCardio(this);
        dbLifting = new DatabaseLifting(this);

        setTodaysTracking();

        arrayList = dbFood.findSome(date);
        arrayList.addAll(dbCardio.findSome(date));
        arrayList.addAll(dbLifting.findSome(date));

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.entries_list_view, arrayList);
        ListView listView = (ListView) findViewById(R.id.list_view_entries_today);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = arrayList.get(position);

                if (message.toLowerCase().contains("food".toLowerCase())) {
                    String temp = message.split("id:")[0];
                    temp = temp.split("Food name")[0];
                    temp = temp.replace("Id:", "").trim();
                    _id = Integer.parseInt(temp);

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure you want to delete this food entry?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbFood.delete(_id);
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = Integer.toString(_id)+"|"+"food";
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else if (message.toLowerCase().contains("cardio".toLowerCase())) {
                    String temp = message.split("id:")[0];
                    temp = temp.split("Cardio name:")[0];
                    temp = temp.replace("Id:", "").trim();
                    _id = Integer.parseInt(temp);

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure you want to delete this cardio entry?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbCardio.delete(_id);
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = _id+"|"+"cardio";
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                } else if (message.toLowerCase().contains("lift".toLowerCase())) {
                    String temp = message.split("id:")[0];
                    temp = temp.split("Lift name:")[0];
                    temp = temp.replace("Id:", "").trim();
                    _id = Integer.parseInt(temp);

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure you want to delete this lift entry?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbLifting.delete(_id);
                            dialog.dismiss();
                            dbLifting.close();
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = _id+"|"+"lifting";
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.newEntry:
                intent = new Intent(this, EntryActivity.class);
                this.startActivity(intent);
                break;

            case R.id.openStatistics:
                intent = new Intent(this, EntryActivity.class);
                this.startActivity(intent);
                break;

            case R.id.openSettings:
                intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;

            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                finish();
                this.startActivity(intent);
                return true;
        }

        return true;
    }

    public void setTodaysTracking() {
        textViewCaloriesToday.setText("Calories today: "+dbFood.findCaloriesToday(date)+ "/1800");

        if (dbCardio.findCardioToday(date)) {
            checkBoxCardioToday.setChecked(true);
            checkBoxCardioToday.setVisibility(View.VISIBLE);
        }

        if (dbLifting.findLiftingToday(date)) {
            checkBoxLiftingToday.setChecked(true);
            checkBoxLiftingToday.setVisibility(View.VISIBLE);
        }
    }
}
