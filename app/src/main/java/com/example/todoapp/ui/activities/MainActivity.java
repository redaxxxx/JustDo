package com.example.todoapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todoapp.R;
import com.example.todoapp.Repository;
import com.example.todoapp.adapters.CategoryAdapter;
import com.example.todoapp.adapters.TaskAdapter;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.model.Category;
import com.example.todoapp.model.Task;
import com.example.todoapp.utils.AppExecutors;
import com.example.todoapp.utils.CategoryClickListener;
import com.example.todoapp.utils.Constants;
import com.example.todoapp.utils.ItemOnClickListener;
import com.example.todoapp.viewModel.TaskViewModel;
import com.example.todoapp.viewModel.TaskViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryClickListener, ItemOnClickListener {

    private List<Category> categoryList;
    private List<Task> taskList;
    private ActivityMainBinding binding;

    private TaskViewModel viewModel;
    private int numberOfDesignTasks ;
    private int numberOfMeetingTasks;
    private int numberOfLearningTasks;
    private BottomSheetBehavior bottomSheetBehavior;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppDatabase database = AppDatabase.getmInstance(this);
        Repository repository = new Repository(database);
        TaskViewModelFactory factory = new TaskViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        //get current date as string and put it in getTodayTasks() method
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        String dateFormatted = dateFormat.format(date);

        viewModel.getTodayTasks(dateFormatted).observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                prepareTodayTaskRecyclerView(taskEntities);
            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                numberOfDesignTasks = viewModel.getRowCount("Design");
                numberOfLearningTasks = viewModel.getRowCount("Learning");
                numberOfMeetingTasks = viewModel.getRowCount("Meeting");
                prepareCategoryRecyclerView(numberOfDesignTasks, numberOfMeetingTasks, numberOfLearningTasks);
                Log.d(Constants.TAG, "Row Count = " + numberOfDesignTasks);
            }
        });

//        getRowCount();


        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setPeekHeight(500);
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setPeekHeight(550);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.addButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            finish();
        });


    }

    private void prepareTodayTaskRecyclerView(List<TaskEntity> taskList){
        binding.todayTaskRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.todayTaskRv.setItemAnimator(new DefaultItemAnimator());
        binding.todayTaskRv.setHasFixedSize(true);
        TaskAdapter adapter = new TaskAdapter(this, taskList, this);
        binding.todayTaskRv.setAdapter(adapter);
    }

    private void prepareCategoryRecyclerView(int numberOfDesignTasks, int numberOfMeetingTasks, int numberOfLearningTasks){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.fa_paint, getResources().getString(R.string.category_design),
                numberOfDesignTasks, R.drawable.design_backgeound));

        categoryList.add(new Category(R.drawable.healthicons_group, getResources().getString(R.string.category_meeting),
                numberOfMeetingTasks, R.drawable.meeting_backgeound));

        categoryList.add(new Category(R.drawable.carbon_image, getResources().getString(R.string.category_learning),
                numberOfLearningTasks, R.drawable.learning_backgeound));

        binding.categoryRv.setHasFixedSize(true);
        binding.categoryRv.setItemAnimator(new DefaultItemAnimator());
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        CategoryAdapter adapter = new CategoryAdapter(categoryList,this);
        binding.categoryRv.setAdapter(adapter);
    }



    @Override
    public void categoryOnClick(String categoryName) {
        Intent intent = new Intent(MainActivity.this, TaskCategoriesActivity.class);
        intent.putExtra(Constants.CATEGORY_NAME, categoryName);
        startActivity(intent);
    }

    @Override
    public void itemOnClickListener(int taskId) {
        id = taskId;
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra(Constants.TASK_ID, taskId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}