package com.example.todoapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import com.example.todoapp.R;
import com.example.todoapp.Repository;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.ActivityAddTaskBinding;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;
    private Repository repository;
    private AppDatabase database;
    private String categoryName;

    private DatePickerDialog datePickerDialog;
    private int mYear;
    private int mMonth;
    private int mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        database = AppDatabase.getmInstance(this);
        repository = new Repository(database);

        //set Date time
        setDateTimeField();

        //appear date picker when touch editText
        binding.taskDateEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                datePickerDialog.show();
                return false;
            }
        });

        //Get text of selected chip
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    categoryName = compoundButton.getText().toString();
                }
            }
        };

        binding.learningChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.meetingChip.setOnCheckedChangeListener(checkedChangeListener);
        binding.designChep.setOnCheckedChangeListener(checkedChangeListener);


        //insert task into database when clicked on create button
        binding.createNewTaskBtn.setOnClickListener(view -> {
            if (isValidate()){
                //insert data of task to database
                insertTask();
                startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
            }

        });
    }
    //insert method
    private void insertTask(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(binding.taskTitleEt.getText().toString());
        taskEntity.setCategoryName(categoryName);
        taskEntity.setTaskDescription(binding.taskDescriptionEt.getText().toString());
        taskEntity.setDatePicker(binding.taskDateEt.getText().toString());

        repository.insertTask(taskEntity);
    }

    //Set Date
    private void setDateTimeField(){
        Calendar calender = Calendar.getInstance();
        mYear = calender.get(Calendar.YEAR);
        mMonth = calender.get(Calendar.MONTH);
        mDay = calender.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(selectedYear, selectedMonth, selectedDay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String formateDate = simpleDateFormat.format(startDate);

                binding.taskDateEt.setText(formateDate);
            }
        },mYear, mMonth, mDay);

        datePickerDialog.setTitle("Select Date");
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    // validate input text field
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