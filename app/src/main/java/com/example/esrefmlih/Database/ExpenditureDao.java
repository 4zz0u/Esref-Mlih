package com.example.esrefmlih.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExpenditureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expenditure expenditure);

    @Update
    void update(Expenditure expenditure);

    @Query("SELECT * FROM expenditure_table")
    LiveData<List<Expenditure>> getAllExpenditures();

    @Delete
    void delete(Expenditure expenditure);

    @Query("DELETE FROM expenditure_table")
    void deleteAll();

}