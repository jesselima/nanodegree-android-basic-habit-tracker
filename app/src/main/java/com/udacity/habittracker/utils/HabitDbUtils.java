package com.udacity.habittracker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.udacity.habittracker.R;
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


    public static long insert(Context context, String name, String startDate, int frequency, int target, int priority) {

        HabitDbHelper mHabitDbHelper = new HabitDbHelper(context);
        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_START_DATE, startDate);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY, frequency);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TARGET, target);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY, priority);

        // Insert a new row for the habit table in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(context, R.string.error_saving_habit, Toast.LENGTH_SHORT).show();
            Log.v("ERROR", "Error with saving habit");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            String idString = String.valueOf(newRowId);
            Toast.makeText(context, "Habit _ID: " + idString, Toast.LENGTH_SHORT).show();
            Log.v("HABIT SAVED", "Row id of the saved habit: " + idString);
        }
        return newRowId;
    }// CLOSE INSERT

}

