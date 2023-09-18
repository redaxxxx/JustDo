package com.example.todoapp;

import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.utils.AppExecutors;

public class Repository {
    private AppDatabase database;
    private Context context;
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
}
