package com.example.cliff.dietarytracker;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseFood extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "food.db";
    public static final String FOOD_TABLE_NAME = "food";
    public static final String FOOD_COLUMN_ID = "_id";
    public static final String FOOD_COLUMN_DATE_ENTERED = "date_entered";
    public static final String FOOD_COLUMN_NAME = "name";
    public static final String FOOD_COLUMN_CALORIES = "calories";
    public static final String FOOD_COLUMN_FAT = "fat";
    public static final String FOOD_COLUMN_CARBS = "carbs";
    public static final String FOOD_COLUMN_PROTIEN = "protein";

    public DatabaseFood(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table food " +
                        "(_id integer primary key, date_entered text, name text, calories text, fat text, carbs text, protein text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food");
        onCreate(db);
    }

    public boolean insert(String date, String name, String calories, String fat, String carbs, String protein) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date_entered", date);
        contentValues.put("name", name);
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("protein", protein);

        db.insert("food", null, contentValues);

        return true;
    }

    public boolean delete(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM food WHERE _id="+_id);

        return true;
    }

    public String findCaloriesToday(String date) {
        int calories = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT calories FROM food WHERE date_entered='"+date+"'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                try {
                    calories += Integer.parseInt(cursor.getString(i));
                } catch (Exception e) {
                    calories += 0;
                }
            }
            cursor.moveToNext();
        }

        return calories+"";
    }

    public ArrayList<String> findAll() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String row = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            row = "";

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i)+ "|";
            }
            arrayList.add(row);
            cursor.moveToNext();
        }

        return arrayList;
    }

    public String findOne(int _id) {
        String row = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, name, calories, fat, carbs, protein FROM food WHERE _id="+_id+"", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i)+ "|";
            }
            cursor.moveToNext();
        }

        return row;
    }

    public ArrayList<String> findSome(String date) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String row;
        String id = "";
        String name = "";
        String calories = "";
        String fat = "";
        String carbs = "";
        String protein = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM food WHERE date_entered='"+date+"'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            row = "";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i)+ "|";

            }

            id = row.split("\\|")[0];
            name = row.split("\\|")[2];
            calories = row.split("\\|")[3];
            fat = row.split("\\|")[4];
            carbs = row.split("\\|")[5];
            protein = row.split("\\|")[6];

            arrayList.add("Id:" +id+"\n"+
                    "Food name: "+name+"\n" +
                    "Calories: "+calories + "\n" +
                    "Fat: "+fat+ ", Carbs: "+carbs+ ", Protein: "+protein
            );
            cursor.moveToNext();
        }
        return arrayList;
    }

    public boolean update(int _id, String name, String calories, String fat, String carbs, String protein) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("protein", protein);

        db.update("food", contentValues, "_id="+_id, null);

        return true;
    }

    public ArrayList<String> foodStatistics() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT name, calories, fat, carbs, protein FROM food", null);
        int totalCalories = 0;
        int totalFat = 0;
        int totalCarbs = 0;
        int totalProtein = 0;
        int totalBeer = 0;
        int totalPotato = 0;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (!cursor.getString(cursor.getColumnIndex("calories")).equals("NA")) {
                totalCalories += Integer.parseInt(cursor.getString(cursor.getColumnIndex("calories")));
            }
            if (!cursor.getString(cursor.getColumnIndex("fat")).equals("NA")) {
                totalFat += Integer.parseInt(cursor.getString(cursor.getColumnIndex("fat")));
            }
            if (!cursor.getString(cursor.getColumnIndex("carbs")).equals("NA")) {
                totalCarbs += Integer.parseInt(cursor.getString(cursor.getColumnIndex("carbs")));
            }
            if (!cursor.getString(cursor.getColumnIndex("protein")).equals("NA")) {
                totalProtein += Integer.parseInt(cursor.getString(cursor.getColumnIndex("protein")));
            }
            if (!cursor.getString(cursor.getColumnIndex("name")).matches("(.*)beer(.*)")) {
                totalBeer += 1;
            }
            if (!cursor.getString(cursor.getColumnIndex("name")).matches("(.*)potato(.*)")) {
                totalPotato += 1;
            }
            cursor.moveToNext();
        }

        arrayList.add(totalCalories+"");
        arrayList.add(totalFat+"");
        arrayList.add(totalCarbs+"");
        arrayList.add(totalProtein+"");
        arrayList.add(totalBeer+"");
        arrayList.add(totalPotato+"");
        return arrayList;
    }
}