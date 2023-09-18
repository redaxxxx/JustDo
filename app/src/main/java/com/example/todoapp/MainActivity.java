package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.todoapp.adapters.CategoryAdapter;
import com.example.todoapp.adapters.TodayTaskAdapter;
import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.model.Category;
import com.example.todoapp.model.TodayTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Category> categoryList;
    private List<TodayTask> todayTaskList;
    private ActivityMainBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.fa_paint, getResources().getString(R.string.category_design),
                5, getResources().getColor(R.color.red)));

        categoryList.add(new Category(R.drawable.healthicons_group, getResources().getString(R.string.category_meeting),
                1, getResources().getColor(R.color.yellow)));

        categoryList.add(new Category(R.drawable.carbon_image, getResources().getString(R.string.category_learning),
                2, getResources().getColor(R.color.green)));

        todayTaskList = new ArrayList<>();
        todayTaskList.add(new TodayTask(R.drawable.fa_paint, getResources().getString(R.string.category_design),
                1,3 , getResources().getColor(R.color.light_red),
                getResources().getColor(R.color.red)));

        todayTaskList.add(new TodayTask(R.drawable.healthicons_group, getResources().getString(R.string.category_meeting),
                2,2, getResources().getColor(R.color.light_yellow),
                getResources().getColor(R.color.yellow)));

        todayTaskList.add(new TodayTask(R.drawable.carbon_image, getResources().getString(R.string.category_learning),
                3,1, getResources().getColor(R.color.light_green),
                getResources().getColor(R.color.green)));

        prepareCategoryRecyclerView(categoryList);
        prepareTodayTaskRecyclerView(todayTaskList);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setPeekHeight(300);
                } if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setPeekHeight(600);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void prepareTodayTaskRecyclerView(List<TodayTask> todayTaskList){
        binding.todayTaskRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.todayTaskRv.setItemAnimator(new DefaultItemAnimator());
        binding.todayTaskRv.setHasFixedSize(true);
        TodayTaskAdapter adapter = new TodayTaskAdapter(todayTaskList);
        binding.todayTaskRv.setAdapter(adapter);
    }

    private void prepareCategoryRecyclerView(List<Category> categoryList){
        binding.categoryRv.setHasFixedSize(true);
        binding.categoryRv.setItemAnimator(new DefaultItemAnimator());
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        binding.categoryRv.setAdapter(adapter);
    }
}