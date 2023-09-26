package com.example.todoapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.Repository;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.utils.AppExecutors;
import com.example.todoapp.model.Task;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<TaskEntity>> taskLiveData;
    public TaskViewModel(Repository repository){
        this.repository = repository;
    }

    public void insertTask(TaskEntity taskEntity){
        repository.insertTask(taskEntity);
    }
    public void deleteTask(TaskEntity taskEntity){
        repository.deleteTask(taskEntity);
    }

    public LiveData<List<TaskEntity>> getTasksOfCategory(String categoryName){
        taskLiveData = repository.getTaskOfCategory(categoryName);
        return taskLiveData;
    }


}
