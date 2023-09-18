package com.example.todoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface TaskDao {

    @Insert(OnConflictStrategy.REPLACE)
    void addTask(TaskEntity taskEntity);
}
