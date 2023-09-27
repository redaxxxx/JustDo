package com.example.todoapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.todoapp.utils.CategoryClickListener;
import com.example.todoapp.utils.Constants;
import com.example.todoapp.viewModel.TaskViewModel;
import com.example.todoapp.viewModel.TaskViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryClickListener {

    private List<Category> categoryList;
    private List<Task> taskList;
    private ActivityMainBinding binding;

    private TaskViewModel viewModel;
    private BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppDatabase database = AppDatabase.getmInstance(this);
        Repository repository = new Repository(database);
        TaskViewModelFactory factory = new TaskViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        //get current date as string and put it in getTodayTasks() method


        categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.fa_paint, getResources().getString(R.string.category_design),
                5, getResources().getColor(R.color.red)));

        categoryList.add(new Category(R.drawable.healthicons_group, getResources().getString(R.string.category_meeting),
                1, getResources().getColor(R.color.yellow)));

        categoryList.add(new Category(R.drawable.carbon_image, getResources().getString(R.string.category_learning),
                2, getResources().getColor(R.color.green)));



        prepareCategoryRecyclerView(categoryList);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setPeekHeight(600);
                } if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setPeekHeight(600);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.addButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
        });

    }

//    private void prepareTodayTaskRecyclerView(List<TaskEntity> taskList){
//        binding.todayTaskRv.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
//        binding.todayTaskRv.setItemAnimator(new DefaultItemAnimator());
//        binding.todayTaskRv.setHasFixedSize(true);
//        TaskAdapter adapter = new TaskAdapter(taskList);
//        binding.todayTaskRv.setAdapter(adapter);
//    }

    private void prepareCategoryRecyclerView(List<Category> categoryList){
        binding.categoryRv.setHasFixedSize(true);
        binding.categoryRv.setItemAnimator(new DefaultItemAnimator());
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        CategoryAdapter adapter = new CategoryAdapter(categoryList,this);
        binding.categoryRv.setAdapter(adapter);
    }


    //Here problem with transaction to fragments(Design, Learning, Meeting)
    @Override
    public void categoryOnClick(String categoryName) {
        Intent intent = new Intent(MainActivity.this, TaskCategoriesActivity.class);
        intent.putExtra(Constants.CATEGORY_NAME, categoryName);
        startActivity(intent);
    }


}