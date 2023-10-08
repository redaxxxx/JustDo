package com.example.todoapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.model.Task;
import com.example.todoapp.utils.AppExecutors;

import java.util.List;

public class Repository {
    private AppDatabase database;
    private int rowCount;;
    public Repository(AppDatabase database){
        this.database = database;
    }
    public void insertTask(TaskEntity taskEntity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.taskDao().addTask(taskEntity);
            }
        });
    }

    public LiveData<List<TaskEntity>> getTaskOfCategory(String categoryName){
        return database.taskDao().getTasksOfCategory(categoryName);
    }

    public LiveData<List<TaskEntity>> getTodayTasks(String datePicker){
        return database.taskDao().getTodayTasks(datePicker);
    }

    public LiveData<TaskEntity> getTaskById(int id){
        return database.taskDao().getTaskById(id);
    }
    private int rowUpdated = 0;
    public int updateTask(TaskEntity taskEntity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               rowUpdated = database.taskDao().updateTask(taskEntity);
            }
        });
        return rowUpdated;
    }

    public void deleteTask(TaskEntity taskEntity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.taskDao().DeleteTask(taskEntity);
            }
        });

    }
    public int getRowCount(String categoryName){
        return database.taskDao().getRowCount(categoryName);
    }
}
