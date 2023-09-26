package com.example.todoapp.viewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapp.Repository;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.viewModel.TaskViewModel;

public class TaskViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;
    public TaskViewModelFactory(Repository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TaskViewModel(repository);

    }
}
