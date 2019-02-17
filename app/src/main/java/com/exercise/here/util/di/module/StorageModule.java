package com.exercise.here.util.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.exercise.here.data.TaxiDao;
import com.exercise.here.data.TaxisDatabase;
import com.exercise.here.util.di.scope.AppScope;

import java.io.File;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import dagger.Module;
import dagger.Provides;


@Module
public class StorageModule {

    @NonNull
    private String fileName;

    @NonNull
    private RoomDatabase roomDB;

    public StorageModule(Context context, String fileName) {
        this.fileName = fileName;
        this.roomDB = Room.databaseBuilder(context, TaxisDatabase.class, fileName).build();
    }

    @Provides
    @AppScope
    File provideFileInternalStorage(Context context) {
        return context.getFileStreamPath(fileName);
    }

    @Provides
    @AppScope
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @AppScope
    synchronized TaxisDatabase provideDB(Context context) {
        return Room.databaseBuilder(context,
                TaxisDatabase.class, fileName).build();
    }

    @Provides
    @AppScope
    TaxiDao provideDao(TaxisDatabase db) {
        return db.taxiDao();
    }

/*    @Provides
    @AppScope
    StorageRepository provideStorageRepository(TaxisDatabase roomDB){
        return new StorageRepository(roomDB);
    }*/
}
