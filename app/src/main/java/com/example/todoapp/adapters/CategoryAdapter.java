package com.example.todoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.databinding.CategoryItemBinding;
import com.example.todoapp.model.Category;
import com.example.todoapp.utils.CategoryClickListener;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private List<Category> categoryList;
    private CategoryClickListener categoryClickListener;

    public CategoryAdapter(List<Category> categoryList, CategoryClickListener categoryClickListener){
        this.categoryList = categoryList;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.binding.categoryImg.setImageResource(category.getCategoryImage());
        holder.binding.categoryName.setText(category.getCategoryName());
        holder.binding.numberOfTaskTv.setText(String.valueOf(category.getNumberTask()));
        holder.binding.itemLayout.setBackgroundResource(category.getColor());

        holder.binding.iconPlus.setTag(position);
        holder.binding.iconPlus.setOnClickListener(view -> {
            categoryClickListener.categoryOnClick(category.getCategoryName());
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        private CategoryItemBinding binding;
        public CategoryHolder(@NonNull CategoryItemBinding categoryItemBinding) {
            super(categoryItemBinding.getRoot());
            binding = categoryItemBinding;

        }
    }
}
