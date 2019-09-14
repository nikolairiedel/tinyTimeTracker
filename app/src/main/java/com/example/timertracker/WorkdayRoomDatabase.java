package com.example.timertracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Workday.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class WorkdayRoomDatabase extends RoomDatabase {

    private static volatile WorkdayRoomDatabase INSTANCE;

    static WorkdayRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WorkdayRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WorkdayRoomDatabase.class, "workday_database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WorkdayDao workdayDao();
}
