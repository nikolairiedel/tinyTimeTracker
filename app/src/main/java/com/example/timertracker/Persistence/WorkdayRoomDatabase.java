package com.example.timertracker.Persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.timertracker.Model.ArrayListConverter;
import com.example.timertracker.Model.NestedArrayListConverter;
import com.example.timertracker.Model.Workday;

@Database(entities = {Workday.class}, version = 4)
@TypeConverters({ArrayListConverter.class, NestedArrayListConverter.class})
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
