package com.example.todoapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "taskTable")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String taskName;
    private String taskDescription;
    private String categoryName;
    private LocalDate datePicker;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDate getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(LocalDate datePicker) {
        this.datePicker = datePicker;
    }
}
