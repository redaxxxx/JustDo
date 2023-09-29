package com.example.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.TaskEntity;
import com.example.todoapp.databinding.TaskItemBinding;
import com.example.todoapp.utils.ItemOnClickListener;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TodayTaskHolder> {

    private List<TaskEntity> taskList;
    private ItemOnClickListener onClickListener;
    private Context context;
    private String categoryName;
    public TaskAdapter(Context context, List<TaskEntity> taskList,ItemOnClickListener onClickListener) {
        this.context = context;
        this.taskList = taskList;
        this.onClickListener = onClickListener;
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
        String categoryName = task.getCategoryName();

        holder.binding.taskNameTv.setText(task.getTaskName());
        holder.binding.taskDescriptionTV.setText(task.getTaskDescription());

        switch (categoryName){
            case "Design":
                holder.binding.categoryImg.setImageResource(R.drawable.fa_paint);
                holder.binding.categoryImgCardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                break;
            case "Meeting":
                holder.binding.categoryImg.setImageResource(R.drawable.healthicons_group);
                holder.binding.categoryImgCardView.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
                break;
            case "Learning":
                holder.binding.categoryImg.setImageResource(R.drawable.carbon_image);
                holder.binding.categoryImgCardView.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            onClickListener.itemOnClickListener(task.getId());
        });
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
