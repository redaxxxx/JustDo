package com.example.todoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCk = new Object();
    private static final String DATABASE_NAME = "task.db";

    private static AppDatabase mInstance;

    public static AppDatabase getmInstance(Context context){
        if (mInstance == null){
            synchronized (LOCk){
                mInstance = Room.databaseBuilder(
                        context,
                        AppDatabase.class,
                        DATABASE_NAME
                ).build();
            }
        }
        return mInstance;
    }

    public abstract TaskDao taskDao();
}
