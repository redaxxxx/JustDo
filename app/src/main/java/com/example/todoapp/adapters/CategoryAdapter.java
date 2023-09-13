package com.example.todoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.CategoryItemBinding;
import com.example.todoapp.model.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList){
        this.categoryList = categoryList;
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
        holder.binding.cardView.setCardBackgroundColor(category.getColor());

        holder.binding.iconPlus.setTag(position);
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
