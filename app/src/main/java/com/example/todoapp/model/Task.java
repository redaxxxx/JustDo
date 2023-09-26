package com.example.todoapp.model;

public class Task {
    private int drawable;
    private String categoryName;
    private String taskTitle;
    private int cardViewCategoryColor;
    private boolean isCompletedTask;


    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCardViewCategoryColor() {
        return cardViewCategoryColor;
    }

    public void setCardViewCategoryColor(int cardViewCategoryColor) {
        this.cardViewCategoryColor = cardViewCategoryColor;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public boolean isCompletedTask() {
        return isCompletedTask;
    }

    public void setCompletedTask(boolean completedTask) {
        isCompletedTask = completedTask;
    }
}
