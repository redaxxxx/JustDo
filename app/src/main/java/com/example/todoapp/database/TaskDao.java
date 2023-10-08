package com.example.todoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import com.example.todoapp.model.Task;

@Dao
public interface TaskDao {

    @Insert()
    void addTask(TaskEntity taskEntity);

    @Update()
    int updateTask(TaskEntity taskEntity);

    @Query("SELECT * FROM taskTable WHERE categoryName=:categoryName")
    LiveData<List<TaskEntity>> getTasksOfCategory(String categoryName);

    @Query("SELECT * FROM taskTable WHERE id=:id")
    LiveData<TaskEntity> getTaskById(int id);

    @Query("SELECT * FROM taskTable WHERE datePicker=:datePicker")
    LiveData<List<TaskEntity>> getTodayTasks(String datePicker);

    @Delete
    void DeleteTask(TaskEntity taskEntity);

    @Query("SELECT COUNT(*) FROM taskTable WHERE categoryName= :categoryName")
    int getRowCount(String categoryName);
}
