package com.example.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.TodayTaskItemBinding;
import com.example.todoapp.model.TodayTask;
import java.util.List;

public class TodayTaskAdapter extends RecyclerView.Adapter<TodayTaskAdapter.TodayTaskHolder> {

    private List<TodayTask> todayTaskList;
    public TodayTaskAdapter(List<TodayTask> todayTaskList) {
        this.todayTaskList = todayTaskList;
    }

    @NonNull
    @Override
    public TodayTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodayTaskHolder(TodayTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskHolder holder, int position) {
        TodayTask todayTask = todayTaskList.get(position);

        holder.binding.categoryImg.setImageResource(todayTask.getDrawable());
        holder.binding.completedTaskNumberTv.setText(String.valueOf(todayTask.getCompletedTaskNumber()));
        holder.binding.taskNumberTv.setText(String.valueOf(todayTask.getTaskNumber()));
        holder.binding.categoryImgCardView.setCardBackgroundColor(todayTask.getCardViewCategoryColor());
        holder.binding.cardView.setCardBackgroundColor(todayTask.getCardViewNumberTaskColor());
    }

    @Override
    public int getItemCount() {
        return todayTaskList.size();
    }

    class TodayTaskHolder extends RecyclerView.ViewHolder{
        private TodayTaskItemBinding binding;
        public TodayTaskHolder(@NonNull TodayTaskItemBinding todayTaskItemBinding) {
            super(todayTaskItemBinding.getRoot());
            binding = todayTaskItemBinding;
        }
    }
}
