package com.example.todoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import com.example.todoapp.model.Task;

@Dao
public interface TaskDao {

    @Insert()
    void addTask(TaskEntity taskEntity);

    @Query("SELECT * FROM taskTable WHERE categoryName=:categoryName")
    LiveData<List<TaskEntity>> getTasksOfCategory(String categoryName);

    @Delete
    void DeleteTask(TaskEntity taskEntity);
}
