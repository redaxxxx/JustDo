package com.example.todoapp.model;

public class TodayTask {
    private int drawable;
    private String categoryName;
    private int completedTaskNumber;
    private int taskNumber;
    private int cardViewNumberTaskColor;
    private int cardViewCategoryColor;

    public TodayTask(int drawable, String categoryName, int completedTaskNumber, int taskNumber,
                     int cardViewNumberTaskColor, int cardViewCategoryColor) {
        this.drawable = drawable;
        this.categoryName = categoryName;
        this.completedTaskNumber = completedTaskNumber;
        this.taskNumber = taskNumber;
        this.cardViewNumberTaskColor = cardViewNumberTaskColor;
        this.cardViewCategoryColor = cardViewCategoryColor;
    }

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

    public int getCompletedTaskNumber() {
        return completedTaskNumber;
    }

    public void setCompletedTaskNumber(int completedTaskNumber) {
        this.completedTaskNumber = completedTaskNumber;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public int getCardViewNumberTaskColor() {
        return cardViewNumberTaskColor;
    }

    public void setCardViewNumberTaskColor(int cardViewNumberTaskColor) {
        this.cardViewNumberTaskColor = cardViewNumberTaskColor;
    }

    public int getCardViewCategoryColor() {
        return cardViewCategoryColor;
    }

    public void setCardViewCategoryColor(int cardViewCategoryColor) {
        this.cardViewCategoryColor = cardViewCategoryColor;
    }
}
