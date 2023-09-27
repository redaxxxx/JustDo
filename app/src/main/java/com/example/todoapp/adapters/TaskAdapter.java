package com.example.todoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.TaskItemBinding;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TodayTaskHolder> {

    private List<TaskEntity> taskList;
    private String categoryName;
    public TaskAdapter(List<TaskEntity> taskList, String categoryName) {
        this.categoryName = categoryName;
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

        switch (categoryName){
            case "Design":
                holder.binding.categoryImg.setImageResource(R.drawable.fa_paint);
                break;
            case "Meeting":
                holder.binding.categoryImg.setImageResource(R.drawable.healthicons_group);
                break;
            case "Learning":
                holder.binding.categoryImg.setImageResource(R.drawable.carbon_image);
                break;
        }
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
