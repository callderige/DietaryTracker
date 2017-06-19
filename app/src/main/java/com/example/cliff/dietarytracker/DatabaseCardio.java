package com.example.cliff.dietarytracker;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseCardio extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cardio.db";
    public static final String FOOD_TABLE_NAME = "cardio";
    public static final String FOOD_COLUMN_ID = "_id";
    public static final String FOOD_COLUMN_DATE_ENTERED = "date_entered";
    public static final String FOOD_COLUMN_NAME = "name";
    public static final String FOOD_COLUMN_HOURS = "hours";
    public static final String FOOD_COLUMN_MINUTES = "minutes";
    public static final String FOOD_COLUMN_DISTANCE = "distance";
    public static final String FOOD_COLUMN_LAPS = "laps";

    public DatabaseCardio(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table cardio " +
                        "(_id integer primary key, date_entered text, name text, hours text, minutes text, distance text, laps text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cardio");
        onCreate(db);
    }

    public boolean insert(String date, String name, String hours, String minutes, String distance, String laps) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date_entered", date);
        contentValues.put("name", name);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("distance", distance);
        contentValues.put("laps", laps);

        db.insert("cardio", null, contentValues);

        return true;
    }

    public boolean delete(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM cardio WHERE _id = '"+_id+"'");

        return true;
    }

    public boolean findCardioToday(String date) {
        boolean didCardioToday = false;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cardio WHERE date_entered='"+date+"'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            didCardioToday = true;
        }

        return didCardioToday;
    }

    public ArrayList<String> findAll() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String row = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cardio", null);
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
        Cursor cursor = db.rawQuery("SELECT _id, name, hours, minutes, distance, laps FROM cardio WHERE _id="+_id+"", null);
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
        String hours = "";
        String minutes = "";
        String distance = "";
        String laps = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM cardio WHERE date_entered='"+date+"'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            row = "";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row += cursor.getString(i)+ "|";

            }

            id = row.split("\\|")[0];
            name = row.split("\\|")[2];
            hours = row.split("\\|")[3];
            minutes = row.split("\\|")[4];
            distance = row.split("\\|")[5];
            laps = row.split("\\|")[6];

            arrayList.add("Id:" +id+"\n"+
                    "Cardio name: "+name+"\n" +
                    "Hours: "+hours + ", Minutes: "+minutes+"\n" +
                    "Distance: "+distance+ " miles, Laps: "+laps
            );
            cursor.moveToNext();
        }
        return arrayList;
    }

    public boolean update(int  _id, String name, String hours, String minutes, String distance, String laps) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("hours", hours);
        contentValues.put("minutes", minutes);
        contentValues.put("distance", distance);
        contentValues.put("laps", laps);

        db.update("cardio", contentValues, "_id="+_id, null);

        return true;
    }

    public ArrayList<String> cardioStatistics() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT name, hours, minutes, distance, laps FROM cardio", null);
        int totalCardioEntries = 0;
        int totalHours = 0;
        int totalMinutes = 0;
        int totalDistance = 0;
        int totalLaps = 0;
        int totalHikes = 0;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            totalCardioEntries += 1;
            if (!cursor.getString(cursor.getColumnIndex("hours")).equals("NA")) {
                totalHours += Integer.parseInt(cursor.getString(cursor.getColumnIndex("hours")));
            }
            if (!cursor.getString(cursor.getColumnIndex("minutes")).equals("NA")) {
                totalMinutes += Integer.parseInt(cursor.getString(cursor.getColumnIndex("minutes")));
            }
            if (!cursor.getString(cursor.getColumnIndex("distance")).equals("NA")) {
                totalDistance += Integer.parseInt(cursor.getString(cursor.getColumnIndex("distance")));
            }
            if (!cursor.getString(cursor.getColumnIndex("laps")).equals("NA")) {
                totalLaps += Integer.parseInt(cursor.getString(cursor.getColumnIndex("laps")));
            }
            if (cursor.getString(cursor.getColumnIndex("name")).matches("(?i:.*hike.*)")) {
                totalHikes += 1;
            }

            cursor.moveToNext();
        }

        arrayList.add(totalCardioEntries+"");
        arrayList.add(totalHours+"");
        arrayList.add(totalMinutes+"");
        arrayList.add(totalDistance+"");
        arrayList.add(totalLaps+"");
        arrayList.add(totalHikes+"");
        return arrayList;
    }
}