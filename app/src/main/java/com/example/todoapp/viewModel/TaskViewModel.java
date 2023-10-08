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
    private LiveData<List<TaskEntity>> todayTasksLiveData;
    private LiveData<TaskEntity> taskEntityLiveData;
    public TaskViewModel(Repository repository){
        this.repository = repository;
    }

    public void insertTask(TaskEntity taskEntity){
        repository.insertTask(taskEntity);
    }

    public int updateTask(TaskEntity taskEntity){
        int rowUpdated = repository.updateTask(taskEntity);
        return rowUpdated;
    }
    public void deleteTask(TaskEntity taskEntity){
        repository.deleteTask(taskEntity);
    }

    public LiveData<List<TaskEntity>> getTasksOfCategory(String categoryName){
        taskLiveData = repository.getTaskOfCategory(categoryName);
        return taskLiveData;
    }

    public LiveData<List<TaskEntity>> getTodayTasks(String datePicker){
        todayTasksLiveData = repository.getTodayTasks(datePicker);
        return todayTasksLiveData;
    }

    public LiveData<TaskEntity> getTaskById(int id){
        taskEntityLiveData = repository.getTaskById(id);
        return taskEntityLiveData;
    }

    public int getRowCount(String categoryName){
        return repository.getRowCount(categoryName);
    }

}
