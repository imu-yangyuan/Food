package com.yuqian.food.model;


import java.io.Serializable;

/**
 * Created by yangy on 2017/4/11.
 */

public class Food implements Serializable {
    private int id;
    private String foodName;

    private String foodPrice;

    private String foodDescribe;

    private String foodPhotoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDescribe() {
        return foodDescribe;
    }

    public void setFoodDescribe(String foodDescribe) {
        this.foodDescribe = foodDescribe;
    }

    public String getFoodPhotoUrl() {
        return foodPhotoUrl;
    }

    public void setFoodPhotoUrl(String foodPhotoUrl) {
        this.foodPhotoUrl = foodPhotoUrl;
    }
}
