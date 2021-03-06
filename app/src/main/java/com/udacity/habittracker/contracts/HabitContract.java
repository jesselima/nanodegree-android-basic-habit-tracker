package com.udacity.habittracker.contracts;

import android.provider.BaseColumns;


public final class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        /* The name of the table. */
        public final static String TABLE_NAME = "habit";
        public final static String _TABLE_NAME_OLD = "_habit_old";

        // It's the id of the habit. It's auto generated id by the contracts base.
        public final static String _ID = BaseColumns._ID;

        // It's the name of the habit to be tracked.
        public final static String COLUMN_HABIT_NAME = "name";
        // It's the date that such habit start to be tracked.
        public final static String COLUMN_HABIT_START_DATE = "start_date";
        // Must have value: 0 for DAILY, 1 for WEEKDLY and 2 MONTHLY
        public final static String COLUMN_HABIT_FREQUENCY = "frequency";
        // It's the target number of times the habit must be done in the given frequency.
        public final static String COLUMN_HABIT_TARGET = "target";
        // Must have values from 1 to 5
        public final static String COLUMN_HABIT_PRIORITY = "priority";


    }

}
