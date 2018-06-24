package com.udacity.habittracker;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.habittracker.models.Habit;
import com.udacity.habittracker.contracts.HabitContract;
import com.udacity.habittracker.contracts.HabitContract.HabitEntry;
import com.udacity.habittracker.helpers.HabitDbHelper;
import com.udacity.habittracker.utils.HabitDbUtils;

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
                insertHabit();
            }
        });

        Button btnRead = findViewById(R.id.btn_read_data);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readHabits();
            }
        });

        Button btnDummyData = findViewById(R.id.btn_insert_dummy_data);
        btnDummyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDummyData();
            }
        });

        Button btnDeleteDataBase = findViewById(R.id.btn_delete_data_base);
        btnDeleteDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDatabase(HabitDbHelper.DATABASE_NAME);
            }
        });
    }

    /* Get contracts from resources and add to the contracts base as a dummy contracts */
    private void insertDummyData() {

        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        Resources resources = getResources();

        String[] habitsArraySize = resources.getStringArray(R.array.dummy_habit_names);
        String[] habitNames = resources.getStringArray(R.array.dummy_habit_names);
        String[] habitStartDates = resources.getStringArray(R.array.dummy_habit_start_date);
        int[] habitFrequency = resources.getIntArray(R.array.dummy_habit_frequency);
        int[] habitTarget = resources.getIntArray(R.array.dummy_habit_target);
        int[] habitPriority = resources.getIntArray(R.array.dummy_habit_priorities);

        ContentValues values = new ContentValues();

        for (int i = 0; i < habitsArraySize.length; i++) {

            Habit habit = new Habit();

            habit.setmHabitName(habitNames[i]);
            habit.setmHabitStartDate(habitStartDates[i]);
            habit.setmHabitFrequency(habitFrequency[i]);
            habit.setmHabitTarget(habitTarget[i]);
            habit.setmHabitPriority(habitPriority[i]);

            values.put(HabitEntry.COLUMN_HABIT_NAME, habit.getmHabitName());
            values.put(HabitEntry.COLUMN_HABIT_START_DATE, habit.getmHabitStartDate());
            values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, habit.getmHabitFrequency());
            values.put(HabitEntry.COLUMN_HABIT_TARGET, habit.getmHabitTarget());
            values.put(HabitEntry.COLUMN_HABIT_PRIORITY, habit.getmHabitPriority());

            // Insert a new row for the habit table in the database, returning the ID of that new row.
            long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
            // Show a toast message depending on whether or not the insertion was successful
            if (newRowId == -1) {
                // If the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, R.string.error_saving_habit, Toast.LENGTH_SHORT).show();
            } else {
                // LOG DATA FOR TESTING PURPOSE ONLY
                Log.v(">>>>>> INSERTED", " ===== New Row ID:" + newRowId + " - " +
                        String.valueOf(habit.getmHabitName()) + " - " +
                        String.valueOf(habit.getmHabitStartDate()) + " - " +
                        String.valueOf(habit.getmHabitFrequency()) + " - " +
                        String.valueOf(habit.getmHabitTarget()) + " - " +
                        String.valueOf(habit.getmHabitPriority()));
            }
        }
        Log.v(">>>>>>", "===== DUMMY DATA INSERT DONE! NOW CLICK 'READ HABITS' BUTTON ======");
        Toast.makeText(this, R.string.dummy_data_inserted, Toast.LENGTH_SHORT).show();

    }// CLOSE insertDummyData

    /**
     * Insert contracts from the form into tha database.
     */
    private void insertHabit() {

        /* Get contracts from user inputs in the form */
        String name = mEditTextName.getText().toString().trim();
        String startDate = currentYear + "-" + (currentMonth + 1) + "-" + currentDayOfMonth;
        String frequencyString = mEditTextFrequency.getText().toString().trim();
        int frequency = Integer.parseInt(frequencyString);
        String targetString = mEditTextTarget.getText().toString().trim();
        int target = Integer.parseInt(targetString);
        String priorityString = mEditTextPriority.getText().toString().trim();
        int priority = Integer.parseInt(priorityString);

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
            Toast.makeText(this, R.string.error_saving_habit, Toast.LENGTH_SHORT).show();
            Log.v("ERROR", "Error with saving habit");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, getString(R.string.orw_if_of_saved_habit) + newRowId, Toast.LENGTH_SHORT).show();
            Log.v("HABIT SAVED", "Row id of the saved habit: " + newRowId);
        }
    }// CLOSE INSERT


    private void readHabits() {

        /*
         * The method "read()" from the class  HabitDbUtils returns a Cursor object.
         * Access the database and read the data using the query() method.
         */
        Cursor cursor = HabitDbUtils.read(this);

        /* Verifies if there is at least 1 result from database. If there is no data returned */
        if (cursor.getCount() < 1){
            Toast.makeText(this, R.string.no_data_in_database, Toast.LENGTH_LONG).show();
            return;
        }

        try {
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

                // LOG DATA FOR TESTING PURPOSE ONLY
                Log.v(">>>>>> ID", String.valueOf(currentID));
                Log.v(">>>>>> Name", String.valueOf(currentName));
                Log.v(">>>>>> Start Date", String.valueOf(currentStartDate));
                Log.v(">>>>>> Frequency", String.valueOf(currentFrequency));
                Log.v(">>>>>> Target", String.valueOf(currentTarget));
                Log.v(">>>>>> Priority", String.valueOf(currentPriority));
                Log.v("=============", "================");

            }
        } finally {
            cursor.close();
            Toast.makeText(this, R.string.see_logcat_for_results, Toast.LENGTH_LONG).show();
        }
    }

} // CLOSE MainActivity
