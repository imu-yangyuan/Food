package com.yuqian.food.model;

import java.util.ArrayList;

/**
 * Created by yangy on 2017/4/12.
 */

public class FoodListData {
    private int state;
    private String msg;
    private ArrayList<Food> foods;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
