package com.example.roomtest1;

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

    @Query("SELECT * FROM expenditure_table WHERE category = :category")
    LiveData<List<Expenditure>> getExpendituresByCategory(int category);

    @Delete
    void delete(Expenditure expenditure);

}