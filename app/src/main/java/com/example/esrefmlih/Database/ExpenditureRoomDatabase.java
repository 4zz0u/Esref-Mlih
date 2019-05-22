package com.example.esrefmlih.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Expenditure.class, version = 1, exportSchema = false)
public abstract class ExpenditureRoomDatabase extends RoomDatabase {

    // Dao paramater, indicating the DAO used in this database
    public abstract ExpenditureDao expenditureDao();

    // Making the database instance as a singleton
    private static volatile ExpenditureRoomDatabase INSTANCE;

    public static ExpenditureRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenditureRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context, ExpenditureRoomDatabase.class, "expenditure_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
