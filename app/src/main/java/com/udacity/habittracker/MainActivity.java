package com.udacity.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.habittracker.data.HabitContract;
import com.udacity.habittracker.data.HabitContract.HabitEntry;
import com.udacity.habittracker.data.HabitDbHelper;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private EditText mEditTextName, mEditTextFrequency, mEditTextTarget, mEditTextPriority;

    private HabitDbHelper mHabitDbHelper;

    private int currentYear;
    private int currentMonth;
    private int currentDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mHabitDbHelper = new HabitDbHelper(this);

        mEditTextName = findViewById(R.id.edt_name);
        mEditTextFrequency = findViewById(R.id.edt_frequency);
        mEditTextTarget = findViewById(R.id.edt_target);
        mEditTextPriority = findViewById(R.id.edt_priority);

        Button btnInsert = findViewById(R.id.btn_save_data);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPet();
            }
        });

        Button btnRead = findViewById(R.id.btn_read_data);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
            }
        });
    }



    private void insertPet() {

        String name = mEditTextName.getText().toString().trim();

        String startDate = currentYear + "-" + (currentMonth + 1) + "-" + currentDayOfMonth;

        String frequencyString = mEditTextFrequency.getText().toString().trim();
        int frequency = Integer.parseInt(frequencyString);

        String targetString = mEditTextTarget.getText().toString().trim();
        int target = Integer.parseInt(targetString);

        String priorityString = mEditTextPriority.getText().toString().trim();
        int priority = Integer.parseInt(priorityString);

        /**
         * String name = "Drink water";
         * String startDate = "2018-06-22";
         * int frequency = 1; // 0 for DAILY, 1 for WEEKDLY and 2 MONTHLY
         * int target = 3; // from 1 to 5. Where 5 is the highest priority
         * int priority = 1; // from 1 to 5. Where 5 is the highest priority
         *
        */

        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitEntry.COLUMN_HABIT_START_DATE, startDate);
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, frequency);
        values.put(HabitEntry.COLUMN_HABIT_TARGET, target);
        values.put(HabitEntry.COLUMN_HABIT_PRIORITY, priority);

        // Insert a new row for the habit table in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Row id of the saved habit: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }// CLOSE INSERT




    private void read(){

        // Create and/or open a database to read from it
        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_START_DATE,
                HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitEntry.COLUMN_HABIT_TARGET,
                HabitEntry.COLUMN_HABIT_PRIORITY
        };

        // Perform a query on the habit table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME, // The table to query
                projection,            // The columns to return
                null,         // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // Don't group the rows
                null,           // Don't filter by row groups
                HabitEntry.COLUMN_HABIT_PRIORITY);         // The sort order

        TextView displayView = findViewById(R.id.text_view_habits);

        try {
           
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_START_DATE + " - " +
                    HabitEntry.COLUMN_HABIT_FREQUENCY + " - " +
                    HabitEntry.COLUMN_HABIT_TARGET + " - " +
                    HabitEntry.COLUMN_HABIT_PRIORITY + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int startDateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_START_DATE);
            int frequencyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY);
            int targetColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TARGET);
            int priorityColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_PRIORITY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentStartDate = cursor.getString(startDateColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);
                int currentTarget = cursor.getInt(targetColumnIndex);
                int currentPriority = cursor.getInt(priorityColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +
                        currentID + " - " +
                        currentName + " - " +
                        currentStartDate + " - " +
                        currentFrequency + " - " +
                        currentTarget + " - " +
                        currentPriority));

                // TESTING
                Log.v("=====================", "=====================");
                Log.v(">>>>>> ID", String.valueOf(currentID));
                Log.v(">>>>>> Name", String.valueOf(currentName));
                Log.v(">>>>>> Start Date", String.valueOf(currentStartDate));
                Log.v(">>>>>> Frequency", String.valueOf(currentFrequency));
                Log.v(">>> Target In Frequency", String.valueOf(currentTarget));
                Log.v(">>>>>> Priority", String.valueOf(currentPriority));
            }
        } finally {
            cursor.close();
        }
        Log.v("===================== ", "=====================");
        }
    }
