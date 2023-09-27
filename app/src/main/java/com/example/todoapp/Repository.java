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

    public void deleteTask(TaskEntity taskEntity){
        database.taskDao().DeleteTask(taskEntity);
    }
}
