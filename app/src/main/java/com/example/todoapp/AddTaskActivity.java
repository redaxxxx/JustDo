package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.ActivityAddTaskBinding;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;
    private Repository repository;
    private AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        database = AppDatabase.getmInstance(this);
        repository = new Repository(database);

        TaskEntity taskEntity = new TaskEntity();
    }

    private boolean isValidate(){
        if (TextUtils.isEmpty(binding.taskTitleEt.getText().toString())){
            binding.taskTitleOutline.setErrorEnabled(true);
            binding.taskTitleOutline.setError("Please, enter task title");
            return false;
        }
        if (TextUtils.isEmpty(binding.taskDateEt.getText().toString())){
            binding.taskTitleOutline.setErrorEnabled(true);
            binding.taskDateOutline.setError("Please, pick date");
            return false;
        }
        if (TextUtils.isEmpty(binding.taskDescriptionEt.getText().toString())){
            binding.taskDescriptionOutline.setErrorEnabled(true);
            binding.taskDescriptionOutline.setError("Please, enter description");
            return false;
        }

        return true;
    }
}