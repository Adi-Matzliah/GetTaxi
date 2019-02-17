package com.exercise.here.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Taxi.class}, version = 1, exportSchema = false)
public abstract class TaxisDatabase extends RoomDatabase {
    public abstract TaxiDao taxiDao();
}