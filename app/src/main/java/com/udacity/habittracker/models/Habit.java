package com.udacity.habittracker.models;


public class Habit {

    private String mHabitName;
    private String mHabitStartDate;
    private int mHabitFrequency;
    private int mHabitTarget;
    private int mHabitPriority;

    public Habit() {
    }

    public Habit(String mHabitName, String mHabitStartDate, int mHabitFrequency, int mHabitTarget, int mHabitPriority) {
        this.mHabitName = mHabitName;
        this.mHabitStartDate = mHabitStartDate;
        this.mHabitFrequency = mHabitFrequency;
        this.mHabitTarget = mHabitTarget;
        this.mHabitPriority = mHabitPriority;
    }

    public String getmHabitName() {
        return mHabitName;
    }

    public void setmHabitName(String mHabitName) {
        this.mHabitName = mHabitName;
    }

    public String getmHabitStartDate() {
        return mHabitStartDate;
    }

    public void setmHabitStartDate(String mHabitStartDate) {
        this.mHabitStartDate = mHabitStartDate;
    }

    public int getmHabitFrequency() {
        return mHabitFrequency;
    }

    public void setmHabitFrequency(int mHabitFrequency) {
        this.mHabitFrequency = mHabitFrequency;
    }

    public int getmHabitTarget() {
        return mHabitTarget;
    }

    public void setmHabitTarget(int mHabitTarget) {
        this.mHabitTarget = mHabitTarget;
    }

    public int getmHabitPriority() {
        return mHabitPriority;
    }

    public void setmHabitPriority(int mHabitPriority) {
        this.mHabitPriority = mHabitPriority;
    }
}
