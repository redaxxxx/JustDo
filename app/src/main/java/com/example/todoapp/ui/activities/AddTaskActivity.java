package com.example.todoapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.Repository;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.ActivityAddTaskBinding;
import com.example.todoapp.utils.Constants;
import com.example.todoapp.viewModel.TaskViewModel;
import com.example.todoapp.viewModel.TaskViewModelFactory;
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
    private boolean mTaskHasChanged = false;
    private int taskId;
    private TaskViewModel viewModel;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mTaskHasChanged = true;
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        database = AppDatabase.getmInstance(this);
        repository = new Repository(database);

        TaskViewModelFactory factory = new TaskViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);


        Intent intentToEditTask = getIntent();
        if (intentToEditTask == null){
            binding.addNewTaskTv.setText(getResources().getString(R.string.add_new_task_text));
            binding.createNewTaskBtn.setText(getResources().getString(R.string.create_new_task_text));

        }else{
            int id = intentToEditTask.getIntExtra(Constants.TASK_ID, 1000);

            binding.addNewTaskTv.setText(getResources().getString(R.string.edit_task_text));
            binding.createNewTaskBtn.setText(getResources().getString(R.string.save_task));

            viewModel.getTaskById(id).observe(this, new Observer<TaskEntity>() {
                @Override
                public void onChanged(TaskEntity taskEntity) {
                    binding.taskTitleEt.setText(taskEntity.getTaskName());
                    binding.taskDescriptionEt.setText(taskEntity.getTaskDescription());
                    binding.taskDateEt.setText(taskEntity.getDatePicker());

                    switch (taskEntity.getCategoryName()){
                        case "Design":
                            binding.designChep.setSelected(true);
                            break;
                        case "Learning":
                            binding.learningChip.setSelected(true);
                            break;
                        case "Meeting":
                            binding.meetingChip.setSelected(true);
                            break;
                    }
                }
            });
        }
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
                if (intentToEditTask == null){
                    insertTask();
                }else {
                    updateTask();
                }
                
            }

        });

        binding.taskTitleEt.setOnTouchListener(onTouchListener);
        binding.taskDateEt.setOnTouchListener(onTouchListener);
        binding.taskDescriptionEt.setOnTouchListener(onTouchListener);
        binding.meetingChip.setOnTouchListener(onTouchListener);
        binding.designChep.setOnTouchListener(onTouchListener);
        binding.learningChip.setOnTouchListener(onTouchListener);
    }
    //insert method
    private void insertTask(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(binding.taskTitleEt.getText().toString());
        taskEntity.setCategoryName(categoryName);
        taskEntity.setTaskDescription(binding.taskDescriptionEt.getText().toString());
        taskEntity.setDatePicker(binding.taskDateEt.getText().toString());

        viewModel.insertTask(taskEntity);
//        repository.insertTask(taskEntity);
        startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
    }

    private void updateTask(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(binding.taskTitleEt.getText().toString());
        taskEntity.setCategoryName(categoryName);
        taskEntity.setTaskDescription(binding.taskDescriptionEt.getText().toString());
        taskEntity.setDatePicker(binding.taskDateEt.getText().toString());

        int rowUpdated = viewModel.updateTask(taskEntity);

        startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
        Toast.makeText(this, "Updating is successful "+ rowUpdated, Toast.LENGTH_SHORT).show();
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy");
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