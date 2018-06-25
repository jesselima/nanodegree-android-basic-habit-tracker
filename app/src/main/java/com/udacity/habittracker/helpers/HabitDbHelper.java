package com.udacity.habittracker.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.udacity.habittracker.contracts.HabitContract;


public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "habit_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // The SQL statement  is set into a String object. This statement is passed as input of the method "execSQL". When this method is called it will create the habit table
    public static String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
            + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
            + HabitContract.HabitEntry.COLUMN_HABIT_START_DATE + " TEXT NOT NULL, "
            + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL, "
            + HabitContract.HabitEntry.COLUMN_HABIT_TARGET + " INTEGER, "
            + HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY + " INTEGER NOT NULL DEFAULT 3);";

    private static final String SQL_ALTER_TABLE = "ALTER TABLE " + HabitContract.HabitEntry.TABLE_NAME + "RENAME TO " + HabitContract.HabitEntry._TABLE_NAME_OLD + ";\n";


    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        String SQL_UPGRADE_TRANSACTION =
                SQL_ALTER_TABLE +
                        SQL_CREATE_HABIT_TABLE + ";\n" +
                        "INSERT INTO " + HabitContract.HabitEntry.TABLE_NAME + " ("
                        + HabitContract.HabitEntry._ID + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_NAME + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_START_DATE + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_TARGET + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY + " )\n"
                        + "SELECT "
                        + HabitContract.HabitEntry._ID + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_NAME + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_START_DATE + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_TARGET + ", "
                        + HabitContract.HabitEntry.COLUMN_HABIT_PRIORITY + "\n"
                        + " FROM " + HabitContract.HabitEntry._TABLE_NAME_OLD + ";\n" +
                        "DROP TABLE " + HabitContract.HabitEntry._TABLE_NAME_OLD + ";";

        /* Resources: https://stackoverflow.com/questions/8147440/android-database-transaction */
        db.beginTransaction();
        try {
            db.execSQL(SQL_UPGRADE_TRANSACTION);
            db.setTransactionSuccessful();
            onCreate(db);
        } catch (SQLException e) {
            Log.v("SQL Transaction Error", e.getMessage());
        } finally {
            db.endTransaction();
        }

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}