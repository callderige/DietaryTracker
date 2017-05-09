package com.example.cliff.dietarytracker;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseLifting extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lifting.db";
    public static final String FOOD_TABLE_NAME = "lifting";
    public static final String FOOD_COLUMN_ID = "_id";
    public static final String FOOD_COLUMN_DATE_ENTERED = "date_entered";
    public static final String FOOD_COLUMN_NAME = "name";
    public static final String FOOD_COLUMN_SETS = "sets";
    public static final String FOOD_COLUMN_REPS = "reps";
    public static final String FOOD_COLUMN_WEIGHT = "weight";

    public DatabaseLifting(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table lifting " +
                        "(_id integer primary key, date_entered text, name text, sets text, reps text, weight text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lifting");
        onCreate(db);
    }

    public boolean insert(String date, String name, String sets, String reps, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date_entered", date);
        contentValues.put("name", name);
        contentValues.put("sets", sets);
        contentValues.put("reps", reps);
        contentValues.put("weight", weight);

        db.insert("lifting", null, contentValues);

        return true;
    }

    public boolean delete(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM lifting WHERE _id = '" + _id + "'");

        return true;
    }

    public boolean findLiftingToday(String date) {
        boolean didLiftingToday = false;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lifting WHERE date_entered='"+date+"'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            didLiftingToday = true;
        }

        return didLiftingToday;
    }

    public ArrayList<String> findAll() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String row = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lifting", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            row = "";

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i) + "|";
            }

            arrayList.add(row);
            cursor.moveToNext();
        }

        return arrayList;
    }

    public String findOne(int _id) {
        String row = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, name, sets, reps, weight FROM lifting WHERE _id="+_id+"", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
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
        String sets = "";
        String reps = "";
        String weight = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lifting WHERE date_entered='" + date + "'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            row = "";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i) + "|";

            }

            id = row.split("\\|")[0];
            name = row.split("\\|")[2];
            sets = row.split("\\|")[3];
            reps = row.split("\\|")[4];
            weight = row.split("\\|")[5];

            arrayList.add("Id:" + id + "\n" +
                    "Lift name: " + name + "\n" +
                    "Sets: " + sets + " Reps: " + reps + "\n" +
                    "Weight: " + weight +" lbs"
            );
            cursor.moveToNext();
        }
        return arrayList;
    }

    public boolean update(int _id, String name, String sets, String reps, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("sets", sets);
        contentValues.put("reps", reps);
        contentValues.put("weight", weight);

        db.update("lifting", contentValues, "_id="+_id, null);

        return true;
    }
}
