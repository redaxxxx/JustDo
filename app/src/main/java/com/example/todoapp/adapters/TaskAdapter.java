package com.example.todoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.TaskItemBinding;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TodayTaskHolder> {

    private List<TaskEntity> taskList;
    public TaskAdapter(List<TaskEntity> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TodayTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodayTaskHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskHolder holder, int position) {
        TaskEntity task = taskList.get(position);

        holder.binding.taskNameTv.setText(task.getTaskName());
        holder.binding.taskDescriptionTV.setText(task.getTaskDescription());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TodayTaskHolder extends RecyclerView.ViewHolder{
        private TaskItemBinding binding;
        public TodayTaskHolder(@NonNull TaskItemBinding taskItemBinding) {
            super(taskItemBinding.getRoot());
            binding = taskItemBinding;
        }
    }
}
