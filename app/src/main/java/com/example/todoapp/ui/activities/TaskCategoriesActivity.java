package com.example.todoapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import com.example.todoapp.R;
import com.example.todoapp.ui.fragments.DesignFragment;
import com.example.todoapp.ui.fragments.LearningFragment;
import com.example.todoapp.ui.fragments.MeetingFragment;
import com.example.todoapp.utils.Constants;

public class TaskCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_categories);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(Constants.CATEGORY_NAME);

        Log.d(Constants.TAG, "CategoryName " + categoryName);
        Fragment fragment;

        switch (categoryName){
            case "Design":
                fragment = new DesignFragment();
                fragmentTransaction(fragment);
                break;
            case "Learning":
                fragment = new LearningFragment();
                fragmentTransaction(fragment);
                break;
            case "Meeting":
                fragment = new MeetingFragment();
                fragmentTransaction(fragment);
                break;
        }
    }

    private void fragmentTransaction(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.categoriesFrame, fragment)
                .commit();
    }
}