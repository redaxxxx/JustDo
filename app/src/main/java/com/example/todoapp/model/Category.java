package com.example.todoapp.model;

public class Category {
    private int categoryImage;
    private String categoryName;
    private int numberTask;
    private int color;

    public Category(int categoryImage, String categoryName, int numberTask, int color) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.numberTask = numberTask;
        this.color = color;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(int numberTask) {
        this.numberTask = numberTask;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
