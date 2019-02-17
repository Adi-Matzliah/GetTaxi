package com.exercise.here.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaxiDao {

    @Query("SELECT * FROM taxi")
    LiveData<List<Taxi>> getAll();

    @Query ("SELECT * FROM taxi ORDER BY eta ASC")
    LiveData<List<Taxi>> loadAllByOrder();

    @Query ("SELECT * FROM taxi ORDER BY eta ASC")
    Single<List<Taxi>> loadAllByOrderRX();

    @Query("SELECT * FROM taxi WHERE id IN (:userIds)")
    LiveData<List<Taxi>> loadAllByIds(int[] userIds);

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(Taxi taxis);

    @Insert(onConflict = REPLACE)
    void insertOrUpdateAll(Taxi... taxis);
}