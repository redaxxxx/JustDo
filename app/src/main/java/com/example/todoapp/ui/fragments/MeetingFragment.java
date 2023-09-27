package com.example.todoapp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;
import com.example.todoapp.Repository;
import com.example.todoapp.adapters.TaskAdapter;
import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.FragmentMeetingBinding;
import com.example.todoapp.viewModel.TaskViewModel;
import com.example.todoapp.viewModel.TaskViewModelFactory;

import java.util.List;

public class MeetingFragment extends Fragment {
    private FragmentMeetingBinding binding;
    private TaskViewModel viewModel;
    private List<TaskEntity> taskList;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting, container, false);

        AppDatabase database = AppDatabase.getmInstance(getActivity());
        Repository repository = new Repository(database);
        TaskViewModelFactory factory = new TaskViewModelFactory(repository);

        viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        viewModel.getTasksOfCategory("Meeting").observe(getViewLifecycleOwner(), new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                taskList = taskEntities;
                prepareRecyclerView(taskEntities);
            }
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
    }

    private void prepareRecyclerView(List<TaskEntity> taskList){
        binding.meetingTaskRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        binding.meetingTaskRv.setHasFixedSize(true);
        binding.meetingTaskRv.setItemAnimator(new DefaultItemAnimator());

        taskAdapter = new TaskAdapter(taskList, "Meeting");
        binding.meetingTaskRv.setAdapter(taskAdapter);
    }
}