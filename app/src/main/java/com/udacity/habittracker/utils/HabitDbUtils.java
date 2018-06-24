package com.udacity.habittracker.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.habittracker.contracts.HabitContract;
import com.udacity.habittracker.helpers.HabitDbHelper;


public final class HabitDbUtils {

    /**
     * Allows others classes instantiates a empty HabitDbUtils object.
     */
    public HabitDbUtils() {
    }

    public static Cursor read(Context context) {

        HabitDbHelper mHabitDbHelper = new HabitDbHelper(context);

        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_START_DATE,
                HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitContract.HabitEntry.COLUMN_HABIT_TARGET,
                HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY
        };

        // Perform a query on the habit table
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME, // The table to query
                projection,            // The columns to return
                null,         // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // Don't group the rows
                null,           // Don't filter by row groups
                HabitContract.HabitEntry._ID);

        return cursor;
    }


}

