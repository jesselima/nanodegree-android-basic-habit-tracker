package com.udacity.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HabitDbHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "habit_tracker.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // The SQL statement  is set into a String object. This statement is passed as input of the method "execSQL". When this method is called it will create the habit table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_START_DATE + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_TARGET + " INTEGER, "
                + HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY + " INTEGER NOT NULL DEFAULT 3);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}