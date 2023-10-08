package com.example.todoapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;
import com.example.todoapp.Repository;
import com.example.todoapp.adapters.TaskAdapter;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.FragmentDesignBinding;
import com.example.todoapp.model.Task;
import com.example.todoapp.ui.activities.AddTaskActivity;
import com.example.todoapp.ui.activities.MainActivity;
import com.example.todoapp.utils.Constants;
import com.example.todoapp.utils.ItemOnClickListener;
import com.example.todoapp.viewModel.TaskViewModel;
import com.example.todoapp.viewModel.TaskViewModelFactory;

import java.util.List;

public class DesignFragment extends Fragment implements ItemOnClickListener {
    private FragmentDesignBinding binding;
    private TaskViewModel viewModel;
    private List<TaskEntity> taskList;
    private TaskAdapter taskAdapter;
    private int numberOfTasks;
    private boolean checkboxisChecked = false;
    private int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_design, container,false);

        AppDatabase database = AppDatabase.getmInstance(getActivity());
        Repository repository = new Repository(database);
        TaskViewModelFactory factory = new TaskViewModelFactory(repository);

        setItemtouchCallback();

        viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        viewModel.getTasksOfCategory("Design").observe(getViewLifecycleOwner(), new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                taskList = taskEntities;
                prepareRecyclerView(taskEntities);
            }
        });

        binding.addButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddTaskActivity.class));
        });

        binding.backArrowBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        return binding.getRoot();
    }

    private void setItemtouchCallback(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TaskEntity taskEntity = taskList.remove(viewHolder.getAdapterPosition());
                viewModel.deleteTask(taskEntity);
                taskAdapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.designTaskRv);
    }
    private void prepareRecyclerView(List<TaskEntity> taskList){
        binding.designTaskRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        binding.designTaskRv.setHasFixedSize(true);
        binding.designTaskRv.setItemAnimator(new DefaultItemAnimator());

        taskAdapter = new TaskAdapter(getActivity(), taskList, this);
        binding.designTaskRv.setAdapter(taskAdapter);
    }

    @Override
    public void itemOnClickListener(int taskId) {
        id = taskId;
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        intent.putExtra(Constants.TASK_ID, taskId);
        startActivity(intent);
    }
}