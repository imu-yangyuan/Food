package com.yuqian.food.model;

/**
 * Created by yangy on 2017/4/12.
 */

public class Comment {

    private int id;

    private String foodComment;

    private int userId;

    private int foodId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodComment() {
        return foodComment;
    }

    public void setFoodComment(String foodComment) {
        this.foodComment = foodComment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}

