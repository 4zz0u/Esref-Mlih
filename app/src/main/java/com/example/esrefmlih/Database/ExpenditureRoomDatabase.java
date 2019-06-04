package com.example.esrefmlih.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = Expenditure.class, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ExpenditureRoomDatabase extends RoomDatabase {

    // Dao paramater, indicating the DAO used in this database
    public abstract ExpenditureDao expenditureDao();

    // Making the database instance as a singleton
    private static volatile ExpenditureRoomDatabase INSTANCE;

    // Adding a migration constant for the date column
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE expenditure_table "
                    + " ADD COLUMN date LONG");
        }
    };
    public static ExpenditureRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpenditureRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context, ExpenditureRoomDatabase.class, "expenditure_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
